/*
 * 成都依何软件有限公司
 * (From 2024)
 */

package com.cdyhrj.fast.orm.api.lambda.exception;

public class ColumnAnnotationRequiredException extends RuntimeException {
    public ColumnAnnotationRequiredException(String propertyName) {
        super("@Column is required for property %s".formatted(propertyName));
    }
}
