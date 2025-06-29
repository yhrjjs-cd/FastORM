package com.cdyhrj.fastorm.sql.where;

import com.cdyhrj.fastorm.api.meta.SqlSegment;
import com.cdyhrj.fastorm.api.parameter.ParamMap;
import org.springframework.lang.NonNull;

public interface Item extends SqlSegment {
    /**
     * 参数值写入参数Map
     *
     * @param paramMap 已经存在参数Map
     */
    void writeToParamMap(@NonNull ParamMap paramMap);
}
