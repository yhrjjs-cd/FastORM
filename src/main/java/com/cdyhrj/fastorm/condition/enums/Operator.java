package com.cdyhrj.fastorm.condition.enums;

import lombok.Getter;

/**
 * 操作符
 *
 * @author huangqi
 */

public enum Operator {
    EQ("="),
    NEQ("!="),
    GT(">"),
    GET(">="),
    LT("<"),
    LET("<="),
    LIKE("LIKE"),
    START_WITH("LIKE"),
    END_WITH("LIKE"),
    IN_LONG("IN"),
    IN_STRING("IN"),
    IN_SQL("IN");

    Operator(String value) {
        this.value = value;
    }

    @Getter
    private final String value;
}
