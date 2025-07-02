package com.cdyhrj.fast.orm.entity;

import com.cdyhrj.fast.orm.annotation.Column;
import com.cdyhrj.fast.orm.annotation.Id;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class BaseEntity implements Entity {
    /**
     * 系统插入字段
     */
    private static final Set<String> SYSTEM_INSERT_FIELDS = Set.of("created_by", "created_by_name", "created_at");

    /**
     * 系统更新字段
     */
    private static final Set<String> SYSTEM_UPDATE_FIELDS = Set.of("updated_by", "updated_by_name", "updated_at");

    @Id
    @Column
    private Long id;

    @Column
    private Long tenantId;

    @Column
    private LocalDateTime createdAt;

    @Column
    private Long createdBy;

    @Column
    private String createdByName;

    @Column
    private LocalDate updatedAt;

    @Column
    private Long updatedBy;

    @Column
    private String updatedByName;

    @Override
    public boolean fieldIsOnlyForInsert(String fieldName) {
        return SYSTEM_INSERT_FIELDS.contains(fieldName);
    }

    @Override
    public boolean fieldIsOnlyForUpdate(String fieldName) {
        return SYSTEM_UPDATE_FIELDS.contains(fieldName);
    }
}
