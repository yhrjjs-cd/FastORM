package com.cdyhrj.fastorm.condition;

import com.cdyhrj.fastorm.api.lambda.PropFn;
import com.cdyhrj.fastorm.condition.expression.AbstractAndExpressionGroup;
import com.cdyhrj.fastorm.condition.expression.OrExpressionGroup;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.queryable.EntityQueryable;
import com.cdyhrj.fastorm.entity.queryable.context.ToSqlContext;
import org.springframework.lang.NonNull;

public class AndCondition<T extends Entity> extends AbstractAndExpressionGroup<T> {
    private final EntityQueryable<T> queryable;

    public AndCondition(@NonNull ToSqlContext<T> context, EntityQueryable<T> queryable) {
        super(context);

        this.queryable = queryable;
    }

    public OrExpressionGroup<T> andOrGroup() {
        OrExpressionGroup<T> group =  new OrExpressionGroup<>(context, this);
        addExpression(group);

        return group;
    }

    @Override
    public <E extends Entity> AndCondition<T> andEq(PropFn<E, ?> propFn, Object value) {
        super.andEq(propFn, value);

        return this;
    }

    @Override
    public <E extends Entity> AndCondition<T> andEq(String alias, PropFn<E, ?> propFn, Object value) {
        super.andEq(alias, propFn, value);

        return this;
    }


    public EntityQueryable<T> ret() {
        return queryable;
    }
}
