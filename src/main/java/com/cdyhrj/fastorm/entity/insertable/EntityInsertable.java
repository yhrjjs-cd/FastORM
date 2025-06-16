/*
 * 成都依何软件有限公司
 * (From 2024)
 */

package com.cdyhrj.fastorm.entity.insertable;

import com.cdyhrj.fastorm.api.lambda.LambdaColumn;
import com.cdyhrj.fastorm.api.lambda.PropFn;
import com.cdyhrj.fastorm.entity.Entity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.lang.NonNull;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class EntityInsertable<E extends Entity> {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final TransactionTemplate transactionTemplate;
    private final E entity;

    /**
     * 关系
     */
    private List<String> relations;

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
     * 插入列，不包含的列不插入
     *
     * @param columns 多列
     * @return 当前对象
     */
    @SafeVarargs
    public final EntityInsertable<E> withColumns(@NonNull PropFn<E, ?>... columns) {
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
    public final EntityInsertable<E> ignoreColumns(@NonNull PropFn<E, ?>... columns) {
        if (Objects.isNull(this.ignoreColumns)) {
            this.ignoreColumns = new ArrayList<>();
        }

        for (final PropFn<E, ?> column : columns) {
            this.ignoreColumns.add(LambdaColumn.resolve(column).getName());
        }

        return this;
    }

    /**
     * 插入关系 <font color="red">请注意：1次只能插入1个关系</font>
     *
     * @param relation 关系名
     * @return 当前对象
     */
    public EntityInsertable<E> withRelation(@NonNull PropFn<E, ?> relation) {
        if (Objects.isNull(this.relations)) {
            this.relations = new ArrayList<>();
        }

        this.relations.add(LambdaColumn.resolve(relation).getName());

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
}
