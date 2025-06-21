package com.cdyhrj.fastorm.entity.updatable.by_object;

import com.cdyhrj.fastorm.condition.AndCondition;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.queryable.context.ToSqlContext;

public class Where<T extends Entity> extends AndCondition<T> {
    public Where(ToSqlContext<T> context, EntityUpdatable<T> entityUpdatable) {
        super(context, entityUpdatable);
    }
}
