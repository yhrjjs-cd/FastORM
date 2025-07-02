package com.cdyhrj.fast.orm.condition.expression;

import com.cdyhrj.fast.orm.api.meta.NoAliasSqlSegment;
import com.cdyhrj.fast.orm.api.meta.SqlSegment;
import com.cdyhrj.fast.orm.api.parameter.ParamMap;
import org.springframework.lang.NonNull;

/**
 * 条件表达式
 *
 * @author huangqi
 */
public interface Expression extends SqlSegment, NoAliasSqlSegment {
    /**
     * 参数值写入参数Map
     *
     * @param paramMap 已经存在参数Map
     */
    void writeToParamMap(@NonNull ParamMap paramMap);
}
