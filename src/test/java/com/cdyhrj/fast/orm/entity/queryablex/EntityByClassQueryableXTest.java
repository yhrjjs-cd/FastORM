package com.cdyhrj.fast.orm.entity.queryablex;

import com.cdyhrj.fast.orm.FastORM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EntityByClassQueryableXTest {
    @Autowired
    private FastORM fastORM;

//    @Test
//    void queryTest() {
//        List<Student> students = fastORM.queryableX(Student.class)
//                .joins()
//                .addJoin(JoinType.INNER, MailMan.class, "XX")
//                .onEq(Student::getId, MailMan::getId)
//                .end()
//                .ret()
//                .where()
//                .andEq(Student::getId, "XX", MailMan::getId)
//                .andIsNull("XX", MailMan::getCode)
//                .ret()
//                .orderBy()
//                .add(Student::getCode, Order.DESC)
//                .ret()
//                .query();
//    }

//    @Test
//    void withTest() {
//        With with1 = With.of("S1", fastORM.queryableX(MailMan.class));
//        List<Student> students = fastORM.queryableX(Student.class)
//                .addWith(with1)
//                .addWith(With.of("S2", fastORM.queryableX(MailMan2.class)))
//                .joins()
//                .addWithJoin(JoinType.INNER, with1.getName())
//                .onEq(Student::getId, "code")
//                .end()
//                .ret()
//                .query();
//    }
}