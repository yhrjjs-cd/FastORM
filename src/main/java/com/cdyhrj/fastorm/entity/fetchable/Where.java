package com.cdyhrj.fastorm.entity.fetchable;

import com.cdyhrj.fastorm.condition.AndCondition;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.queryable.context.ToSqlContext;

public class Where<T extends Entity> extends AndCondition<T, EntityByClassFetchable<T>> {
    private final EntityByClassFetchable<T> host;

    public Where(ToSqlContext<T, EntityByClassFetchable<T>> context, EntityByClassFetchable<T> entityClassUpdatable) {
        super(context);

        this.host = entityClassUpdatable;
    }

    @Override
    public EntityByClassFetchable<T> ret() {
        return this.host;
    }
}
