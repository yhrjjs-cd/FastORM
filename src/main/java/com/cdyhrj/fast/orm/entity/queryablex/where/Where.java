package com.cdyhrj.fast.orm.entity.queryablex.where;

import com.cdyhrj.fast.orm.entity.Entity;
import com.cdyhrj.fast.orm.entity.context.ToSqlContext;
import com.cdyhrj.fast.orm.entity.queryablex.EntityByClassQueryableX;

public class Where<E extends Entity, H extends EntityByClassQueryableX<E>> extends AndConditionX<E, H> {
    private final H host;

    public Where(ToSqlContext<E, H> context, H host) {
        super(context);

        this.host = host;
    }

    public H ret() {
        return host;
    }
}
