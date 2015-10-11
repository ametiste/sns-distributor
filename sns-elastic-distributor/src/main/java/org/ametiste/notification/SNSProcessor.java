package org.ametiste.notification;

import java.util.Map;

/**
 * Created by ametiste on 7/6/15.
 */
public interface SNSProcessor {

    void send(String indexName, String id, Map<String, Object> data) throws Exception;
}
