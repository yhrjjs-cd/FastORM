package com.cdyhrj.fastorm.entity.queryable.join;

import com.cdyhrj.fastorm.api.func.JoinFunc;
import com.cdyhrj.fastorm.api.lambda.LambdaColumn;
import com.cdyhrj.fastorm.api.lambda.PropFn;
import com.cdyhrj.fastorm.api.meta.FieldInfo;
import com.cdyhrj.fastorm.api.meta.SqlSegment;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.queryable.EntityQueryable;
import com.cdyhrj.fastorm.entity.queryable.context.TableAvailable;
import com.cdyhrj.fastorm.entity.queryable.context.ToSqlContext;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class Join<T extends Entity, L extends Entity, R extends Entity> implements SqlSegment {
    public static <T extends Entity, L extends Entity, R extends Entity> Join<T, L, R> of(
            ToSqlContext<T> context,
            JoinType joinType,
            Class<L> sourceEntityClass,
            Class<R> targetEntityClass) {
        Join<T, L, R> newInstance = new Join<>();

        newInstance.context = context;
        newInstance.joinType = joinType;
        newInstance.targetEntityKey = targetEntityClass.getName();

        context.addEntity(targetEntityClass);

        return newInstance;
    }

    public static <T extends Entity, L extends Entity, R extends Entity> Join<T, L, R> of(
            ToSqlContext<T> context,
            JoinType joinType,
            Class<R> targetEntityClass) {
        Join<T, L, R> newInstance = new Join<>();

        newInstance.context = context;
        newInstance.joinType = joinType;
        newInstance.targetEntityKey = targetEntityClass.getName();

        context.addEntity(targetEntityClass);

        return newInstance;
    }


    private ToSqlContext<T> context;
    private JoinType joinType;
    private String targetEntityKey;

    private final List<OnItem> items = new ArrayList<>();

    public EntityQueryable<T> ret() {
        return context.getBelongTo();
    }

    /**
     * 添加标准条件
     *
     * @param criteria 条件
     * @return Join对象
     */
    public Join<T, L, R> andCri(String criteria) {
        StringOnItem item = StringOnItem.of(criteria);
        items.add(item);

        return this;
    }

    public Join<T, L, R> andCri(PropFn<L, ?> sourcePropFn, PropFn<R, ?> targetPropFn, JoinFunc<L, R> fn) {
        return andCri(fn.on(context, sourcePropFn, targetPropFn));
    }

    /**
     * 相等
     *
     * @param sourceFieldFun 源字段函数
     * @param targetFieldFun 目标字段函数
     * @return Join对象
     */
    public Join<T, L, R> andEq(PropFn<L, ?> sourceFieldFun, PropFn<R, ?> targetFieldFun) {
        FieldInfo sourceFiled = LambdaColumn.resolve(sourceFieldFun);
        TableAvailable sourceEntity = context.getTableEntity(sourceFiled.getEntityClass().getName());

        FieldInfo targetFiled = LambdaColumn.resolve(targetFieldFun);
        TableAvailable targetEntity = context.getTableEntity(targetFiled.getEntityClass().getName());

        return andEq(sourceEntity.getAlias(), sourceFiled.getName(), targetEntity.getAlias(), targetFiled.getName());
    }

    /**
     * 添加相等项
     *
     * @param targetFieldFun 目标字段函数
     * @param value          字段值
     * @return Join对象
     */
    public Join<T, L, R> andEq(PropFn<R, ?> targetFieldFun, Object value) {
        FieldInfo targetFiled = LambdaColumn.resolve(targetFieldFun);
        TableAvailable targetEntity = context.getTableEntity(targetFiled.getEntityClass().getName());

        return andEq(targetEntity.getAlias(), targetFiled.getName(), value);
    }

    @Override
    public String toSql() {
        TableAvailable target = context.getTableEntity(targetEntityKey);

        return "%s %s ON %s".formatted(joinType.getSqlString(), target.toSql(), getItemsSql());
    }

    private String getItemsSql() {
        return this.items.stream().map(SqlSegment::toSql)
                .collect(Collectors.joining(" AND "));
    }

    /**
     * 相等
     *
     * @param sourceTableAlias 源表别名
     * @param sourceFieldName  源字段
     * @param targetTableAlias 目标表别名
     * @param targetFieldName  目标字段
     * @return Join对象
     */
    private Join<T, L, R> andEq(String sourceTableAlias, String sourceFieldName, String targetTableAlias, String targetFieldName) {
        OnItem2 item = OnItem2.of(sourceTableAlias, sourceFieldName, targetTableAlias, targetFieldName);
        items.add(item);

        return this;
    }

    /**
     * 添加相等项
     *
     * @param tableAlias 表别名
     * @param fieldName  字段名
     * @param value      字段值
     * @return Join对象
     */
    private Join<T, L, R> andEq(String tableAlias, String fieldName, Object value) {
        OnItem1 item = OnItem1.of(tableAlias, fieldName, value);
        items.add(item);

        return this;
    }
}
