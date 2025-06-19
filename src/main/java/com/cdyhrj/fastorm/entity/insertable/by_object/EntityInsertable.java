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

    /**
     * 执行插入操作
     *
     * @return 插入的对象
     */
    public E exec() {
        EntityProxy entityProxy = Entity.getEntityProxy(entity.getClass());
        entityProxy.updateEntityWithDefaultValue(entity, OperationType.INSERT);

        entity.beforeInsert();

        Map<String, Object> paramMap = entityProxy.getValueMap(entity);

        String sqlText = SqlHelper.generateInsertSqlText(entityProxy, entity);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.namedParameterJdbcOperations.update(sqlText, new MapSqlParameterSource(paramMap), keyHolder);

        Number key = keyHolder.getKey();
        if (Objects.nonNull(key)) {
            entityProxy.updateEntityId(entity, key.longValue());
        }

        entity.afterInsert();

        return entity;
    }
}
