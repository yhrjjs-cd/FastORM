/*
 * 成都依何软件有限公司
 * (From 2024)
 */

package com.cdyhrj.fastorm.lambda.exception;

public class MethodNameException extends RuntimeException {
    public MethodNameException(String methodName) {
        super(methodName + " must start with 'is|get|set'");
    }
}
