package com.cdyhrj.fastorm.queryable.inner;

import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.queryable.On;
import lombok.Data;

@Data
public class Join<E extends Entity> {
    public static <T extends Entity> Join<T> of(Class<T> targetClass, On<?, T> on) {
        Join<T> join = new Join<>();
        join.setTargetClass(targetClass);
        join.setOn(on);

        return join;
    }

    private Class<E> targetClass;
    private On<?, E> on;
}
