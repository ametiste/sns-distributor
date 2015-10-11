package org.ametiste.notification.sweeper.model;

import org.ametiste.notification.sweeper.application.logger.SweepMethod;

/**
 * Created by ametiste on 7/2/15.
 */
public class SweepLog {

    private SweepMethod method;
    private SweepDetails details;

    public SweepLog(SweepMethod method, SweepDetails details) {
        this.method = method;
        this.details = details;
    }

    public SweepMethod getMethod() {
        return method;
    }

    public SweepDetails getDetails() {
        return details;
    }
}
