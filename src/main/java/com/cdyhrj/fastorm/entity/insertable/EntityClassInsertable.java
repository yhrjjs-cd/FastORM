/*
 * 成都依何软件有限公司
 * (From 2024)
 */

package com.cdyhrj.fastorm.entity.insertable;

import com.cdyhrj.fastorm.api.chain.Chain;
import com.cdyhrj.fastorm.api.lambda.LambdaColumn;
import com.cdyhrj.fastorm.api.lambda.PropFn;
import com.cdyhrj.fastorm.config.FastOrmConfig;
import com.cdyhrj.fastorm.entity.Entity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.lang.NonNull;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class EntityClassInsertable<E extends Entity> {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final TransactionTemplate transactionTemplate;
    private final FastOrmConfig fastOrmConfig;

    protected Chain<E> paramChain;

    /**
     * 插入列
     */
    @Getter
    private List<String> withColumns;

    /**
     * 忽略列
     */
    @Getter
    private List<String> ignoreColumns;

    /**
     * 批量处理每次数量
     */
    private int batchSize = fastOrmConfig.getBatchSize();

    /**
     * 插入列，不包含的列不插入
     *
     * @param columns 多列
     * @return 当前对象
     */
    @SafeVarargs
    public final EntityClassInsertable<E> withColumns(@NonNull PropFn<E, ?>... columns) {
        if (Objects.isNull(this.withColumns)) {
            this.withColumns = new ArrayList<>();
        }

        for (final PropFn<E, ?> column : columns) {
            this.withColumns.add(LambdaColumn.resolve(column).getName());
        }

        return this;
    }

    /**
     * 忽略列, 不插入
     *
     * @param columns 多列
     * @return 当前对象
     */
    @SafeVarargs
    public final EntityClassInsertable<E> ignoreColumns(@NonNull PropFn<E, ?>... columns) {
        if (Objects.isNull(this.ignoreColumns)) {
            this.ignoreColumns = new ArrayList<>();
        }

        for (final PropFn<E, ?> column : columns) {
            this.ignoreColumns.add(LambdaColumn.resolve(column).getName());
        }

        return this;
    }

    /**
     * 设置批量处理大小
     *
     * @param batchSize 批量大小
     * @return 当前对象
     */
    public EntityClassInsertable<E> batchSize(int batchSize) {
        this.batchSize = batchSize;

        return this;
    }


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


    public void exec() {

    }

    public Long execReturnId() {
        return 0L;
    }

    public String execReturnName() {
        return "";
    }


    public void execMap(Map<String, Object> valueMap) {

    }

    public void execList(List<Map<String, Object>> valueList) {

    }
}
