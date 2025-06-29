package com.cdyhrj.fastorm.entity.queryablex.order_by;

import com.cdyhrj.fastorm.api.lambda.LambdaQuery;
import com.cdyhrj.fastorm.api.lambda.PropFn;
import com.cdyhrj.fastorm.api.meta.FieldInfo;
import com.cdyhrj.fastorm.api.meta.SqlSegment;
import com.cdyhrj.fastorm.api.order.Order;
import com.cdyhrj.fastorm.condition.ConditionHost;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.context.ContextAware;
import com.cdyhrj.fastorm.entity.context.TableAvailable;
import com.cdyhrj.fastorm.entity.context.ToSqlContext;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * OrderBy自己
 *
 * @author <a href="mailto:huangqi@cdyhrj.com">黄奇</a>
 */
public class OrderBy<E extends Entity, H extends ConditionHost<E>> implements SqlSegment, ContextAware<E, H> {
    private final List<String> segments = new ArrayList<>();
    private final H queryable;
    private final ToSqlContext<E, H> context;

    @Override
    public ToSqlContext<E, H> getContext() {
        return context;
    }

    public OrderBy(ToSqlContext<E, H> context, H queryable) {
        this.context = context;
        this.queryable = queryable;
    }

    public OrderBy<E, H> add(PropFn<E, ?> propFn) {
        FieldInfo fi = LambdaQuery.resolve(propFn);
        TableAvailable ta = getContext().getTableEntity(fi.getEntityClass().getName());

        return add("%s.%s".formatted(ta.getAlias(), fi.getName()));
    }

    public OrderBy<E, H> add(PropFn<E, ?> propFn, Order order) {
        FieldInfo fi = LambdaQuery.resolve(propFn);
        TableAvailable ta = getContext().getTableEntity(fi.getEntityClass().getName());

        return add("%s.%s %s".formatted(ta.getAlias(), fi.getName(), order));
    }

    public OrderBy<E, H> add(String segment) {
        this.segments.add(segment);

        return this;
    }

    public OrderBy<E, H> add(String field, Order order) {
        return add("%s %s".formatted(field, order));
    }


    @Override
    public String toSql() {
        return "ORDER BY " + StringUtils.join(segments, ",");
    }

    public H ret() {
        return queryable;
    }
}
