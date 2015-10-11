package org.ametiste.notification.sweeper.application.logger;

import org.ametiste.notification.sweeper.model.SweepDetails;
import org.ametiste.notification.sweeper.model.SweepLog;

import java.util.List;

/**
 * Created by ametiste on 7/1/15.
 */
public interface SweepLogger {

    void log(SweepMethod method, SweepDetails details);
    List<SweepLog> listLogs();
    List<SweepLog> listLogsBy(String sweepMethod);
}
