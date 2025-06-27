package com.cdyhrj.fastorm.util;

import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.EntityProxy;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import java.sql.ResultSet;

/**
 * 查询列表RowMapper
 *
 * @author huangqi
 */

public class QueryRowMapper<T> implements RowMapper<T> {
    public QueryRowMapper(Class<T> classOfT) {
        this.classOfT = classOfT;
    }

    /**
     * 对象类
     */
    private final Class<T> classOfT;

    @Override
    public T mapRow(@NonNull ResultSet rs, int rowNum) {
        EntityProxy entityProxy = Entity.getEntityProxy(classOfT);

        return entityProxy.updateEntity(rs);
    }
}
