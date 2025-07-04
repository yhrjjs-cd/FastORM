package com.cdyhrj.fast.orm.entity.insertable.by_class;

import com.cdyhrj.fast.orm.FastORM;
import com.cdyhrj.fast.orm.entity.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EntityClassInsertableTest {
    @Autowired
    private FastORM fastORM;

    @Test
    void tesEntityClassInsertExec() {
        int count = fastORM.insertable(Student.class)
                .set(Student::getCode, "test")
                .insert();

        Assertions.assertEquals(1, count);
    }

    @Test
    void tesEntityClassInsertExecReturnId() {
        Long id = fastORM.insertable(Student.class)
                .set(Student::getCode, "test")
                .insertReturnId();

        Assertions.assertTrue(id > 0, "Id should be greater than 0");
    }
}