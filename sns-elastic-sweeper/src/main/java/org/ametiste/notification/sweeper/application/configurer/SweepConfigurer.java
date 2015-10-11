package org.ametiste.notification.sweeper.application.configurer;

import java.util.Map;

/**
 * Created by ametiste on 7/1/15.
 */
public interface SweepConfigurer {

    Map<String, String> loadAll();
    void update(Map<String, Object> configs);
    boolean contains(String name);
    String get(String name);

}
