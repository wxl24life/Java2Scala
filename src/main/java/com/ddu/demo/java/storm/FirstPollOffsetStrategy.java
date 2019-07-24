package com.ddu.demo.java.storm;

/**
 * @author wxl24life
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
    LATEST;

    @Override
    public String toString() {
        return "FirstPollOffsetStrategy{" + super.toString() + "}";
    }
}
