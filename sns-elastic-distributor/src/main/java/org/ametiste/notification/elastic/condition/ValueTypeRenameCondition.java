package org.ametiste.notification.elastic.condition;

import org.ametiste.notification.elastic.preprocess.RenameCondition;

/**
 * Created by Daria on 05.06.2015.
 */
public class ValueTypeRenameCondition implements RenameCondition {

    private Class<?> type;

    public ValueTypeRenameCondition(Class<?> type) {

        this.type = type;
    }

    public boolean renameRequired(Object object) {
        return object != null && type.equals(object.getClass());
    }
}
