package com.cdyhrj.fast.orm.entity.queryable.where;

import com.cdyhrj.fast.orm.condition.AndCondition;
import com.cdyhrj.fast.orm.condition.ConditionHost;
import com.cdyhrj.fast.orm.entity.Entity;
import com.cdyhrj.fast.orm.entity.context.ToSqlContext;

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
