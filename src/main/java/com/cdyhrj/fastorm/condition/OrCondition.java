package com.cdyhrj.fastorm.condition;

import com.cdyhrj.fastorm.api.lambda.PropFn;
import com.cdyhrj.fastorm.condition.expression.AbstractOrExpressionGroup;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.queryable.EntityQueryable;
import com.cdyhrj.fastorm.entity.queryable.context.ToSqlContext;
import org.springframework.lang.NonNull;

public class OrCondition<T extends Entity> extends AbstractOrExpressionGroup<T> {
    private final EntityQueryable<T> queryable;

    public OrCondition(@NonNull ToSqlContext<T> context, EntityQueryable<T> queryable) {
        super(context);

        this.queryable = queryable;
    }

    public <E extends Entity> OrCondition<T> orEq(PropFn<E, ?> propFn, Object value) {
        super.orEq(propFn, value);

        return this;
    }

    public <E extends Entity> OrCondition<T> orEq(String alias, PropFn<E, ?> propFn, Object value) {
        super.orEq(alias, propFn, value);

        return this;
    }

    public EntityQueryable<T> ret() {
        return queryable;
    }
}
