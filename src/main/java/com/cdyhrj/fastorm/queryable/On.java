package com.cdyhrj.fastorm.queryable;

import com.cdyhrj.fastorm.entity.Entity;

public class On<S extends Entity, T extends Entity> {
    public static <S extends Entity, T extends Entity> On<S, T> of(Class<S> sourceClazz, Class<T> targetClazz) {
        return new On<>(sourceClazz, targetClazz);
    }

    private Class<S> sourceClazz;
    private Class<T> targetClazz;
//    private PropFun<S, ?> sourceKeyFun;
//    private PropFun<T, ?> targetKeyFun;

    private On() {

    }

    private On(Class<S> sourceClazz, Class<T> targetClazz) {
//        this.sourceKeyFun = sourceKeyFun;
//        this.targetKeyFun = targetKeyFun;
    }

}
