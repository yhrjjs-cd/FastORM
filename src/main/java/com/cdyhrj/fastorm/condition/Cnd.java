//package com.cdyhrj.fastorm.condition;
//
//import com.cdyhrj.fastorm.condition.expression.AndExpressGroup;
//import com.cdyhrj.fastorm.entity.Entity;
//import com.cdyhrj.fastorm.lambda.LambdaColumn;
//import com.cdyhrj.fastorm.lambda.PropFn;
//import com.cdyhrj.fastorm.meta.FieldInfo;
//
///**
// * 条件入口
// *
// * @author huangqi
// * @noinspection unused
// */
//
//public class Cnd {
//    public static AndExpressGroup and() {
//        return new AndExpressGroup();
//    }
//
//    public static <E extends Entity> AndExpressGroup andEqual(PropFn<E, ?> propFn, Object value) {
//        return and().andEqual(propFn, value);
//    }
//}
