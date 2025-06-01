package com.cdyhrj.fastorm;

import com.cdyhrj.fastorm.entity.Student;
import com.cdyhrj.fastorm.queryable.Queryable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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

        Queryable<Student> queryable = fastORM.queryable(Student.class)
                .join(Student.class, "S2")
                .andEqual("S2", Student::getName, Student::getName)
                .andREqual("S2", Student::getId, Student::getName)
                .ret()
                .join(Student.class, "SSS3")
                .andLEqual("S2", Student::getName, "SSS3", Student::getName)
                .andREqual("SSS3", Student::getName, 28)
                .ret()
//                .join(BaseEntity.class, Base2Entity.class)
//                .andEqual(BaseEntity::getName, Base2Entity::getName)
//                .ret()
//                .join(BaseEntity.class, On.of(Student::getId, BaseEntity::getName).andEqual(BaseEntity::getName, "Hello"))
//                .join(Base2Entity.class, On.of(Student::getName, Base2Entity::getName).andEqual(Base2Entity::getName, "Hello2"))
//                .where(null);
//                .andEqual(Student::getId, BaseEntity::getName)
//                .andEqual(Student::getName, 100)
//                .andEqual(BaseEntity::getName, 100)
//                .ret()
//                .where(Cnd.andEqual(Student::getName, "2323")
//                        .andEqual(Student::getName, "2323"));
                ;

        System.out.println(queryable.toSqlString());
    }
}
