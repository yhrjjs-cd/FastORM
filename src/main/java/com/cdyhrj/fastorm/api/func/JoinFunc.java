package com.cdyhrj.fastorm.api.func;

import com.cdyhrj.fastorm.api.lambda.PropFn;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.queryable.context.ToSqlContext;

@FunctionalInterface
public interface JoinFunc<S extends Entity, T extends Entity> {
    String on(ToSqlContext<?,?> context, PropFn<S, ?> sourcePropFn, PropFn<T, ?> targetPropFn);
}
