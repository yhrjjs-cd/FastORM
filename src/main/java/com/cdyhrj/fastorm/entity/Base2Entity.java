package com.cdyhrj.fastorm.entity;

import com.cdyhrj.fastorm.annotation.Column;
import lombok.Data;

@Data
public class Base2Entity implements Entity {
    @Column
    private String name;

    @Column
    private String code;
}
