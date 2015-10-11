package org.ametiste.notification.dummy;

import org.ametiste.notification.SNSProcessor;
import org.ametiste.notification.exceptions.ExceptionOne;
import org.ametiste.notification.exceptions.ExceptionTwo;

import java.util.Map;

/**
 * Created by ametiste on 7/6/15.
 */
public class SnsDummyClient implements SNSProcessor {

    @Override
    public void send(String indexName, String id, Map<String, Object> data) throws Exception {
        if(id.equals("11111111-1111-1111-1111-111111111111")) {
            throw new ExceptionOne();
        }
        if(id.equals("11111111-1111-1111-1111-111111111112")) {
            throw new ExceptionTwo();
        }
    }
}
