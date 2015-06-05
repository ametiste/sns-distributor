package org.ametiste.notification.elastic.preprocess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Daria on 04.06.2015.
 */
public class FieldRenamingPolicy  implements ReportNamingPolicy{

    private List<RenameRule> rules;

    public FieldRenamingPolicy(List<RenameRule> rules) {
        this.rules = rules;
    }

    //TODO sweet ball of dirt :3
    @Override
    public Map<String, Object> apply(Map<String, Object> map) {
        Map<String, Object> applied = map;
        for(RenameRule rule: rules) {
            applied = this.applyRule(rule.getExpression(), rule.getReplacement(), rule.getCondition(), applied);
        }
        return applied;
    }

    private Map<String, Object> applyRule(String expression, String replacement, RenameCondition condition, Map<String, Object> map) {
        String[] expressionParts = expression.split("\\.", 2);
        if(expressionParts.length==1) {
            if(expressionParts[0].startsWith("*")) {
                String replaced = expressionParts[0].replace("*", "");
                return this.applyDeep(map, replaced, replacement, condition);
            }
            else {
                return this.applyRoot(map, expression, replacement, condition);
            }
        }
         else {
            if(expressionParts.length==0) {
                throw new IllegalArgumentException();
            }
            else {
                if(map.containsKey(expressionParts[0]) && Map.class.isAssignableFrom(map.get(expressionParts[0]).getClass())) {
                    Map<String, Object> applied = new HashMap<>(map);
                    applied.put(expressionParts[0], applyRule(expressionParts[1], replacement, condition, (Map<String, Object>) map.get(expressionParts[0])));
                    return applied;
                }

            }
        }
        return map;
    }

    private Map<String, Object> applyRoot(Map<String, Object> map, String expression, String replacement, RenameCondition condition) {
        if(map.containsKey(expression) && condition.renameRequired(map.get(expression))) {
            Object value = map.get(expression);
            Map<String, Object> applied = new HashMap<>(map);
            applied.remove(expression);
            applied.put(replacement, value);
            return applied;
        }
        else {
            return map;
        }
    }

    private Map<String, Object> applyDeep(Map<String, Object> map, String replaced, String replacement, RenameCondition condition) {

        Map<String, Object> applied = new HashMap<>();

        for(Map.Entry<String, Object> entry: map.entrySet()) {

            String key = entry.getKey();
            if(entry.getKey().equals(replaced) && condition.renameRequired(entry.getValue())) {
                key = replacement;
            }

            Object value = entry.getValue();
            if(Map.class.isAssignableFrom(entry.getValue().getClass())) {
                value = applyDeep((Map<String, Object>) entry.getValue(),replaced, replacement, condition );
            }
            applied.put(key, value);
        }

        return applied;
    }
}
