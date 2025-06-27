package com.cdyhrj.fastorm.entity.deletable.by_object;

import com.cdyhrj.fastorm.FastORM;
import com.cdyhrj.fastorm.entity.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EntityDeletableTest {
    @Autowired
    private FastORM fastORM;

    @Test
    void testExecById() {
        Student student = new Student();
        student.setId(1L);
        int rows = fastORM.deletable(student)
                .execById();

        Assertions.assertTrue(rows >= 0);
    }

    @Test
    void testExecByName() {
        Student student = new Student();
        student.setCode("That");
        int rows = fastORM.deletable(student)
                .execByName();

        Assertions.assertTrue(rows >= 0);
    }
}