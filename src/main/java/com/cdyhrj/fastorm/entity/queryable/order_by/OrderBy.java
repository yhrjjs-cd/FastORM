package com.cdyhrj.fastorm.entity.queryable.order_by;

import com.cdyhrj.fastorm.api.lambda.LambdaQuery;
import com.cdyhrj.fastorm.api.lambda.PropFn;
import com.cdyhrj.fastorm.api.meta.FieldInfo;
import com.cdyhrj.fastorm.api.meta.SqlSegment;
import com.cdyhrj.fastorm.api.order.Order;
import com.cdyhrj.fastorm.condition.ConditionHost;
import com.cdyhrj.fastorm.entity.Entity;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * OrderBy自己
 *
 * @author <a href="mailto:huangqi@cdyhrj.com">黄奇</a>
 */
public class OrderBy<E extends Entity, H extends ConditionHost<E>> implements SqlSegment {
    private final List<String> segments = new ArrayList<>();
    private final H queryable;

    public OrderBy(H queryable) {
        this.queryable = queryable;
    }

    public OrderBy<E, H> add(PropFn<E, ?> propFn) {
        FieldInfo fi = LambdaQuery.resolve(propFn);

        return add(fi.getName());
    }

    public OrderBy<E, H> add(PropFn<E, ?> propFn, Order order) {
        FieldInfo fi = LambdaQuery.resolve(propFn);

        return add("%s %s".formatted(fi.getName(), order));
    }

    private OrderBy<E, H> add(String segment) {
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
