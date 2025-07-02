package com.cdyhrj.fast.orm.entity.base;

import com.cdyhrj.fast.orm.api.chain.Chain;
import com.cdyhrj.fast.orm.api.lambda.PropFn;
import com.cdyhrj.fast.orm.condition.ConditionHost;
import com.cdyhrj.fast.orm.entity.Entity;
import org.springframework.lang.NonNull;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * Set Param while get entity by class
 */
public class AbstractEntityByClassParamSetter<E extends Entity, H extends ConditionHost<E>> {
    protected Chain<E> paramChain;

    /**
     * 设置参数
     *
     * @param keyFun 字段函数
     * @param value  值
     * @return 当前对象
     */
    @SuppressWarnings("unchecked")
    public <R extends Number> H set(@NonNull PropFn<E, R> keyFun, R value) {
        if (Objects.isNull(paramChain)) {
            paramChain = Chain.make(keyFun, value);
        } else {
            paramChain.add(keyFun, value);
        }

        return (H) this;
    }

    /**
     * 设置参数
     *
     * @param keyFun 字段函数
     * @param value  值
     * @return 当前对象
     */
    @SuppressWarnings("unchecked")
    public <R extends String> H set(@NonNull PropFn<E, R> keyFun, R value) {
        if (Objects.isNull(paramChain)) {
            paramChain = Chain.make(keyFun, value);
        } else {
            paramChain.add(keyFun, value);
        }

        return (H) this;
    }


    /**
     * 设置参数
     *
     * @param keyFun 字段函数
     * @param value  值
     * @return 当前对象
     */
    @SuppressWarnings("unchecked")
    protected H set(@NonNull PropFn<E, ?> keyFun, Object value) {
        if (Objects.isNull(paramChain)) {
            paramChain = Chain.make(keyFun, value);
        } else {
            paramChain.add(keyFun, value);
        }

        return (H) this;
    }

    protected Map<String, Object> paramChainToMap() {
        if (Objects.isNull(this.paramChain)) {
            return Collections.emptyMap();
        }

        return this.paramChain.toParamMap();
    }
}
