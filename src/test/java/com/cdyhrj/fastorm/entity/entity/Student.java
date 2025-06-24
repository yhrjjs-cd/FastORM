package com.cdyhrj.fastorm.entity.entity;

import com.cdyhrj.fastorm.annotation.Column;
import com.cdyhrj.fastorm.annotation.Id;
import com.cdyhrj.fastorm.annotation.Name;
import com.cdyhrj.fastorm.annotation.Table;
import com.cdyhrj.fastorm.entity.BaseEntity;
import lombok.Data;


@Data
@Table(name = "test_student")
public class Student extends BaseEntity {
    @Id
    @Column
    private int id;

    @Name
    @Column
    private String code;
}
