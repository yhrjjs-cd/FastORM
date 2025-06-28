package com.cdyhrj.fastorm.entity.insertable.by_class;

import com.cdyhrj.fastorm.annotation.enums.OperationType;
import com.cdyhrj.fastorm.api.chain.Chain;
import com.cdyhrj.fastorm.condition.ConditionHost;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.EntityProxy;
import com.cdyhrj.fastorm.entity.base.AbstractEntityByClassParamSetter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.lang.NonNull;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Map;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class EntityClassInsertable<E extends Entity> extends AbstractEntityByClassParamSetter<E, EntityClassInsertable<E>> implements ConditionHost<E> {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final TransactionTemplate transactionTemplate;
    private final Class<E> entityClass;

    /**
     * 设置参数, 仅仅为内部使用
     *
     * @param key   字段
     * @param value 值
     * @return 当前对象
     */
    public EntityClassInsertable<E> set(@NonNull String key, Long value) {
        if (Objects.isNull(paramChain)) {
            paramChain = Chain.make(key, value);
        } else {
            paramChain.add(key, value);
        }

        return this;
    }

    public int insert() {
        EntityProxy entityProxy = Entity.getEntityProxy(entityClass);

        Map<String, Object> paramMap = entityProxy.getDefaultValueMap(OperationType.INSERT);
        paramMap.putAll(this.paramChainToMap());
        String sqlText = SqlHelper.generateInsertSqlText(entityProxy, paramMap);

        return this.namedParameterJdbcOperations.update(sqlText, new MapSqlParameterSource(paramMap));
    }

    public Long insertReturnId() {
        EntityProxy entityProxy = Entity.getEntityProxy(entityClass);

        Map<String, Object> paramMap = entityProxy.getDefaultValueMap(OperationType.INSERT);
        paramMap.putAll(this.paramChainToMap());
        String sqlText = SqlHelper.generateInsertSqlText(entityProxy, paramMap);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.namedParameterJdbcOperations.update(sqlText, new MapSqlParameterSource(paramMap), keyHolder);

        Number key = Objects.requireNonNull(keyHolder.getKey(), "Error in getting key");

        return key.longValue();
    }
}
