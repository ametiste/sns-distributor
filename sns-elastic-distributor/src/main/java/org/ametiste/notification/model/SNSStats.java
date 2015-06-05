package org.ametiste.notification.model;

/**
 * Created by atlantis on 6/2/15.
 */
public class SNSStats {

    private final long sentMessageCount;
    private final long pendingMessagesCount;
    private final long lostMessageCount;

    public SNSStats(long sentMessageCount, long pendingMessagesCount, long lostMessageCount) {
        this.sentMessageCount = sentMessageCount;
        this.pendingMessagesCount = pendingMessagesCount;
        this.lostMessageCount = lostMessageCount;
    }

    public long getSent() {
        return sentMessageCount;
    }

    public long getPending() {
        return pendingMessagesCount;
    }

    public long getLost() {
        return lostMessageCount;
    }
}
