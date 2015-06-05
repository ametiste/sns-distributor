package org.ametiste.notification.model;

import java.util.List;

/**
 * Created by Daria on 03.06.2015.
 */
public class SNSDump {
    private final String forClass;
    private final List<SNSFailure> failedReports;

    public SNSDump(String forClass, List<SNSFailure> failedReports) {

        this.forClass = forClass;
        this.failedReports = failedReports;
    }

    public String getForClass() {
        return forClass;
    }

    public List<SNSFailure> getFailedReports() {
        return failedReports;
    }
}
