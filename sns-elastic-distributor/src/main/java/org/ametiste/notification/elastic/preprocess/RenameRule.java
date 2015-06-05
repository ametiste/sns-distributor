package org.ametiste.notification.elastic.preprocess;

import org.ametiste.notification.elastic.preprocess.RenameCondition;

/**
 * Created by Daria on 04.06.2015.
 */
public class RenameRule {

    private String expression;
    private String replacement;
    private RenameCondition condition;


    public RenameRule(String expression, String replacement, RenameCondition condition) {
        this.expression = expression;
        this.replacement = replacement;
        this.condition = condition;
    }

    public String getExpression() {
        return expression;
    }

    public String getReplacement() {
        return replacement;
    }

    public RenameCondition getCondition() {
        return condition;
    }
}
