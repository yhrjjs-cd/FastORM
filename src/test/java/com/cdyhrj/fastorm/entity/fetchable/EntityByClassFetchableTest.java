package com.cdyhrj.fastorm.entity.fetchable;

import com.cdyhrj.fastorm.FastORM;
import com.cdyhrj.fastorm.entity.entity.Student;
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
    void tesEntityClassInsertExec() {
        Optional<Student> student = fastORM.fetchable(Student.class)
                .id(369L)
                .fetchById();

        Assertions.assertTrue(student.isPresent());
    }
}