package com.cdyhrj.fastorm.entity.support.where;

import com.cdyhrj.fastorm.condition.AndCondition;
import com.cdyhrj.fastorm.condition.ConditionHost;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.context.ToSqlContext;

public class Where<E extends Entity, H extends ConditionHost<E>> extends AndCondition<E, H> {
    private final H host;

    public Where(ToSqlContext<E, H> context, H host) {
        super(context);

        this.host = host;
    }

    public H ret() {
        return host;
    }
}
