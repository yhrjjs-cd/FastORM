package com.cdyhrj.fastorm;


import com.cdyhrj.fastorm.entity.BaseEntity;
import com.cdyhrj.fastorm.entity.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 简单查询
 */
@Component
@RequiredArgsConstructor
public class SimpleQueryable {
    private final FastORM fastORM;

    public void test() {
//        // 查询所有
//        List<Student> list = fastORM.queryable(Student.class).toList();
//
//        fastORM.queryable(Student.class)
//                .leftJoin(BaseEntity.class)
//                .andEqual(Student::getId, BaseEntity::getName)
//                .andEqual(Student::getId, BaseEntity::getName)
//                .andLeftEqual(Student::getName, 100)
//                .andRightEqual(BaseEntity::getName, 100)
//                .ret()
//                .toList();
//
//        // 查询总数
//        int count = fastORM.queryable(Student.class).count();
//
//        // 按条件查询
//        fastORM.queryable(Student.class).where(AndCondition.of()).toList();
//
//        // 通过Id查询
//        fastORM.queryable(Student.class).id(100L).toList();
//
//        // 通过name查询
//        fastORM.queryable(Student.class).name("name").toList();
    }

    public static void main(String[] args) {
        FastORM fastORM = new FastORM();

        List<Student> students = fastORM.queryable(Student.class)
                .join(BaseEntity.class)
                .andEq(Student::getId, BaseEntity::getName)
                .andEq(BaseEntity::getName, 1)
                .andCri("T1.name = T2.name")
                .andEq(BaseEntity::getName, 1)
                .ret()
                .where()
                .andEq(Student::getName, 100)
                .andEq(BaseEntity::getName, "100")
                .andOrGroup()
                .orEq(BaseEntity::getName, "100")
                .orEq(Student::getId, "200")
                .ret()
                .ret()
                .toList();

        System.out.println(students);
    }
}
