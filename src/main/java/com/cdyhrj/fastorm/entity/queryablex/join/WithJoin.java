package com.cdyhrj.fastorm.entity.queryablex.join;

import com.cdyhrj.fastorm.api.lambda.LambdaQuery;
import com.cdyhrj.fastorm.api.lambda.PropFn;
import com.cdyhrj.fastorm.api.meta.FieldInfo;
import com.cdyhrj.fastorm.api.meta.SqlSegment;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.context.TableAvailable;
import com.cdyhrj.fastorm.entity.context.ToSqlContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Join 项
 *
 * @param <E> Joins类，主实体类
 */
@Slf4j
public class WithJoin<E extends Entity> implements Join {
    private final JoinType joinType;
    private final ToSqlContext<?, ?> context;
    private final Joins<E> joins;
    private final List<OnItem> items = new ArrayList<>();
    private final String nameOfWith;

    public static <E extends Entity> WithJoin<E> of(JoinType joinType, Joins<E> joins, String nameOfWith) {
        return new WithJoin<>(joinType, joins, nameOfWith);
    }

    private WithJoin(JoinType joinType, Joins<E> joins, String nameOfWith) {
        this.joinType = joinType;
        this.context = joins.getContext();
        this.joins = joins;
        this.nameOfWith = nameOfWith;
    }

    public WithJoin<E> onEq(PropFn<E, ?> leftFn, String nameOfWithField) {
        FieldInfo left = LambdaQuery.resolve(leftFn);
        TableAvailable leftTable = getLeftTable();
        TableAvailable rightTable = context.getTableEntity(nameOfWith);

        this.items.add(OnItem2.of(leftTable.getAlias(), left.getName(), rightTable.getAlias(), nameOfWithField));

        return this;
    }

    private TableAvailable getLeftTable() {
        return context.getPrimaryEntity();
    }

    public Joins<E> end() {
        return joins;
    }

    @Override
    public String toSql() {
        TableAvailable target = context.getTableEntity(nameOfWith);

        return "%s %s ON %s".formatted(joinType.getSqlString(), target.toSql(), getItemsSql());
    }

    private String getItemsSql() {
        Assert.notEmpty(this.items, "At least one item is required");

        return this.items.stream().map(SqlSegment::toSql)
                .collect(Collectors.joining(" AND "));
    }
}
