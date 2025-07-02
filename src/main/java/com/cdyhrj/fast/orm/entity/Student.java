package com.cdyhrj.fast.orm.entity;

import com.cdyhrj.fast.orm.annotation.Column;
import com.cdyhrj.fast.orm.annotation.Name;
import com.cdyhrj.fast.orm.annotation.Table;
import lombok.Data;

@Data
@Table(name = "test_student")
public class Student extends BaseEntity {
    @Name
    @Column
    private String code;

    @Column
    private String name;
}
