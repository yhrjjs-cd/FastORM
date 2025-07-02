package com.cdyhrj.fast.orm.entity;

import com.cdyhrj.fast.orm.annotation.Column;
import com.cdyhrj.fast.orm.annotation.Name;
import com.cdyhrj.fast.orm.annotation.Table;
import lombok.Data;

@Data
@Table(name = "test_mail2")
public class MailMan2 extends BaseEntity {
    @Name
    @Column
    private String code;

    @Column
    private String name;
}
