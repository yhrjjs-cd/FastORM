package com.cdyhrj.fastorm.entity.insertable.by_list;

import com.cdyhrj.fastorm.FastORM;
import com.cdyhrj.fastorm.entity.BaseEntity;
import com.cdyhrj.fastorm.entity.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class EntitiesInsertableTest {
    @Autowired
    private FastORM fastORM;

    @Test
    void tesEntitiesInsertExec() {
        List<Student> students = new ArrayList<>();
        students.add(new Student());
        students.add(new Student());
        students.add(new Student());

        Assertions.assertDoesNotThrow(() -> {
            fastORM.insertable(students)
                    .batchSize(2)
                    .exec();
        });
    }

    @Test
    void tesEntitiesInsertExecByEach() {
        List<BaseEntity> entities = new ArrayList<>();
        entities.add(new Student());
        entities.add(new Student());
        entities.add(new Student());

        Assertions.assertDoesNotThrow(() -> {
            fastORM.insertable(entities)
                    .exec();
        });
    }
}
