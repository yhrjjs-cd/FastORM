package com.cdyhrj.fastorm.tests;

import com.cdyhrj.fastorm.FastORM;
import com.cdyhrj.fastorm.entity.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class InsertableTestCase {
    @Autowired
    private FastORM fastORM;

    @Test
    void tesEntityInsertExec() {
        Student student = new Student();

        int count = fastORM.insertable(student)
                .exec();

        Assertions.assertEquals(1, count);
    }

    @Test
    void tesEntityInsertExecReturnId() {
        Student student = new Student();

        Long id = fastORM.insertable(student)
                .execReturnId();

        Assertions.assertTrue(id > 0, "Id should be greater than 0");
    }

    @Test
    void tesEntityInsertExecReturnName() {
        Student student = new Student();
        student.setCode("NAME");

        String name = fastORM.insertable(student)
                .execReturnName();

        Assertions.assertEquals("NAME", name, "Code should be equal to 'NAME'");
    }
}
