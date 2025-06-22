package com.cdyhrj.fastorm.condition;


import com.cdyhrj.fastorm.api.lambda.PropFn;
import com.cdyhrj.fastorm.condition.enums.Operator;
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
                                                        String alias, PropFn<E, ?> keyFn,
                                                        Object value) {
        return UnaryExpression.of(context, alias, keyFn, Operator.EQ, value);
    }

    public static <E extends Entity> UnaryExpression eq(@NonNull ToSqlContext<?, ?> context,
                                                        String sourceAlias, PropFn<E, ?> sourceKeyFn,
                                                        String targetAlias, PropFn<E, ?> targetKeyFn) {
//        return UnaryExpression.of(context, alias, keyFn, Operator.EQ, value);
        return null;
    }
}
