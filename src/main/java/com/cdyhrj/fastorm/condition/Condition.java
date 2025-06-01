package com.cdyhrj.fastorm.condition;

import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.lambda.PropFn;
import com.cdyhrj.fastorm.meta.SqlNode;
import com.cdyhrj.fastorm.parameter.ParamMap;

/**
 * 条件接口
 */
public interface Condition<T extends Entity> extends SqlNode {
    <E extends Entity> Condition<T> andEq(PropFn<E, ?> propFn, Object value);

    <E extends Entity> Condition<T> andEq(String alias, PropFn<E, ?> propFn, Object value);

    /**
     * 写入参数Map
     *
     * @param paramMap 参数Map
     */
    void writeToParamMap(ParamMap paramMap);
}
