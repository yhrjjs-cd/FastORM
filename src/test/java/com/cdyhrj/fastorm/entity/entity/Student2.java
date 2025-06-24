package com.cdyhrj.fastorm.entity.entity;

import com.cdyhrj.fastorm.annotation.Column;
import com.cdyhrj.fastorm.annotation.Table;
import com.cdyhrj.fastorm.entity.BaseEntity;
import lombok.Data;


@Data
@Table(name = "test_student")
public class Student2 extends Student {
    @Column
    private String name;
}
