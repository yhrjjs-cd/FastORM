package com.cdyhrj.fastorm.queryable;

import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.lambda.PropFun;
import com.cdyhrj.fastorm.queryable.inner.Join;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 连接
     */
    private List<Join<?>> joins = new ArrayList<>();


    public Queryable(Class<E> entityClazz) {
        this.entityClazz = entityClazz;
    }

    public <E1 extends Entity> Queryable<E> join(Class<E1> targetClass, On<E, E1> on) {
        joins.add(Join.of(targetClass, on));

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
