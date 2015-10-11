package org.ametiste.notification.sweeper.application.connector;

import org.ametiste.notification.sweeper.model.IndexStatistics;
import org.ametiste.notification.sweeper.model.IndicesStats;

/**
 * Created by ametiste on 7/1/15.
 */
public interface ElasticConnector {

    IndicesStats stats();
    void deleteByName(String name);
    IndexStatistics statsByName(String name);
    String getIndexWildcard();
}
