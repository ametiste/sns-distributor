package org.ametiste.notification.sweeper.interfaces.dto;

import org.ametiste.notification.sweeper.model.SweepLog;

/**
 * Created by ametiste on 7/3/15.
 */
public class SweepLogDTO {

    private String method;
    private SweepDetailsDTO details;

    public SweepLogDTO(SweepLog log) {
        method = log.getMethod().toString();
        details = new SweepDetailsDTO(log.getDetails());
    }

    public String getMethod() {
        return method;
    }

    public SweepDetailsDTO getDetails() {
        return details;
    }
}
