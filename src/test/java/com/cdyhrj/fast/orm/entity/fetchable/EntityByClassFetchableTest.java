package com.cdyhrj.fast.orm.entity.fetchable;

import com.cdyhrj.fast.orm.FastORM;
import com.cdyhrj.fast.orm.entity.Student;
import com.cdyhrj.fast.orm.api.order.Order;
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
                .orderBy()
                .add(Student::getId, Order.DESC)
                .ret()
                .fetch();

        Assertions.assertTrue(student.isPresent());
    }
}