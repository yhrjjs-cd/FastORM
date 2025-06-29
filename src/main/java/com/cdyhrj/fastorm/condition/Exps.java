package com.cdyhrj.fastorm.condition;


import com.cdyhrj.fastorm.api.lambda.PropFn;
import com.cdyhrj.fastorm.condition.enums.Operator;
import com.cdyhrj.fastorm.condition.expression.Between;
import com.cdyhrj.fastorm.condition.expression.IsNull;
import com.cdyhrj.fastorm.condition.expression.Like;
import com.cdyhrj.fastorm.condition.expression.UnaryExpression;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.queryable.context.ToSqlContext;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;

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

    public static <E extends Entity> Like like(@NonNull ToSqlContext<?, ?> context,
                                               String alias,
                                               PropFn<E, String> keyFn,
                                               String value) {
        return Like.of(context, alias, keyFn, value, "%", "%");
    }

    public static <E extends Entity> Between<LocalDateTime> between(@NonNull ToSqlContext<?, ?> context,
                                                                    String alias,
                                                                    PropFn<E, LocalDateTime> keyFn,
                                                                    LocalDateTime value1,
                                                                    LocalDateTime value2) {
        return Between.of(context, alias, keyFn, value1, value2);
    }

    public static <E extends Entity> Between<LocalDate> between(@NonNull ToSqlContext<?, ?> context,
                                                                String alias,
                                                                PropFn<E, LocalDate> keyFn,
                                                                LocalDate value1,
                                                                LocalDate value2) {
        return Between.of(context, alias, keyFn, value1, value2);
    }

    public static <E extends Entity> Between<YearMonth> between(@NonNull ToSqlContext<?, ?> context,
                                                                String alias,
                                                                PropFn<E, YearMonth> keyFn,
                                                                YearMonth value1,
                                                                YearMonth value2) {
        return Between.of(context, alias, keyFn, value1, value2);
    }

    public static <E extends Entity> Between<Year> between(@NonNull ToSqlContext<?, ?> context,
                                                           String alias,
                                                           PropFn<E, Year> keyFn,
                                                           Year value1,
                                                           Year value2) {
        return Between.of(context, alias, keyFn, value1, value2);
    }

    public static <E extends Entity> Between<Number> between(@NonNull ToSqlContext<?, ?> context,
                                                             String alias,
                                                             PropFn<E, Number> keyFn,
                                                             Number value1,
                                                             Number value2) {
        return Between.of(context, alias, keyFn, value1, value2);
    }
}
