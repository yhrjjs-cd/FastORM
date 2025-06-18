package com.cdyhrj.fastorm.entity;

import com.cdyhrj.fastorm.annotation.Column;
import com.cdyhrj.fastorm.annotation.Table;
import lombok.Data;

@Data
@Table(name = "ttt_student")
public class Student implements Entity {
    @Column
    private int id;

    @Column
    private String code;

    @Column
    private String name;
}
