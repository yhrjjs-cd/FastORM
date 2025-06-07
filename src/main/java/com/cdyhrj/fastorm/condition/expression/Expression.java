package com.cdyhrj.fastorm.condition.expression;

import com.cdyhrj.fastorm.meta.SqlSegment;
import com.cdyhrj.fastorm.parameter.ParamMap;
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
}
