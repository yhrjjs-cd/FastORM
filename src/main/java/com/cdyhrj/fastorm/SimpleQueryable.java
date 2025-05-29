package com.cdyhrj.fastorm;

import com.cdyhrj.fastorm.condition.AndCondition;
import com.cdyhrj.fastorm.entity.BaseEntity;
import com.cdyhrj.fastorm.entity.Student;
import com.cdyhrj.fastorm.queryable.Queryable;
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
        // 查询所有
        List<Student> list = fastORM.queryable(Student.class).toList();

        fastORM.queryable(Student.class)
                .leftJoin(BaseEntity.class)
                .andEqual(Student::getId, BaseEntity::getName)
                .andEqual(Student::getId, BaseEntity::getName)
                .andLeftEqual(Student::getName, 100)
                .andRightEqual(BaseEntity::getName, 100)
                .ret()
                .toList();

        // 查询总数
        int count = fastORM.queryable(Student.class).count();

        // 按条件查询
        fastORM.queryable(Student.class).where(AndCondition.of()).toList();

        // 通过Id查询
        fastORM.queryable(Student.class).id(100L).toList();

        // 通过name查询
        fastORM.queryable(Student.class).name("name").toList();
    }

    public static void main(String[] args) {
        FastORM fastORM = new FastORM();

        Queryable<Student> queryable = fastORM.queryable(Student.class)
                .leftJoin(BaseEntity.class)
                .andEqual(Student::getId, BaseEntity::getName)
                .andLeftEqual(Student::getName, 100)
                .andRightEqual(BaseEntity::getName, 100)
                .ret();

        System.out.println(queryable.toSqlString());
    }
}
