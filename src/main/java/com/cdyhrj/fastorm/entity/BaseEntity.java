package com.cdyhrj.fastorm.entity;

import com.cdyhrj.fastorm.annotation.Column;
import lombok.Data;

@Data
public class BaseEntity implements Entity {
    @Column
    private String name;
}
