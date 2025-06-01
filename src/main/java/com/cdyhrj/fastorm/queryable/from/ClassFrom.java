package com.cdyhrj.fastorm.queryable.from;

import com.cdyhrj.fastorm.entity.Entity;

public class ClassFrom implements From {
    public static ClassFrom of(Class<? extends Entity> entityClass) {
        return new ClassFrom(entityClass);
    }

    private Class<? extends Entity> entityClass;

    public ClassFrom(Class<? extends Entity> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public String getName() {
        return entityClass.getSimpleName();
    }
}
