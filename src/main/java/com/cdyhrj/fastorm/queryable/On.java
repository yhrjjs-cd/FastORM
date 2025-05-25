package com.cdyhrj.fastorm.queryable;

import com.cdyhrj.fastorm.entity.Entity;

public class On<S extends Entity, T extends Entity> {
    public static <S extends Entity, T extends Entity> On<S, T> of(Class<S> sourceClass, Class<T> targetClass) {
        return new On<>(sourceClass, targetClass);
    }

    private Class<S> sourceClass;
    private Class<T> targetClass;

    private On(Class<S> sourceClass, Class<T> targetClass) {
        this.sourceClass = sourceClass;
        this.targetClass = targetClass;
    }
}
