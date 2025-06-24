package com.cdyhrj.fastorm.entity.insertable.by_class;

import com.cdyhrj.fastorm.FastORM;
import com.cdyhrj.fastorm.entity.entity.Student;
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
                .exec();

        Assertions.assertEquals(1, count);
    }

    @Test
    void tesEntityClassInsertExecReturnId() {
        Long id = fastORM.insertable(Student.class)
                .set(Student::getCode, "test")
                .execReturnId();

        Assertions.assertTrue(id > 0, "Id should be greater than 0");
    }
}