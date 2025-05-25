/*
 * 成都依何软件有限公司
 * (From 2024)
 */
package com.cdyhrj.fastorm.lambda.exception;

public class NotLambdaSyntheticClassException extends RuntimeException {
    public NotLambdaSyntheticClassException() {
        super("Must lambda Synthetic class");
    }
}
