package com.cdyhrj.fastorm.entity.queryable.orderby;

import com.cdyhrj.fastorm.api.lambda.LambdaColumn;
import com.cdyhrj.fastorm.api.lambda.PropFn;
import com.cdyhrj.fastorm.api.meta.FieldInfo;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.queryable.EntityQueryable;
import com.cdyhrj.fastorm.entity.queryable.context.AbstractSqlSegmentContextAware;
import com.cdyhrj.fastorm.entity.queryable.context.TableAvailable;
import com.cdyhrj.fastorm.entity.queryable.context.ToSqlContext;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * OrderBy字句
 *
 * @author <a href="mailto:huangqi@cdyhrj.com">黄奇</a>
 */
public class OrderBy<E extends Entity> extends AbstractSqlSegmentContextAware<E> {
    private final List<String> segments = new ArrayList<>();
    private final EntityQueryable<E> queryable;

    public OrderBy(ToSqlContext<E> context, EntityQueryable<E> queryable) {
        super(context);

        this.queryable = queryable;
    }

    public <T extends Entity> OrderBy<E> add(PropFn<T, ?> propFn) {
        FieldInfo fi = LambdaColumn.resolve(propFn);
        TableAvailable ta = getContext().getTableEntity(fi.getEntityClass().getName());

        return add("%s.%s".formatted(ta.getAlias(), fi.getName()));
    }

    public <T extends Entity> OrderBy<E> add(PropFn<T, ?> propFn, Order order) {
        FieldInfo fi = LambdaColumn.resolve(propFn);
        TableAvailable ta = getContext().getTableEntity(fi.getEntityClass().getName());

        return add("%s.%s %s".formatted(ta.getAlias(), fi.getName(), order));
    }

    public OrderBy<E> add(String segment) {
        this.segments.add(segment);

        return this;
    }

    public OrderBy<E> add(String field, Order order) {
        return add("%s %s".formatted(field, order));
    }


    @Override
    public String toSql() {
        return "ORDER BY " + StringUtils.join(segments, ",");
    }

    public EntityQueryable<E> ret() {
        return queryable;
    }

    public enum Order {
        ASC, DESC
    }
}
