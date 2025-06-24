/*
 * 成都依何软件有限公司
 * (From 2024)
 */

package com.cdyhrj.fastorm.entity.insertable.by_object;

import com.cdyhrj.fastorm.annotation.enums.OperationType;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.EntityProxy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Map;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class EntityInsertable<E extends Entity> {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final TransactionTemplate transactionTemplate;
    private final E entity;
    private final EntityProxy entityProxy;

    public EntityInsertable(NamedParameterJdbcOperations namedParameterJdbcOperations, TransactionTemplate transactionTemplate, E entity) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
        this.transactionTemplate = transactionTemplate;
        this.entity = entity;
        this.entityProxy = Entity.getEntityProxy(entity.getClass());
    }


    /**
     * 执行插入操作
     */
    public int exec() {
        entityProxy.updateEntityWithDefaultValue(entity, OperationType.INSERT);

        Map<String, Object> paramMap = entityProxy.getValueMap(entity);
        String sqlText = SqlHelper.generateInsertSqlText(entityProxy, entity);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rows = this.namedParameterJdbcOperations.update(sqlText, new MapSqlParameterSource(paramMap), keyHolder);

        Number key = keyHolder.getKey();
        if (Objects.nonNull(key)) {
            entityProxy.updateEntityId(entity, key.longValue());
        }

        return rows;
    }

    public Long execReturnId() {
        exec();

        return this.entityProxy.getIdValue(entity);
    }
}
