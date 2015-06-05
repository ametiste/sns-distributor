package org.ametiste.notification.elastic;

import org.ametiste.notification.elastic.preprocess.ReportNamingPolicy;
import org.ametiste.notification.elastic.dump.ErrorDump;
import org.ametiste.notification.elastic.dump.Failure;
import org.ametiste.notification.model.*;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Created by Daria on 03.06.2015.
 */
public class SNSElasticClient {

    private Client client;
    private SNSCounter counter;
    private DateFormat indexFormat = new SimpleDateFormat("'reports-'yyyy-MM-dd");
    private ReportNamingPolicy policy;
    private int requestTimeoutInMs;
    private ErrorDump dump = new ErrorDump();

    public SNSElasticClient(Client client, ReportNamingPolicy policy, int requestTimeoutInMs) {
        this.policy = policy;
        this.requestTimeoutInMs = requestTimeoutInMs;

        counter = new SNSCounter();

        this.client = client;
    }

    public void send(Report report) throws IOException {
        try {
            innerSend(report);
        }catch (IllegalArgumentException e) {
            //do nothing
        }
    }

    private void innerSend(Report report) throws IOException {
        counter.incrementTotal();

        Map<String, Object> preparedMap = policy.apply(build(report));

        XContentBuilder builder = jsonBuilder().startObject();
        for(Map.Entry<String, Object> entry: preparedMap.entrySet()) {
            builder.field(entry.getKey(), entry.getValue());
        }
        builder.endObject();

        String indexName = this.buildNameFromDate(report.getDate());

        try {
            //IndexResponse response =
              client.prepareIndex(indexName, "logs", report.getId().toString())
                    .setSource(builder).setTimeout(TimeValue.timeValueMillis(requestTimeoutInMs))
                    .setCreate(true)
                    .execute()
                    .actionGet();

            counter.incrementIndexed();

        }catch (Exception e) {
            counter.incrementFailed();
            dump.record(report, e);
            throw new IllegalArgumentException(e);
        }
    }

    private Map<String, Object> build(Report report) {

        Map<String, Object> map = new HashMap<>();

        map.put(Fields.ID, report.getId());
        map.put(Fields.DATE, report.getDate());
        map.put(Fields.SENDER, report.getSender());
        map.put(Fields.TYPE, report.getType());
        map.put(Fields.CONTENT, report.getContent());

        return map;
    }

    private String buildNameFromDate(long date) {

        return indexFormat.format(date);
    }

    public Report testSend(Report report) throws IOException {
        this.innerSend(report);
        return report;
    }

    public SNSStats stats() {
        return counter.getStats();
    }


    public SNSDump dump(String className) {

        Collection<Failure> failures = dump.getFailures();
        List<SNSFailure> views = new ArrayList<>(failures.size());
        for(Failure failure: failures) {
            if(className.equals("*")) {
                views.add(new SNSFailure(failure.getFailedObject(), ExceptionUtils.getStackTrace(failure.getException())));
            }
            else {
                if(failure.getException().getClass().toString().contains(className)) {
                    views.add(new SNSFailure(failure.getFailedObject(), ExceptionUtils.getStackTrace(failure.getException())));
                }
            }
        }
        return new SNSDump(className, views);

    }

}
