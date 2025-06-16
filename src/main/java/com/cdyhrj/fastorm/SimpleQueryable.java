package com.cdyhrj.fastorm;


import com.cdyhrj.fastorm.entity.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单查询
 */
@Component
@RequiredArgsConstructor
public class SimpleQueryable {
    private final FastORM fastORM;

//    public static void main(String[] args) {
//        List<Student> students = fastORM.queryable(Student.class)
//                .join(BaseEntity.class)
//                .andEq(Student::getId, BaseEntity::getName)
//                .andEq(BaseEntity::getName, 1)
//                .andCri("T1.name = T2.name")
//                .andEq(BaseEntity::getName, 1)
//                .ret()
//                .where()
//                .andEq(Student::getName, 100)
//                .andEq(BaseEntity::getName, "100")
//                .andOrGroup()
//                .orEq(BaseEntity::getName, "100")
//                .orEq(Student::getId, "200")
//                .ret()
//                .ret()
//                .orderBy().add(BaseEntity::getName, OrderBy.Order.ASC).ret()
//                .toList();

    public void testInsertEntity() {
        Student student = new Student();

        fastORM.insertable(student)
                .exec();
    }

    public void testInsertListEntity() {
        List<Student> userList = new ArrayList<>();
        fastORM.insertable(userList)
                .batchSize(2000)
                .exec();
    }

    public void testInsertEntityClass() {
        fastORM.insertable(Student.class)
                .set(Student::getId, 1)
                .set(Student::getName, "that")
                .exec();
    }
}
