package com.cdyhrj.fastorm.entity.queryablex;

import com.cdyhrj.fastorm.FastORM;
import com.cdyhrj.fastorm.entity.MailMan;
import com.cdyhrj.fastorm.entity.Student;
import com.cdyhrj.fastorm.entity.queryablex.join.JoinType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class EntityByClassQueryableXTest {
    @Autowired
    private FastORM fastORM;

    @Test
    void queryTest() {
        List<Student> students = fastORM.queryableX(Student.class)
                .joins()
                .addJoin(JoinType.INNER, MailMan.class, "XX1")
                .onEq(Student::getId, MailMan::getId)
                .end()
                .ret()
                .where()
                .andEq(Student::getCode, "that")
                .andEq("XX1", MailMan::getCode, "that")
                .ret()
                .query();

//        Assertions.assertFalse(students.isEmpty());
    }
}