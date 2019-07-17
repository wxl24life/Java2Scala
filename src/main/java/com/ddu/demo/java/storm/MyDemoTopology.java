package com.ddu.demo.java.storm;

import org.apache.storm.Config;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;

import java.util.Properties;

import static com.ddu.demo.java.storm.DemoTopoSubmitHelper.submit;

/**
 * @author wxl24life
 */
public class MyDemoTopology {
    public static void main(String[] args) throws InvalidTopologyException, AuthorizationException, AlreadyAliveException {
        TopologyBuilder builder = new TopologyBuilder();

        Properties kafkaProps = new Properties();
        kafkaProps.put("topic", "TEST");
        kafkaProps.put("bootstrap.servers", "localhost:9092");
        kafkaProps.put("group.id", "");
        kafkaProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaProps.put("group.id", "test");

        // test properties
        // kafkaProps.put("fetch.min.bytes", "20");

        MyKafkaSpout<String, String> kafkaSpout = new MyKafkaSpout<>(kafkaProps);
        builder.setSpout("kafka", kafkaSpout, 2);
        builder.setBolt("print", new DemoBolt(), 2).shuffleGrouping("kafka");

        Config conf = new Config();
        conf.setMaxTaskParallelism(1);
        conf.setNumWorkers(2);

        submit(args, conf, builder);
    }
}

