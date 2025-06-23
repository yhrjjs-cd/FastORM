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
    void tesEntityInsert() {
        Student student = new Student();
        Assertions.assertNull(student.getId());

        fastORM.insertable(student)
                .exec();

        Assertions.assertNotNull(student.getId());
    }
}
