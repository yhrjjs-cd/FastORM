package com.cdyhrj.fastorm.entity.base;

import com.cdyhrj.fastorm.api.chain.Chain;
import com.cdyhrj.fastorm.api.lambda.PropFn;
import com.cdyhrj.fastorm.condition.ConditionHost;
import com.cdyhrj.fastorm.entity.Entity;
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
    public <R extends Number> H set(@NonNull PropFn<E, R> keyFun, R value) {
        if (Objects.isNull(paramChain)) {
            paramChain = Chain.make(keyFun, value);
        } else {
            paramChain.add(keyFun, value);
        }

        //noinspection unchecked
        return (H) this;
    }

    /**
     * 设置参数
     *
     * @param keyFun 字段函数
     * @param value  值
     * @return 当前对象
     */
    public <R extends String> H set(@NonNull PropFn<E, R> keyFun, R value) {
        if (Objects.isNull(paramChain)) {
            paramChain = Chain.make(keyFun, value);
        } else {
            paramChain.add(keyFun, value);
        }

        //noinspection unchecked
        return (H) this;
    }


    /**
     * 设置参数
     *
     * @param keyFun 字段函数
     * @param value  值
     * @return 当前对象
     */
    protected H set(@NonNull PropFn<E, ?> keyFun, Object value) {
        if (Objects.isNull(paramChain)) {
            paramChain = Chain.make(keyFun, value);
        } else {
            paramChain.add(keyFun, value);
        }

        //noinspection unchecked
        return (H) this;
    }

    protected Map<String, Object> paramChainToMap() {
        if (Objects.isNull(this.paramChain)) {
            return Collections.emptyMap();
        }

        return this.paramChain.toParamMap();
    }
}
