package org.ametiste.notification.sweeper.application.configurer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ametiste on 7/2/15.
 */
public class InMemSweepConfigurer implements SweepConfigurer {

    private Map<String, String> map;

    public InMemSweepConfigurer(Map<String, Object> defaults) {
        map = new HashMap<>();
        this.update(defaults);
    }

    @Override
    public Map<String, String> loadAll() {
        return Collections.unmodifiableMap(map);
    }

    @Override
    public void update(Map<String, Object> configs) {
        configs.entrySet().forEach(entry ->
                        map.put(entry.getKey(), entry.getValue().toString())
        );
    }

    @Override
    public boolean contains(String name) {
        return map.containsKey(name);
    }

    @Override
    public String get(String name) {
        if(contains(name)) {
            return map.get(name);
        }
        throw new IllegalArgumentException("no key exists or incompatible type");
    }
}
