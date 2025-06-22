package com.cdyhrj.fastorm;

import com.cdyhrj.fastorm.annotation.Column;
import com.cdyhrj.fastorm.annotation.Id;
import com.cdyhrj.fastorm.annotation.Table;
import com.cdyhrj.fastorm.entity.BaseEntity;
import lombok.Data;


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
