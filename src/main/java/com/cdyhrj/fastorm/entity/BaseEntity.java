package com.cdyhrj.fastorm.entity;

import com.cdyhrj.fastorm.annotation.Column;
import com.cdyhrj.fastorm.annotation.Table;
import lombok.Data;

@Data
@Table(name = "base_entity")
public class BaseEntity implements Entity {
    @Column
    private String name;
}
