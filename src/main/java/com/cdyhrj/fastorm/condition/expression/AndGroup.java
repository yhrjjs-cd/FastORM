//package com.cdyhrj.fastorm.condition.expression;
//
//import com.cdyhrj.fastorm.condition.Exps;
//import com.cdyhrj.fastorm.condition.enums.Logic;
//import com.cdyhrj.fastorm.entity.Entity;
//import com.cdyhrj.fastorm.lambda.LambdaColumn;
//import com.cdyhrj.fastorm.lambda.PropFn;
//import com.cdyhrj.fastorm.meta.FieldInfo;
//import com.cdyhrj.fastorm.queryable.context.Context;
//import lombok.extern.slf4j.Slf4j;
//
///**
// * And条件组
// *
// * @author huangqi
// */
//@Slf4j
//public class AndGroup extends ExpressGroup {
//    protected Context<?> context;
//
//    public AndGroup() {
//        this.logic = Logic.And;
//    }
//
//    @Override
//    public void setContext(Context<?> context) {
//        this.context = context;
//    }
//
//    public AndGroup add(Expression expression) {
//        this.expessionList.add(expression);
//
//        return this;
//    }
//
//    public <E extends Entity> AndGroup andEqual(PropFn<E, ?> propFn, Object value) {
//        FieldInfo fieldInfo = LambdaColumn.resolve(propFn);
//
//        return this.add(Exps.equalTo(null, propFn, value));
//    }
//
//    @Override
//    public String toSql() {
//        return "ddddddddddddddddd";
//    }
//}
