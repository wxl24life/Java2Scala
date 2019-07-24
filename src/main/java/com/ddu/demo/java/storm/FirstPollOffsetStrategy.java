package com.ddu.demo.java.storm;

import org.apache.storm.kafka.spout.KafkaSpout;

/**
 * Defines how the {@link KafkaSpout} seeks the offset to be used in the first poll to Kafka upon topology deployment.
 * By default this parameter is set to UNCOMMITTED_EARLIEST.
 */
public enum FirstPollOffsetStrategy {
    /**
     * The kafka spout polls records starting in the first offset of the partition, regardless of previous commits. This setting only
     * takes effect on topology deployment
     */
    EARLIEST,
    /**
     * The kafka spout polls records with offsets greater than the last offset in the partition, regardless of previous commits. This
     * setting only takes effect on topology deployment
     */
    LATEST,
    /**
     *  The kafka spout polls records from the last committed offset, if any. If no offset has been committed it behaves as EARLIEST
     */
    UNCOMMITTED_EARLIEST,
    /**
     * The kafka spout polls records from the last committed offset, if any. If no offset has been committed it behaves as LATEST
     */
    UNCOMMITTED_LATEST;
    @Override
    public String toString() {
        return "FirstPollOffsetStrategy{" + super.toString() + "}";
    }
}
