/*
 * 成都依何软件有限公司
 * (From 2024)
 */

/*
 * 成都依何软件有限公司
 * (From 2024)
 */

package com.cdyhrj.fast.orm.exception;

public class EntityClassEnhanceException extends RuntimeException {
    public EntityClassEnhanceException(String enhanceClassName) {
        super("Enhance base entity error, class name: " + enhanceClassName);
    }
}
