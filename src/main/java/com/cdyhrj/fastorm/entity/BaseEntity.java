package com.cdyhrj.fastorm.entity;

import com.cdyhrj.fastorm.annotation.Column;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class BaseEntity implements Entity {
    /**
     * 系统更新字段
     */
    private static final Set<String> SYSTEM_UPDATE_FIELDS = Set.of("updated_by", "updated_by_name", "updated_at");

    @Column
    private LocalDateTime createdAt;

    @Column
    private Long createdBy;

    @Column
    private String createdByName;

    @Column
    private LocalDate updatedAt;

    @Override
    public void beforeInsert() {
        createdAt = LocalDateTime.now();
        createdBy = 1L;
        createdByName = "111";
    }

    @Override
    public void beforeUpdate() {
        updatedAt = LocalDate.now();
    }

    @Override
    public boolean fieldIsOnlyForUpdate(String fieldName) {
        return SYSTEM_UPDATE_FIELDS.contains(fieldName);
    }
}
