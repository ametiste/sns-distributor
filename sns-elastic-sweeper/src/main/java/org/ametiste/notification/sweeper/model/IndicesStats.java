package org.ametiste.notification.sweeper.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ametiste on 7/2/15.
 */
public class IndicesStats {

    private long total;
    private List<IndexStatistics> byIndex;

    public IndicesStats(long total) {
        this.total = total;
        this.byIndex = new ArrayList<>();
    }

    public void addIndexStat(String name, long total) {
        this.byIndex.add(new IndexStatistics(name, total));
    }

    public long getTotal() {
        return total;
    }

    public List<IndexStatistics> getDetails() {
        return this.byIndex;
    }
}
