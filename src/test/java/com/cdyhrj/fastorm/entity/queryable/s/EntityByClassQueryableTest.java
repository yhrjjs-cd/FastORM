package com.cdyhrj.fastorm.entity.queryable.s;

import com.cdyhrj.fastorm.FastORM;
import com.cdyhrj.fastorm.entity.entity.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class EntityByClassQueryableTest {
    @Autowired
    private FastORM fastORM;

    @Test
    void queryTest() {
        List<Student> students = fastORM.queryable(Student.class)
                .where()
                .andEq(Student::getCode, "test")
                .ret()
                .query();

        Assertions.assertFalse(students.isEmpty());
    }
}