package com.cdyhrj.fastorm.entity;

import com.cdyhrj.fastorm.annotation.Column;
import lombok.Data;

@Data
public class Student implements Entity {
    @Column
    private int id;

    @Column
    private String code;

    @Column
    private String name;
}
