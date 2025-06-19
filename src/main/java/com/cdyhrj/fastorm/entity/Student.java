package com.cdyhrj.fastorm.entity;

import com.cdyhrj.fastorm.annotation.Column;
import com.cdyhrj.fastorm.annotation.Id;
import com.cdyhrj.fastorm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@Table(name = "test_student")
public class Student extends BaseEntity {
    @Column
    @Id
    private Long id;

    @Column
    private String code;

    @Column
    private String name;
}
