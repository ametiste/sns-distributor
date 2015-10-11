package org.ametiste.notification.sweeper.model;

/**
 * Created by ametiste on 7/1/15.
 */
public class IndexStatistics {

    private long storeSize;
    private String indexName;

    public IndexStatistics(String indexName, long storeSize) {
        this.indexName = indexName;
        this.storeSize = storeSize;
    }

    public long getStoreSize() {
        return storeSize;
    }

    public String getIndexName() {
        return indexName;
    }


}
