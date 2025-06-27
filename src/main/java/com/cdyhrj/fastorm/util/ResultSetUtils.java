package com.cdyhrj.fastorm.util;

import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.EntityProxy;

import java.sql.ResultSet;

/**
 * ResultSet辅助类
 *
 * @author huangqi
 */
public class ResultSetUtils {
    public static <E> E toEntity(ResultSet rs, Class<E> classOfT) {
        EntityProxy entityProxy = Entity.getEntityProxy(classOfT);

        return entityProxy.updateEntity(rs);
    }
}
