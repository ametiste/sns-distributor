package org.ametiste.notification.dummy;


import org.ametiste.notification.sweeper.application.connector.ElasticConnector;
import org.ametiste.notification.sweeper.model.IndexStatistics;
import org.ametiste.notification.sweeper.model.IndicesStats;

/**
 * Created by ametiste on 7/6/15.
 */
public class DummySweepConnector implements ElasticConnector {

    @Override
    public IndicesStats stats() {
        return null;
    }

    @Override
    public void deleteByName(String name) {

    }

    @Override
    public IndexStatistics statsByName(String name) {
        return null;
    }

    @Override
    public String getIndexWildcard() {
        return null;
    }
}
