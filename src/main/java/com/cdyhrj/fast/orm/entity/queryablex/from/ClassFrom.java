package com.cdyhrj.fast.orm.entity.queryablex.from;

import com.cdyhrj.fast.orm.entity.Entity;

public class ClassFrom implements From {
    public static ClassFrom of(Class<? extends Entity> entityClass) {
        return new ClassFrom(entityClass);
    }

    private final Class<? extends Entity> entityClass;

    public ClassFrom(Class<? extends Entity> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public String getContent() {
        return entityClass.getSimpleName();
    }
}
