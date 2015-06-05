package org.ametiste.notification.elastic.preprocess;

import java.util.Map;

/**
 * Created by Daria on 04.06.2015.
 */
public interface ReportNamingPolicy {

    Map<String,Object> apply(Map<String, Object> map);
}
