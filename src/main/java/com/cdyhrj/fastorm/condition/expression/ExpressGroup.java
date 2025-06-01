//package com.cdyhrj.fastorm.condition.expression;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 条件组
// *
// * @author huangqi
// */
//public abstract class ExpressGroup implements Expression {
//    protected List<Expression> expessionList = new ArrayList<>();
//
////    @Override
////    public String toParametricExpression(PropertyToFieldNameMap propertyToFieldNameMap) {
////        if (exps.isEmpty()) {
////            return Condition.DEFAULT_CONDITION_TRUE;
////        }
////
////        return "(" +
////                exps.stream()
////                        .map(exp -> exp.toParametricExpression(propertyToFieldNameMap))
////                        .collect(Collectors.joining(String.format(" %s ", logic.getValue()))) +
////                ")";
////    }
////
////    @Override
////    public void writeToParamMap(ParamMap paramMap) {
////        for (Expression expression : exps) {
////            expression.writeToParamMap(paramMap);
////        }
////    }
////
////    @Override
////    public void resolveTable(Map<String, Table> tableMap) {
////        // 没有意义，只是为了实现接口
////    }
////
////    public ExpressGroup resolveTableMap(Map<String, Table> tableMap) {
////        this.tableMap = tableMap;
////
////        return this;
////    }
////
////    @Override
////    public String toSql() {
////        return "(" + exps.stream().map(exp -> {
////            exp.resolveTable(tableMap);
////            return exp.toSql();
////        }).collect(Collectors.joining(" %s ".formatted(logic.getValue()))) + ")";
////    }
//}
