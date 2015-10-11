package org.ametiste.notification.sweeper.application.runner;

import org.ametiste.notification.sweeper.model.IndexStatistics;

import java.util.Comparator;

/**
 * Created by ametiste on 7/1/15.
 */
public class IndexDescNameComparator implements Comparator<IndexStatistics> {

    @Override
    public int compare(IndexStatistics o1, IndexStatistics o2) {

        return - o1.getIndexName().compareTo(o2.getIndexName());
    }
}
