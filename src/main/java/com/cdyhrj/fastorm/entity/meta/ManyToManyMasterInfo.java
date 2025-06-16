package com.cdyhrj.fastorm.entity.meta;

import com.cdyhrj.fastorm.entity.Entity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 多对多元数据
 *
 * @author huangqi
 */
@Data
@Accessors(fluent = true)
public class ManyToManyMasterInfo {
    /**
     * 关联类
     */
    private Class<Entity> relationClass;
}
