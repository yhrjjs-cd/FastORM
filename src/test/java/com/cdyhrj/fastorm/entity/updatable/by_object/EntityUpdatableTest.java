package com.cdyhrj.fastorm.entity.updatable.by_object;

import com.cdyhrj.fastorm.FastORM;
import com.cdyhrj.fastorm.entity.Student;
import com.cdyhrj.fastorm.exception.KeyValueRequiredException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class EntityUpdatableTest {
    @Autowired
    private FastORM fastORM;

    @Test
    void tesEntityUpdateExecWithoutIdAndName() {
        Assertions.assertThrows(KeyValueRequiredException.class, () -> {
            Student student = new Student();
            fastORM.updatable(student)
                    .exec();
        });
    }

    @Test
    void tesEntityUpdateExecById() {
        Student student = new Student();
        student.setId(100);

        int rows = fastORM.updatable(student)
                .exec();

        Assertions.assertEquals(0, rows);
    }

    @Test
    void tesEntityUpdateExecByName() {
        Student student = new Student();
        student.setCode("Code");

        int rows = fastORM.updatable(student)
                .exec();

        Assertions.assertEquals(0, rows);
    }
}