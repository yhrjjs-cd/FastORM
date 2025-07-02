package com.cdyhrj.fast.orm.api.func;

import com.cdyhrj.fast.orm.entity.Entity;

@FunctionalInterface
public interface FnBefore<E extends Entity> {
    void apply(E entity);
}
