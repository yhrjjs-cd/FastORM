package com.cdyhrj.fast.orm.api.func;

import com.cdyhrj.fast.orm.api.lambda.PropFn;
import com.cdyhrj.fast.orm.entity.Entity;
import com.cdyhrj.fast.orm.entity.context.ToSqlContext;

@FunctionalInterface
public interface JoinFunc<S extends Entity, T extends Entity> {
    String on(ToSqlContext<?,?> context, PropFn<S, ?> sourcePropFn, PropFn<T, ?> targetPropFn);
}
