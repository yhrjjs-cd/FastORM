//package com.cdyhrj.fastorm.condition.expression;
//
//import com.cdyhrj.fastorm.condition.enums.Operator;
//import com.cdyhrj.fastorm.entity.Entity;
//import com.cdyhrj.fastorm.lambda.LambdaColumn;
//import com.cdyhrj.fastorm.lambda.PropFn;
//import com.cdyhrj.fastorm.queryable.context.TableEntity;
//import com.cdyhrj.fastorm.meta.FieldInfo;
//
//public class UnaryExpression extends AbstractUnaryExpression {
//    protected Operator operator;
//
//    public static <E extends Entity> UnaryExpression of(TableEntity<?> aliasEntity, PropFn<E, ?> field, Operator operator, Object value) {
//        FieldInfo fieldInfo = LambdaColumn.resolve(field);
//
//        return new UnaryExpression(aliasEntity, fieldInfo.getName(), operator, value);
//    }
//
//    public UnaryExpression(TableEntity<?> aliasEntity, String field, Operator operator, Object value) {
//        super(aliasEntity, field, value);
//
//        this.operator = operator;
//    }
//
//    @Override
//    public String toSql() {
//        return "%s %s".formatted(this.aliasEntity.getAlias(), this);
//    }
//}
