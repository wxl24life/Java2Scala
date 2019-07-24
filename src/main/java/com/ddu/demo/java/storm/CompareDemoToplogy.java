package com.ddu.demo.java.storm;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.storm.Config;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.topology.TopologyBuilder;

import static com.ddu.demo.java.storm.DemoTopoSubmitHelper.submit;

/**
 * Standard implementation using storm-kafka-client api
 *
 * @author wxl24life
 */
public class CompareDemoToplogy {
    public static void main(String[] args) throws InvalidTopologyException, AuthorizationException, AlreadyAliveException {
        TopologyBuilder builder = new TopologyBuilder();

        KafkaSpoutConfig.Builder<String, String> kafkaBuilder = new KafkaSpoutConfig.Builder<>("localhost:9092", "TEST");
        kafkaBuilder.setProp(ConsumerConfig.GROUP_ID_CONFIG, "compare-test");
        kafkaBuilder.setProp(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaBuilder.setProp(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        // test FirstPollOffsetStrategy
        kafkaBuilder.setFirstPollOffsetStrategy(KafkaSpoutConfig.FirstPollOffsetStrategy.EARLIEST);

        KafkaSpout<String, String> kafkaSpout = new KafkaSpout<>(kafkaBuilder.build());
        builder.setSpout("kafka", kafkaSpout, 2);
        builder.setBolt("print", new DemoBolt(), 2).shuffleGrouping("kafka");

        Config conf = new Config();
        conf.setMaxTaskParallelism(1);
        conf.setNumWorkers(2);

        submit(args, conf, builder);
    }
}

