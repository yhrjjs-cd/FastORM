package com.cdyhrj.fast.orm.condition.enums;

import lombok.Getter;

/**
 * 表达式逻辑
 *
 * @author huangqi
 */

public enum Logic {
    And("AND"),
    Or("OR");

    Logic(String value) {
        this.value = value;
    }

    @Getter
    private String value;
}
