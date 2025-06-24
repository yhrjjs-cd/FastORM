package com.cdyhrj.fastorm.entity.insertable.by_class;

import com.cdyhrj.fastorm.annotation.enums.OperationType;
import com.cdyhrj.fastorm.api.chain.Chain;
import com.cdyhrj.fastorm.api.lambda.PropFn;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.EntityProxy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.lang.NonNull;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class EntityClassInsertable<E extends Entity> {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final TransactionTemplate transactionTemplate;
    private final Class<E> entityClass;

    protected Chain<E> paramChain;

    /**
     * 设置参数
     *
     * @param keyFun 字段函数
     * @param value  值
     * @return 当前对象
     */
    public <R extends Number> EntityClassInsertable<E> set(@NonNull PropFn<E, R> keyFun, R value) {
        if (Objects.isNull(paramChain)) {
            paramChain = Chain.make(keyFun, value);
        } else {
            paramChain.add(keyFun, value);
        }

        return this;
    }

    /**
     * 设置参数
     *
     * @param keyFun 字段函数
     * @param value  值
     * @return 当前对象
     */
    public <R extends String> EntityClassInsertable<E> set(@NonNull PropFn<E, R> keyFun, R value) {
        if (Objects.isNull(paramChain)) {
            paramChain = Chain.make(keyFun, value);
        } else {
            paramChain.add(keyFun, value);
        }

        return this;
    }

    public int exec() {
        EntityProxy entityProxy = Entity.getEntityProxy(entityClass);

        Map<String, Object> paramMap = entityProxy.getDefaultValueMap(OperationType.INSERT);
        paramMap.putAll(this.paramChainToMap());
        String sqlText = SqlHelper.generateInsertSqlText(entityProxy, paramMap);

        return this.namedParameterJdbcOperations.update(sqlText, new MapSqlParameterSource(paramMap));
    }

    public Long execReturnId() {
        EntityProxy entityProxy = Entity.getEntityProxy(entityClass);

        Map<String, Object> paramMap = entityProxy.getDefaultValueMap(OperationType.INSERT);
        paramMap.putAll(this.paramChainToMap());
        String sqlText = SqlHelper.generateInsertSqlText(entityProxy, paramMap);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.namedParameterJdbcOperations.update(sqlText, new MapSqlParameterSource(paramMap), keyHolder);

        Number key = Objects.requireNonNull(keyHolder.getKey(), "Error in getting key");

        return key.longValue();
    }


    private Map<String, Object> paramChainToMap() {
        if (Objects.isNull(this.paramChain)) {
            return Collections.emptyMap();
        }

        return this.paramChain.toParamMap();
    }
}
