package com.ddu.demo.java.storm;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;

/**
 * @author wxl24life
 */
public class DemoTopoSubmitHelper {
    static void submit(String[] args, Config conf, TopologyBuilder builder) throws InvalidTopologyException, AuthorizationException, AlreadyAliveException {
        if (args != null && args.length > 0) {
            StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
        } else {
            LocalCluster cluster = new LocalCluster();
            conf.setMaxTaskParallelism(1);
            cluster.submitTopology("test", conf, builder.createTopology());
        }
    }
}
