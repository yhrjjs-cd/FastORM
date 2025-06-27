package com.cdyhrj.fastorm.entity.insertable.by_object;

import com.cdyhrj.fastorm.FastORM;
import com.cdyhrj.fastorm.entity.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class EntityInsertableTest {
    @Autowired
    private FastORM fastORM;

    @Test
    void tesEntityInsertExec() {
        Student student = new Student();

        int count = fastORM.insertable(student)
                .insert();

        Assertions.assertEquals(1, count);
    }

    @Test
    void tesEntityInsertExecReturnId() {
        Student student = new Student();

        Long id = fastORM.insertable(student)
                .insertReturnId();

        Assertions.assertTrue(id > 0, "Id should be greater than 0");
    }
}
