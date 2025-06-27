package com.cdyhrj.fastorm.entity.updatable.by_class;

import com.cdyhrj.fastorm.FastORM;
import com.cdyhrj.fastorm.entity.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class EntityClassUpdatableTest {
    @Autowired
    private FastORM fastORM;


    @Test
    void tesEntityUpdateExecById() {
        int rows = fastORM.updatable(Student.class)
                .id(100L)
                .set(Student::getName, "This is Name")
                .execById();

        Assertions.assertTrue(rows >= 0);
    }

    @Test
    void tesEntityUpdateExecByName() {
        int rows = fastORM.updatable(Student.class)
                .name("Hello")
                .set(Student::getName, "This is Name")
                .execByName();

        Assertions.assertTrue(rows >= 0);
    }

    @Test
    void tesEntityUpdateExecByWhere() {
        int rows = fastORM.updatable(Student.class)
                .where()
                .andEq(Student::getId, 100L)
                .andEq(Student::getName, "Hello")
                .ret()
                .set(Student::getName, "This is Name")
                .execByWhere();

        Assertions.assertTrue(rows >= 0);
    }

    @Test
    void tesEntityUpdateField() {
        int rows = fastORM.updatable(Student.class)
                .id(100L)
                .updateField(
                        Student::getName, "This is Name",
                        Student::getCode, "This is Code");

        Assertions.assertTrue(rows >= 0);
    }
}
