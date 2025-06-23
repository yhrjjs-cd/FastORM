package com.cdyhrj.fastorm.api.func;

import com.cdyhrj.fastorm.entity.Entity;

@FunctionalInterface
public interface FnBefore<E extends Entity> {
    void apply(E entity);
}
