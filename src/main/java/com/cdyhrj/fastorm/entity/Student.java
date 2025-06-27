package com.cdyhrj.fastorm.entity;

import com.cdyhrj.fastorm.annotation.Column;
import com.cdyhrj.fastorm.annotation.Id;
import com.cdyhrj.fastorm.annotation.Name;
import com.cdyhrj.fastorm.annotation.Table;
import lombok.Data;

@Data
@Table(name = "test_student")
public class Student extends BaseEntity {
    @Id
    @Column
    private long id;

    @Name
    @Column
    private String code;

    @Column
    private String name;
}
