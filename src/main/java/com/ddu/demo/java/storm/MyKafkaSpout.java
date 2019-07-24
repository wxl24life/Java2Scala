package com.ddu.demo.java.storm;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author wxl24life
 */
class MyKafkaSpout<K, V> extends BaseRichSpout {
    private static final Logger LOG = LoggerFactory.getLogger(MyKafkaSpout.class);

    private SpoutOutputCollector collector;
    private TopologyContext context;

    private Properties properties;
    private KafkaConsumer<K, V> consumer;
    private FirstPollOffsetStrategy firstPollOffsetStrategy = FirstPollOffsetStrategy.UNCOMMITTED_EARLIEST;

    private Map<TopicPartition, List<ConsumerRecord<K, V>>> waitingToEmitted;

    // constructor
    MyKafkaSpout(Properties properties) {
        this.properties = properties;
    }

    public void setFirstPollOffsetStrategy(FirstPollOffsetStrategy firstPollOffsetStrategy) {
        this.firstPollOffsetStrategy = firstPollOffsetStrategy;
    }

    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
        this.context = context;
        // In order to prevent from consuming duplicated messages or lost message while consuming,
        // we set enable.auto.commit to false to commit manually
        properties.setProperty("enable.auto.commit", "false");
        waitingToEmitted = new HashMap<>();

        consumer = new KafkaConsumer<K, V>(properties);
        // 订阅单一主题
        consumer.subscribe(Collections.singleton(properties.getProperty("topic")), new KafkaSpoutConsumerRebalanceListener());
    }

    // kafka rebalance listener, do some work when consumer rebalance happens
    private class KafkaSpoutConsumerRebalanceListener implements ConsumerRebalanceListener {

        private Collection<TopicPartition> previousAssignment = new HashSet<>();

        @Override
        public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
            previousAssignment = partitions;
        }

        @Override
        public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
            LOG.info("Partitions reassignment. [task-ID={}, consumer-group={}, consumer={}, topic-partitions={}]",
                    context.getThisTaskId(), properties.get("group.id"), consumer, partitions);

            // do initialize here!
            Set<TopicPartition> newPartitions = new HashSet<>(partitions);
            newPartitions.removeAll(previousAssignment);
            for (TopicPartition tp : newPartitions) {
                LOG.info("Found new topic = {}, partition = {}", tp.topic(), tp.partition());
                // Get the last committed offset for this partition
                OffsetAndMetadata committedOffsetAndMetadata = consumer.committed(tp);
                if (committedOffsetAndMetadata != null) {
                    // In fact we only want to use FirstPollOffsetStrategy when the topology is first deployed.
                    // So we need some strategy to tell us if this topology has just deployed
                    // Otherwise when firstPollOffsetStrategy is set to EARLIEST or LATEST, we will process
                    // dupilcated messages or lost messages.
                    if (firstPollOffsetStrategy == FirstPollOffsetStrategy.EARLIEST) {
                        consumer.seekToBeginning(Collections.singleton(tp));
                    } else if (firstPollOffsetStrategy == FirstPollOffsetStrategy.LATEST) {
                        consumer.seekToEnd(Collections.singleton(tp));
                    } else {
                        consumer.seek(tp, committedOffsetAndMetadata.offset()); // no need to +1
                    }
                } else {
                    // No offset commits has ever been done for this consumer group and topic-partion
                    if (firstPollOffsetStrategy == FirstPollOffsetStrategy.EARLIEST
                            || firstPollOffsetStrategy == FirstPollOffsetStrategy.UNCOMMITTED_EARLIEST) {
                        consumer.seekToBeginning(Collections.singleton(tp));
                    } else if (firstPollOffsetStrategy == FirstPollOffsetStrategy.LATEST
                            || firstPollOffsetStrategy == FirstPollOffsetStrategy.UNCOMMITTED_LATEST) {
                        consumer.seekToEnd(Collections.singleton(tp));
                    }
                }
                LOG.info("Set consumer position to [{}] for topic-partition [{}] with [{}] and committed offset [{}]",
                        consumer.position(tp), tp, firstPollOffsetStrategy, committedOffsetAndMetadata);
            }

            LOG.info("Initialization complete");
        }
    }

    @Override
    public void nextTuple() {
        try {
            // 使用 poll 方法持续轮询，参数代表了超时时间（单位 ms），在 consumer 缓冲区里没有可用数据时会发生阻塞，
            // 超时参数用于控制阻塞的时间，指定了方法在多久之后可以返回，不管有无数据都返回
            // 超时时间的设定取决于应用程序对响应速度的要求，比如在多长时间内把控制权交给轮询线程
            // 如果设置成 poll(0)，不阻塞，直接返回

            // 关于 kafka consumer 轮询的进一步说明
            // 1. 轮询除了获取分区数据，在首次调用新消费者的 poll() 方法时，会负责查找 GroupCoordinator，然后加入 group，并接受分配过来的分区
            // 2. 如果发生 rebalance，整个过程也是在轮询期间完成的
            // 3. 心跳也是从轮询里发送出去的
            // 所以，要确保在轮训期间所做的任何处理工作尽快完成
            if (waitingToEmitted.isEmpty()) {
                ConsumerRecords<K, V> consumerRecords = consumer.poll(100);
                for (TopicPartition tp : consumerRecords.partitions()) {
                    waitingToEmitted.put(tp, new ArrayList<>(consumerRecords.records(tp)));
                }
            }
            emitIfWaitingNotEmmited();
            // consumer.commitAsync(); // 异步提交，碰到可恢复异常时也不会重试
        } catch (Exception e) {
            // log error
            e.printStackTrace();
        }
    }

    // ======== emit  =========
    private void emitIfWaitingNotEmmited() {
        Iterator<List<ConsumerRecord<K, V>>> waitingToEmitIter = waitingToEmitted.values().iterator();
        while (waitingToEmitIter.hasNext()) {
            List<ConsumerRecord<K, V>> waitingToEmitForTp = waitingToEmitIter.next();
            if (!waitingToEmitForTp.isEmpty()) {
                ConsumerRecord<K, V> consumerRecord = waitingToEmitForTp.remove(0);
                this.collector.emit(Arrays.asList(consumerRecord.topic(), consumerRecord.partition(), consumerRecord.offset(),
                        consumerRecord.key(), consumerRecord.value()));
                this.consumer.commitSync();
            }

            // remove 跟 hasNext 方法一起使用
            if (waitingToEmitForTp.isEmpty()) {
                waitingToEmitIter.remove();
            }
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("topic", "partition", "offset", "key", "value"));
    }

    @Override
    public void close() {
        // 退出关闭 consumer，随之关闭网络连接和 socket，并会立即触发一次再均衡
        this.consumer.close();
    }
}


