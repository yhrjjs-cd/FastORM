package com.cdyhrj.fastorm.entity;

import com.cdyhrj.fastorm.annotation.Column;
import com.cdyhrj.fastorm.annotation.Name;
import com.cdyhrj.fastorm.annotation.Table;
import lombok.Data;

@Data
@Table(name = "test_mail")
public class MailMan extends BaseEntity {
    @Name
    @Column
    private String code;

    @Column
    private String name;
}
