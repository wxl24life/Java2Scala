package com.ddu.demo.java.storm;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.storm.Config;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.topology.TopologyBuilder;

import java.util.Collection;
import java.util.regex.Pattern;

import static com.ddu.demo.java.storm.DemoTopoSubmitHelper.submit;

/**
 * Standard implementation using storm-kafka-client api
 *
 * @author wxl24life
 */
public class CompareDemoToplogy {
    public static void main(String[] args) throws InvalidTopologyException, AuthorizationException, AlreadyAliveException {
        TopologyBuilder topologyBuilder = new TopologyBuilder();

        // Use factory method KafkaSpoutConfig.topologyBuilder() to create Builder with String key/value deserializers
        KafkaSpoutConfig.Builder<String, String> kafkaSpoutConfigBuilder = KafkaSpoutConfig.builder("localhost:9092", "TEST");
        KafkaSpoutConfig<String, String> kafkaSpoutConfig = kafkaSpoutConfigBuilder
                .setProp(ConsumerConfig.GROUP_ID_CONFIG, "compare-test")
                .setFirstPollOffsetStrategy(KafkaSpoutConfig.FirstPollOffsetStrategy.EARLIEST)
                .setEmitNullTuples(true)
                .setPollTimeoutMs(100)
                .build();

        KafkaSpout<String, String> kafkaSpout = new KafkaSpout<>(kafkaSpoutConfig);
        topologyBuilder.setSpout("kafka", kafkaSpout, 2);
        topologyBuilder.setBolt("print", new DemoBolt(), 2).shuffleGrouping("kafka");

        Config conf = new Config();
        conf.setMaxTaskParallelism(1);
        conf.setNumWorkers(2);

        submit(args, conf, topologyBuilder);
    }

    // Create Builder with Byte[] deserializer
    private static KafkaSpoutConfig<Byte[], Byte[]>  createKafkaSpoutConfigBuilderWithByteArraySerializer() {
        KafkaSpoutConfig.Builder<Byte[], Byte[]> kafkaSpoutConfigBuilder = new KafkaSpoutConfig.Builder<>("localhost:9092", "TEST");
        kafkaSpoutConfigBuilder.setProp(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
        kafkaSpoutConfigBuilder.setProp(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
        kafkaSpoutConfigBuilder.setProp(ConsumerConfig.GROUP_ID_CONFIG, "compare-test");
        return kafkaSpoutConfigBuilder.build();
    }

}

