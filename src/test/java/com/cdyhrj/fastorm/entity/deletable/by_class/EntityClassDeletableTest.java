package com.cdyhrj.fastorm.entity.deletable.by_class;

import com.cdyhrj.fastorm.FastORM;
import com.cdyhrj.fastorm.entity.entity.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EntityClassDeletableTest {
    @Autowired
    private FastORM fastORM;

    @Test
    void testExecById() {
        int rows = fastORM.deletable(Student.class)
                .id(100L)
                .execById();

        Assertions.assertTrue(rows >= 0);
    }

    @Test
    void testExecByName() {
        int rows = fastORM.deletable(Student.class)
                .name("Name")
                .execByName();

        Assertions.assertTrue(rows >= 0);
    }

    @Test
    void testExecByWhere() {
        int rows = fastORM.deletable(Student.class)
                .where()
                .andEq(Student::getName, "Name")
                .andEq(Student::getCode, "Name")
                .ret()
                .execByWhere();

        Assertions.assertTrue(rows >= 0);
    }
}