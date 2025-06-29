package com.cdyhrj.fastorm.entity.queryablex.where;

import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.context.ToSqlContext;
import com.cdyhrj.fastorm.entity.queryablex.EntityByClassQueryableX;

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
