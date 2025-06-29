package com.cdyhrj.fastorm.condition.enums;

/**
 * 操作符
 *
 * @author huangqi
 */

public enum Operator {
    EQ("="),
    NEQ("!="),
    GT(">"),
    GTE(">="),
    LT("<"),
    LTE("<="),
    LIKE("LIKE"),
    START_WITH("LIKE"),
    END_WITH("LIKE"),
    IN_LONG("IN"),
    IN_STRING("IN"),
    IN_SQL("IN"),
    BETWEEN("BETWEEN"),
    IS_NULL("IS NULL");

    Operator(String value) {
        this.value = value;
    }

    private final String value;

    @Override
    public String toString() {
        return value;
    }
}
