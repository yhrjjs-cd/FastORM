package com.cdyhrj.fastorm.entity.updatable.by_object;

import com.cdyhrj.fastorm.condition.AndCondition;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.queryable.context.ToSqlContext;

public class Where<T extends Entity> extends AndCondition<T, EntityUpdatable<T>> {
    private final EntityUpdatable<T> host;

    public Where(ToSqlContext<T, EntityUpdatable<T>> context, EntityUpdatable<T> entityUpdatable) {
        super(context);

        this.host = entityUpdatable;
    }

    @Override
    public EntityUpdatable<T> ret() {
        return this.host;
    }
}
