package com.ddu.demo.java.storm;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

import java.util.Properties;

/**
 * @author wxl24life
 */
public class StormDemoTopology {
    public static void main(String[] args) throws InvalidTopologyException, AuthorizationException, AlreadyAliveException {
        TopologyBuilder builder = new TopologyBuilder();

        Properties kafkaProps = new Properties();
        kafkaProps.put("topic", "TEST");
        kafkaProps.put("bootstrap.servers", "localhost:9092");
        kafkaProps.put("group.id", "");
        kafkaProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaProps.put("group.id", "test");

        MyKafkaSpout<String, String> kafkaSpout = new MyKafkaSpout<>(kafkaProps);
        builder.setSpout("kafka", kafkaSpout, 2);
        builder.setBolt("print", new PrintBolt(), 2).shuffleGrouping("kafka");

        Config conf = new Config();
        conf.setMaxTaskParallelism(1);
        conf.setNumWorkers(2);

        if (args != null && args.length > 0) {
            StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
        } else {
            LocalCluster cluster = new LocalCluster();
            conf.setMaxTaskParallelism(1);
            cluster.submitTopology("test", conf, builder.createTopology());
        }
    }
}

class PrintBolt extends BaseBasicBolt {

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        System.out.println(tuple.toString());
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
