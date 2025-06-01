package com.cdyhrj.fastorm.condition;


import com.cdyhrj.fastorm.condition.enums.Operator;
import com.cdyhrj.fastorm.condition.expression.UnaryExpression;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.lambda.PropFn;
import com.cdyhrj.fastorm.queryable.context.Context;

/**
 * 辅助函数
 *
 * @author huangqi
 */

public class Exps {
    private Exps() {

    }

    public static <E extends Entity> UnaryExpression eq(Context<?> context, String alias, PropFn<E, ?> keyFn, Object value) {
        return UnaryExpression.of(context, alias, keyFn, Operator.EQ, value);
    }
}
