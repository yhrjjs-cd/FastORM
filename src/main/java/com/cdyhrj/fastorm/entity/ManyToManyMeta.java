package com.cdyhrj.fastorm.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 多对多元数据
 *
 * @author huangqi
 */
@Data
@Accessors(fluent = true)
public class ManyToManyMeta {
    /**
     * 左列字段
     */
    private String leftFieldName;

    /**
     * 右列字段
     */
    private String rightFieldName;
}
