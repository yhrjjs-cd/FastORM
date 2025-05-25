package com.cdyhrj.fastorm.queryable;

import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.lambda.PropFun;
import org.springframework.lang.NonNull;

/**
 * Queryable
 *
 * @author <a href="mailto:huangqi@cdyhrj.com">黄奇</a>
 */
public class Queryable<E extends Entity> {
    /**
     * 主实体类
     */
    private Class<E> entityClazz;

    public Queryable(Class<E> entityClazz) {
        this.entityClazz = entityClazz;
    }

    public <E1 extends Entity> Queryable<E> join(Class<E1> targetClazz, On<E, E1> on) {
        return this;
    }

    public <E1 extends Entity> Queryable<E> join(Class<E1> targetClazz, @NonNull PropFun<E, ?> sourceKeyFun, @NonNull PropFun<E1, ?> targetKeyFun) {
        On<E, E1> on = On.of(entityClazz, targetClazz);

        return this;
    }

    /**
     * 设置参数
     *
     * @param keyFun 字段函数
     * @param value  值
     * @return 当前对象
     */
    public Queryable<E> param(@NonNull PropFun<E, ?> keyFun, Object value) {

        return this;
    }
}
