package com.cdyhrj.fastorm.entity.deletable.by_object;

import com.cdyhrj.fastorm.condition.AndCondition;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.queryable.context.ToSqlContext;

public class Where<T extends Entity> extends AndCondition<T, EntityDeletable<T>> {
    private final EntityDeletable<T> host;

    public Where(ToSqlContext<T, EntityDeletable<T>> context, EntityDeletable<T> entityDeletable) {
        super(context);

        this.host = entityDeletable;
    }

    @Override
    public EntityDeletable<T> ret() {
        return this.host;
    }
}
