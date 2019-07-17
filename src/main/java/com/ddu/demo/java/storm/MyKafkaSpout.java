package com.ddu.demo.java.storm;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

/**
 * @author wxl24life
 */
public class MyKafkaSpout<K, V> extends BaseRichSpout {

    SpoutOutputCollector collector;

    Properties properties;
    KafkaConsumer<K, V> consumer;

    public MyKafkaSpout(Properties properties) {
        this.properties = properties;
    }

    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;

        consumer = new KafkaConsumer<K, V>(properties);
        // 订阅单一主题
        consumer.subscribe(Collections.singleton(properties.getProperty("topic")));
    }

    @Override
    public void nextTuple() {
        try {
            while (true) {
                ConsumerRecords<K, V> records = consumer.poll(100);
                for (ConsumerRecord<K, V> record : records) {
                    this.collector.emit(Arrays.asList(record.key(), record.value()));
                }
            }
        } finally {
            consumer.close();
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("key", "value"));
    }
}
