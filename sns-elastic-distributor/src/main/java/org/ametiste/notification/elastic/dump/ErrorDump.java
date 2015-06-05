package org.ametiste.notification.elastic.dump;

import org.ametiste.notification.model.Report;
import com.google.common.collect.EvictingQueue;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by Daria on 03.06.2015.
 */
public class ErrorDump {


    private EvictingQueue<Failure> failures;

    public ErrorDump() {
        failures = EvictingQueue.create(100);
    }

    public void record(Report report, Exception e) {
        failures.add(new Failure(report, e));
    }


    public Collection<Failure> getFailures() {
        return Collections.unmodifiableCollection(failures);
    }
}
