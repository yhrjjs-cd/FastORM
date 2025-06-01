//package com.cdyhrj.fastorm.condition.expression;
//
//import com.cdyhrj.fastorm.condition.Condition;
//import com.cdyhrj.fastorm.condition.Exps;
//import com.cdyhrj.fastorm.condition.enums.Logic;
//import com.cdyhrj.fastorm.entity.Entity;
//import com.cdyhrj.fastorm.lambda.LambdaColumn;
//import com.cdyhrj.fastorm.lambda.PropFn;
//import com.cdyhrj.fastorm.meta.FieldInfo;
//import lombok.extern.slf4j.Slf4j;
//
///**
// * And条件组
// *
// * @author huangqi
// */
//@Slf4j
//public class AndExpressGroup extends ExpressGroup implements Condition {
//    public AndExpressGroup() {
//        this.logic = Logic.And;
//    }
//
//    public AndExpressGroup add(Expression expression) {
//        this.expessionList.add(expression);
//
//        return this;
//    }
//
//    public <E extends Entity> AndExpressGroup andEqual(PropFn<E, ?> propFn, Object value) {
//        FieldInfo fieldInfo = LambdaColumn.resolve(propFn);
//        log.info("fieldInfo:{}", fieldInfo.getEntityClass());
//
//        return this.add(Exps.equalTo(null, propFn, value));
//    }
//}
