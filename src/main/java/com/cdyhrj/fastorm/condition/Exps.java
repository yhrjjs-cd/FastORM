package com.cdyhrj.fastorm.condition;


import com.cdyhrj.fastorm.api.lambda.PropFn;
import com.cdyhrj.fastorm.condition.enums.Operator;
import com.cdyhrj.fastorm.condition.expression.IsNull;
import com.cdyhrj.fastorm.condition.expression.UnaryExpression;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.queryable.context.ToSqlContext;
import org.springframework.lang.NonNull;

/**
 * 辅助函数
 *
 * @author huangqi
 */

public class Exps {
    private Exps() {

    }

    public static <E extends Entity> UnaryExpression eq(@NonNull ToSqlContext<?, ?> context,
                                                        String alias,
                                                        PropFn<E, ?> keyFn,
                                                        Object value) {
        return UnaryExpression.of(context, alias, keyFn, Operator.EQ, value);
    }

    public static <E extends Entity, N extends Number> UnaryExpression lt(@NonNull ToSqlContext<?, ?> context,
                                                                          String alias,
                                                                          PropFn<E, N> keyFn,
                                                                          N value) {
        return UnaryExpression.of(context, alias, keyFn, Operator.LT, value);
    }

    public static <E extends Entity, N extends Number> UnaryExpression lte(@NonNull ToSqlContext<?, ?> context,
                                                                           String alias,
                                                                           PropFn<E, N> keyFn,
                                                                           N value) {
        return UnaryExpression.of(context, alias, keyFn, Operator.LTE, value);
    }

    public static <E extends Entity, N extends Number> UnaryExpression gt(@NonNull ToSqlContext<?, ?> context,
                                                                          String alias,
                                                                          PropFn<E, N> keyFn,
                                                                          N value) {
        return UnaryExpression.of(context, alias, keyFn, Operator.GT, value);
    }

    public static <E extends Entity, N extends Number> UnaryExpression gte(@NonNull ToSqlContext<?, ?> context,
                                                                           String alias,
                                                                           PropFn<E, N> keyFn,
                                                                           N value) {
        return UnaryExpression.of(context, alias, keyFn, Operator.GTE, value);
    }

    public static <E extends Entity> IsNull isNull(@NonNull ToSqlContext<?, ?> context,
                                                   String alias,
                                                   PropFn<E, ?> keyFn) {
        return IsNull.of(context, alias, keyFn);
    }
}
