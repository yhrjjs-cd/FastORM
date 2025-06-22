package com.cdyhrj.fastorm.entity.deletable.by_class;

import com.cdyhrj.fastorm.condition.AndCondition;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.queryable.context.ToSqlContext;
import com.cdyhrj.fastorm.entity.updatable.by_class.EntityClassUpdatable;

public class Where<T extends Entity> extends AndCondition<T, EntityClassDeletable<T>> {
    private final EntityClassDeletable<T> host;

    public Where(ToSqlContext<T, EntityClassDeletable<T>> context, EntityClassDeletable<T> entityClassDeletable) {
        super(context);

        this.host = entityClassDeletable;
    }

    @Override
    public EntityClassDeletable<T> ret() {
        return this.host;
    }
}
