package org.ametiste.notification.elastic;

import org.ametiste.notification.SNSProcessor;
import org.ametiste.notification.elastic.dump.ErrorDump;
import org.ametiste.notification.elastic.preprocess.ReportNamingPolicy;
import org.ametiste.notification.model.Report;
import org.ametiste.notification.model.SNSDump;
import org.ametiste.notification.model.SNSFailure;
import org.ametiste.notification.model.SNSStats;
import org.apache.commons.lang.exception.ExceptionUtils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Daria on 03.06.2015.
 */
public class SNSClient {

    private SNSCounter counter;
    private DateFormat indexFormat;
    private SNSProcessor client;
    private ReportNamingPolicy policy;
    private ErrorDump dump = new ErrorDump();

    public SNSClient(SNSProcessor client, ReportNamingPolicy policy, String reportPrefix) {
        this.client = client;
        this.policy = policy;
        this.indexFormat = new SimpleDateFormat("'"+ reportPrefix + "-'yyyy-MM-dd");
        counter = new SNSCounter();

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

        String indexName = this.buildNameFromDate(report.getDate());

        try {
            //IndexResponse response =
            client.send(indexName, report.getId().toString(), preparedMap);
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

        List<SNSFailure> views = dump.getFailures().stream().filter(failure -> className.equals("*")
                || failure.getException().getClass().toString().contains(className))
                .map(failure -> new SNSFailure(failure.getFailedObject(),
                        ExceptionUtils.getStackTrace(failure.getException())))
                .collect(Collectors.toList());
        return new SNSDump(className, views);

    }

}
