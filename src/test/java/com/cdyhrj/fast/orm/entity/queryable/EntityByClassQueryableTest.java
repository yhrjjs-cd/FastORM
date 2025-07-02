package com.cdyhrj.fast.orm.entity.queryable;

import com.cdyhrj.fast.orm.FastORM;
import com.cdyhrj.fast.orm.entity.Student;
import com.cdyhrj.fast.orm.pager.Pager;
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
                .andOrGroup()
                .orEq(Student::getCode, "test")
                .orLte(Student::getId, 1L)
                .orEq(Student::getCode, "test")
                .orAndGroup()
                .andEq(Student::getCode, "test")
                .end()
                .end()
                .andEq(Student::getCode, "test")
                .ret()
                .pager(Pager.DEFAULT)
                .query();

        Assertions.assertFalse(students.isEmpty());
    }
}