//package com.cdyhrj.fastorm.condition;
//
//
//import com.cdyhrj.fastorm.condition.enums.Operator;
//import com.cdyhrj.fastorm.condition.expression.ExpressGroup;
//import com.cdyhrj.fastorm.condition.expression.UnaryExpression;
//import com.cdyhrj.fastorm.entity.Entity;
//import com.cdyhrj.fastorm.lambda.PropFn;
//import com.cdyhrj.fastorm.queryable.context.TableEntity;
//
///**
// * 辅助函数
// *
// * @author huangqi
// */
//
//public class Exps {
//    private Exps() {
//
//    }
//
//    public static ExpressGroup and() {
//        return ExpressGroup.and();
//    }
//
//
//    public static <E extends Entity> UnaryExpression equalTo(TableEntity<?> aliasEntity, PropFn<E, ?> keyFn, Object value) {
//        return UnaryExpression.of(aliasEntity, keyFn, Operator.EQ, value);
//    }
//}
