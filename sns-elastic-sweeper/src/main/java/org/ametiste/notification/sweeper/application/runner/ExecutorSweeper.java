package org.ametiste.notification.sweeper.application.runner;

import org.ametiste.notification.sweeper.application.configurer.SweepConfigurer;
import org.ametiste.notification.sweeper.application.connector.ElasticConnector;
import org.ametiste.notification.sweeper.application.logger.SweepLogger;
import org.ametiste.notification.sweeper.application.logger.SweepMethod;
import org.ametiste.notification.sweeper.model.IndexStatistics;
import org.ametiste.notification.sweeper.model.IndicesStats;
import org.ametiste.notification.sweeper.model.SweepDetails;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by ametiste on 7/1/15.
 */
public class ExecutorSweeper implements Sweeper, Runnable {


    private final ElasticConnector connector;
    private final SweepConfigurer configurer;
    private final SweepLogger logger;
    private final ScheduledExecutorService executor;
    private final Comparator<IndexStatistics> comparator;
    private final long gbToBMultuplexer =  1024 * 1024 * 1024;

    public ExecutorSweeper(ElasticConnector connector, SweepConfigurer configurer, SweepLogger logger,
                           ScheduledExecutorService executor, Comparator<IndexStatistics> comparator) {

        this.connector = connector;
        this.configurer = configurer;
        this.logger = logger;
        this.executor = executor;
        this.comparator = comparator;
    }


    public void init() {
        executor.execute(this);
    }

    public void deleteByName(String name) {
        IndicesStats totalStats = connector.stats();
        long capacity = Long.parseLong(getEssentialConfig("capacity")) * gbToBMultuplexer;
        try {
            IndexStatistics indexStatistics = connector.statsByName(name);
            connector.deleteByName(name);
            logger.log(SweepMethod.MANUAL, new SweepDetails(System.currentTimeMillis(), name, capacity, indexStatistics.getStoreSize(), totalStats.getTotal()));
        }
        catch (IllegalArgumentException e) {
            throw e;
        }

    }

    @Override
    public void sweep() {

        long capacity = Long.parseLong(getEssentialConfig("capacity")) * gbToBMultuplexer;

        IndicesStats totalStats = connector.stats();


        if (totalStats.getTotal() < capacity) {
            logger.log(SweepMethod.SKIPPED, new SweepDetails(System.currentTimeMillis(), connector.getIndexWildcard(), capacity, totalStats.getTotal(), totalStats.getTotal()));
            return;
        }

        List<IndexStatistics> stats = totalStats.getDetails();
        stats.sort(comparator);
        List<IndexStatistics> indicesToRemove = new ArrayList<>();
        long total = 0;

        for(IndexStatistics stat: stats) {
            total = total + stat.getStoreSize();
            if(total > capacity) {
                indicesToRemove.add(stat);
            }
        }

        total = totalStats.getTotal();

        for(IndexStatistics stat: indicesToRemove) {
            connector.deleteByName(stat.getIndexName());
            logger.log(SweepMethod.SCHEDULED,
                    new SweepDetails(System.currentTimeMillis(), stat.getIndexName(),
                            capacity, stat.getStoreSize(), total - stat.getStoreSize()));
        }
    }

    public void run() {
        this.sweep();
        executor.schedule(this, Long.parseLong(getEssentialConfig("sweepPeriod")), TimeUnit.MINUTES);
    }


    private String getEssentialConfig(String name) {
        if(!configurer.contains(name)) {
            throw new IllegalArgumentException("No config defined with name: " + name);

        }
        return configurer.get(name);
    }
}
