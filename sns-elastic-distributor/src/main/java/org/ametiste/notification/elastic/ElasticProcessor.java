package org.ametiste.notification.elastic;

import org.ametiste.notification.SNSProcessor;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Created by ametiste on 7/6/15.
 */
public class ElasticProcessor implements SNSProcessor {

    private Client client;
    private int requestTimeoutInMs;

    public ElasticProcessor(Client client, int requestTimeoutInMs) {
        this.client = client;
        this.requestTimeoutInMs = requestTimeoutInMs;
    }

    public void send(String indexName, String id, Map<String, Object> data) throws IOException {

        XContentBuilder builder = jsonBuilder().startObject();
        for(Map.Entry<String, Object> entry: data.entrySet()) {
            builder.field(entry.getKey(), entry.getValue());
        }
        builder.endObject();

        client.prepareIndex(indexName, "logs", id)
                .setSource(builder).setTimeout(TimeValue.timeValueMillis(requestTimeoutInMs))
                .setCreate(true)
                .execute()
                .actionGet();
    }
}
