package org.ametiste.notification.elastic.dump;

/**
 * Created by Daria on 03.06.2015.
 */
public class Failure {

    private Object failedObject;
    private Exception exception;

    public Failure(Object failed, Exception exception) {
        this.failedObject = failed;
        this.exception = exception;
    }

    public Object getFailedObject() {
        return failedObject;
    }

    public Exception getException() {
        return exception;
    }
}
