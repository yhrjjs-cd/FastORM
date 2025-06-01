//package com.cdyhrj.fastorm.condition;
//
//import com.cdyhrj.fastorm.entity.Entity;
//import com.cdyhrj.fastorm.lambda.PropFn;
//import com.cdyhrj.fastorm.queryable.context.Context;
//
///**
// * 条件入口
// *
// * @author huangqi
// * @noinspection unused
// */
//
//public class Cnd {
//    public static AndCondition and(Context<?> context) {
//        return new AndCondition(context);
//    }
//
//    public static <E extends Entity> AndCondition andEqual(Context<?> context, PropFn<E, ?> propFn, Object value) {
//        return and(context).andEqual(propFn, value);
//    }
//}
