package com.cdyhrj.fastorm.entity.queryable.where;

import com.cdyhrj.fastorm.condition.AndCondition;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.queryable.EntityQueryable;
import com.cdyhrj.fastorm.entity.queryable.context.ToSqlContext;

public class Where<T extends Entity> extends AndCondition<T, EntityQueryable<T>> {
    private final EntityQueryable<T> host;

    public Where(ToSqlContext<T, EntityQueryable<T>> context, EntityQueryable<T> queryable) {
        super(context);

        this.host = queryable;
    }

    @Override
    public EntityQueryable<T> ret() {
        return host;
    }
}
