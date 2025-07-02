package com.cdyhrj.fast.orm.entity.queryablex.order_by;

import com.cdyhrj.fast.orm.api.lambda.LambdaQuery;
import com.cdyhrj.fast.orm.api.lambda.PropFn;
import com.cdyhrj.fast.orm.api.meta.FieldInfo;
import com.cdyhrj.fast.orm.api.meta.SqlSegment;
import com.cdyhrj.fast.orm.api.order.Order;
import com.cdyhrj.fast.orm.entity.Entity;
import com.cdyhrj.fast.orm.entity.context.ContextAware;
import com.cdyhrj.fast.orm.entity.context.TableAvailable;
import com.cdyhrj.fast.orm.entity.context.ToSqlContext;
import com.cdyhrj.fast.orm.entity.queryablex.EntityByClassQueryableX;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * OrderBy自己
 *
 * @author <a href="mailto:huangqi@cdyhrj.com">黄奇</a>
 */
public class OrderBy<E extends Entity, H extends EntityByClassQueryableX<E>> implements SqlSegment, ContextAware<E, H> {
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

    public <T extends Entity> OrderBy<E, H> addX(PropFn<T, ?> propFn) {
        return addX(propFn, Order.ASC);
    }

    public <T extends Entity> OrderBy<E, H> addX(String alias, PropFn<T, ?> propFn) {
        return addX(alias, propFn, Order.ASC);
    }


    public <T extends Entity> OrderBy<E, H> addX(PropFn<T, ?> propFn, Order order) {
        FieldInfo fi = LambdaQuery.resolve(propFn);
        TableAvailable ta = getContext().getTableEntity(fi.getEntityClass().getName());

        return add("%s.%s %s".formatted(ta.getAlias(), fi.getName(), order));
    }

    public <T extends Entity> OrderBy<E, H> addX(String alias, PropFn<T, ?> propFn, Order order) {
        FieldInfo fi = LambdaQuery.resolve(propFn);

        return add("%s.%s %s".formatted(alias, fi.getName(), order));
    }

    public OrderBy<E, H> add(PropFn<E, ?> propFn) {
        return add(propFn, Order.ASC);
    }

    public OrderBy<E, H> add(String alias, PropFn<E, ?> propFn) {
        return add(alias, propFn, Order.ASC);
    }

    public OrderBy<E, H> add(PropFn<E, ?> propFn, Order order) {
        FieldInfo fi = LambdaQuery.resolve(propFn);
        TableAvailable ta = getContext().getTableEntity(fi.getEntityClass().getName());

        return add("%s.%s %s".formatted(ta.getAlias(), fi.getName(), order));
    }

    public OrderBy<E, H> add(String alias, PropFn<E, ?> propFn, Order order) {
        FieldInfo fi = LambdaQuery.resolve(propFn);

        return add("%s.%s %s".formatted(alias, fi.getName(), order));
    }

    private OrderBy<E, H> add(String segment) {
        this.segments.add(segment);

        return this;
    }

    private OrderBy<E, H> add(String field, Order order) {
        return add("%s %s".formatted(field, order));
    }


    @Override
    public String toSql() {
        if (segments.isEmpty()) {
            return "";
        }

        return "ORDER BY " + StringUtils.join(segments, ",");
    }

    public H ret() {
        return queryable;
    }
}
