package com.cdyhrj.fastorm.entity.fetchable;

import com.cdyhrj.fastorm.FastORM;
import com.cdyhrj.fastorm.entity.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class EntityByClassFetchableTest {
    @Autowired
    private FastORM fastORM;

    @Test
    void fetchByIdTest() {
        Optional<Student> student = fastORM.fetchable(Student.class)
                .id(369L)
                .fetch();

        Assertions.assertTrue(student.isPresent());
    }

    @Test
    void fetchByNameTest() {
        Optional<Student> student = fastORM.fetchable(Student.class)
                .name("test")
                .fetch();

        Assertions.assertTrue(student.isPresent());
    }

    @Test
    void fetchByWhereTest() {
        Optional<Student> student = fastORM.fetchable(Student.class)
                .where()
                .andEq(Student::getCode, "test")
                .andIsNull(Student::getName)
                .ret()
                .fetch();

        Assertions.assertTrue(student.isPresent());
    }
}