package com.cdyhrj.fastorm.condition;

import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.queryable.EntityQueryable;
import com.cdyhrj.fastorm.entity.queryable.context.ToSqlContext;

public class Where<T extends Entity> extends AndCondition<T> {
    public Where(ToSqlContext<T> context, EntityQueryable<T> queryable) {
        super(context, queryable);
    }
}
