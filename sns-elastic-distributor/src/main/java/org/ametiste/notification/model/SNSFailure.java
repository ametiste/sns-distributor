package org.ametiste.notification.model;

/**
 * Created by Daria on 03.06.2015.
 */
public class SNSFailure {
    private final Object failedObject;
    private final String stackTrace;

    public SNSFailure(Object failedObject, String stackTrace) {

        this.failedObject = failedObject;
        this.stackTrace = stackTrace;
    }

    public Object getFailedObject() {
        return failedObject;
    }

    public String getStackTrace() {
        return stackTrace;
    }
}
