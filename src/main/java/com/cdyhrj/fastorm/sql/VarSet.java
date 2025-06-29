package com.cdyhrj.fastorm.sql;

import java.util.HashMap;
import java.util.Map;

/**
 * 变量集合
 *
 * @author huangqi
 */
public class VarSet {
    private Map<String, String> values = new HashMap<>();

    public void add(String name, String value) {
        this.values.put(name, value);
    }

    public String get(String name) {
        return this.values.get(name);
    }

    /**
     * 是否包含var
     *
     * @param name 名称
     * @return true/false
     */
    public boolean hasVar(String name) {
        return this.values.containsKey(name);
    }
}
