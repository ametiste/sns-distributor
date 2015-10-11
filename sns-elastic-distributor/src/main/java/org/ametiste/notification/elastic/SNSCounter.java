package org.ametiste.notification.elastic;


import org.ametiste.notification.model.SNSStats;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Daria on 03.06.2015.
 */
public class SNSCounter {

    private AtomicLong total = new AtomicLong(0);
    private AtomicLong indexed = new AtomicLong(0);
    private AtomicLong failed = new AtomicLong(0);

    public void incrementTotal() {
        total.incrementAndGet();
    }

    public void incrementIndexed() {
        indexed.incrementAndGet();
    }

    public void incrementFailed() {
        failed.incrementAndGet();
    }


    public SNSStats getStats() {
        long indexed = this.indexed.get();
        long failed = this.failed.get();
        long pending = this.total.get() - indexed - failed;
        return new SNSStats(indexed, pending, failed);
    }
}
