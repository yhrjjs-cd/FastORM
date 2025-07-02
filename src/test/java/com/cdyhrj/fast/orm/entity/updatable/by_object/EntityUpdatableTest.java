package com.cdyhrj.fast.orm.entity.updatable.by_object;

import com.cdyhrj.fast.orm.FastORM;
import com.cdyhrj.fast.orm.entity.Student;
import com.cdyhrj.fast.orm.exception.KeyValueRequiredException;
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
                    .update();
        });
    }

    @Test
    void tesEntityUpdateExecById() {
        Student student = new Student();
        student.setId(100L);

        int rows = fastORM.updatable(student)
                .update();

        Assertions.assertEquals(0, rows);
    }

    @Test
    void tesEntityUpdateExecByName() {
        Student student = new Student();
        student.setCode("Code");

        int rows = fastORM.updatable(student)
                .update();

        Assertions.assertEquals(0, rows);
    }
}