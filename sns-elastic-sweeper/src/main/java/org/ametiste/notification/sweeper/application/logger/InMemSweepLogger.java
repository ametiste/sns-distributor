package org.ametiste.notification.sweeper.application.logger;

import org.ametiste.notification.sweeper.model.SweepDetails;
import org.ametiste.notification.sweeper.model.SweepLog;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ametiste on 7/1/15.
 */
public class InMemSweepLogger implements SweepLogger {

    private LinkedList<SweepLog> log;

    public InMemSweepLogger() {
        log = new LinkedList<>();
    }

    @Override
    public void log(SweepMethod method, SweepDetails details) {
        log.addFirst(new SweepLog(method,details));
    }

    @Override
    public List<SweepLog> listLogs() {
        return Collections.unmodifiableList(log);
    }

    @Override
    public List<SweepLog> listLogsBy(String sweepMethod) {
        return log.stream()
                .filter(entry -> entry.getMethod().toString().equalsIgnoreCase(sweepMethod))
                .distinct()
                .collect(Collectors.toList());
    }
}
