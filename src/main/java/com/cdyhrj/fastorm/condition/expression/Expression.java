package com.cdyhrj.fastorm.condition.expression;

import com.cdyhrj.fastorm.api.meta.SqlSegment;
import com.cdyhrj.fastorm.api.parameter.ParamMap;
import com.cdyhrj.fastorm.entity.PropertyToFieldNameMap;
import org.springframework.lang.NonNull;

/**
 * 条件表达式
 *
 * @author huangqi
 */

public interface Expression extends SqlSegment {
    /**
     * 参数值写入参数Map
     *
     * @param paramMap 已经存在参数Map
     */
    void writeToParamMap(@NonNull ParamMap paramMap);
//
//    /**
//     * 参数化表达式
//     *
//     * @param propertyToFieldNameMap 属性字段名映射
//     * @return 表达式
//     */
//    String toParametricExpression(PropertyToFieldNameMap propertyToFieldNameMap);
}
