package org.ametiste.notification.sweeper.model;

/**
 * Created by ametiste on 7/1/15.
 */
public class SweepDetails {
    private final long timestamp;
    private final String indexName;
    private long capacity;
    private final long indexSize;
    private long totalSize;

    public SweepDetails(long timestamp, String indexName, long capacity, long indexSize, long totalSize) {

        this.timestamp = timestamp;
        this.indexName = indexName;
        this.capacity = capacity;
        this.indexSize = indexSize;
        this.totalSize = totalSize;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getIndexName() {
        return indexName;
    }

    public long getIndexSize() {
        return indexSize;
    }

    public long getCapacity() {
        return capacity;
    }

    public long getTotalSize() {
        return totalSize;
    }
}
