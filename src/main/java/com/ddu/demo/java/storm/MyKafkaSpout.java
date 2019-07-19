package com.ddu.demo.java.storm;

import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;

import java.util.*;

/**
 * @author wxl24life
 */
class MyKafkaSpout<K, V> extends BaseRichSpout {

    private SpoutOutputCollector collector;

    private Properties properties;
    private KafkaConsumer<K, V> consumer;

    private Map<TopicPartition, List<ConsumerRecord<K, V>>> waitingToEmitted;

    // constructor
    MyKafkaSpout(Properties properties) {
        this.properties = properties;
    }

    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
        // In order to prevent from consuming duplicated messages or lost message while consuming,
        // we set enable.auto.commit to false to commit manually
        properties.setProperty("enable.auto.commit", "false");
        waitingToEmitted = new HashMap<>();

        consumer = new KafkaConsumer<K, V>(properties);
        // 订阅单一主题
        consumer.subscribe(Collections.singleton(properties.getProperty("topic")));
    }

    @Override
    public void nextTuple() {
        try {
            while (true) {
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
                    ConsumerRecords<K, V> records = consumer.poll(100);

                    /*Set<TopicPartition> topicPartitions = records.partitions();
                    for (TopicPartition tp : topicPartitions) {
                        tp.partition(), tp.topic();
                    }*/
                    for (ConsumerRecord<K, V> record : records) {
                        TopicPartition topicPartition = new TopicPartition(record.topic(), record.partition());
                        if (waitingToEmitted.containsKey(topicPartition)) {
                            waitingToEmitted.get(topicPartition).add(record);
                        } else {
                            waitingToEmitted.put(topicPartition, Collections.singletonList(record));
                        }
                    }
                } else {
                    emitIfWaitingNotEmmited();
                }
                // consumer.commitAsync(); // 异步提交，碰到可恢复异常时也不会重试
            }
        } catch (Exception e) {
            // log error
            e.printStackTrace();
        } finally {
            // 退出关闭 consumer，随之关闭网络连接和 socket，并会立即触发一次再均衡
            consumer.close();
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
                break;
            }
            // remove 跟 hasNext 方法一起使用
            waitingToEmitIter.remove();
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("key", "value"));
    }
}
