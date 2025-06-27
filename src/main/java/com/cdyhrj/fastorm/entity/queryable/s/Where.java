package com.cdyhrj.fastorm.entity.queryable.s;

import com.cdyhrj.fastorm.condition.AndCondition;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.queryable.context.ToSqlContext;

public class Where<T extends Entity> extends AndCondition<T, EntityByClassQueryable<T>> {
    private final EntityByClassQueryable<T> host;

    public Where(ToSqlContext<T, EntityByClassQueryable<T>> context, EntityByClassQueryable<T> queryable) {
        super(context);

        this.host = queryable;
    }

    @Override
    public EntityByClassQueryable<T> ret() {
        return host;
    }
}
