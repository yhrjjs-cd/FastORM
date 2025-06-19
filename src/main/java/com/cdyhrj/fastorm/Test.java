package com.cdyhrj.fastorm;

import com.cdyhrj.fastorm.entity.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Test implements CommandLineRunner {
    private final FastORM fastORM;

    @Override
    public void run(String... args) throws Exception {
        Student student = new Student();
        student.setName("<UNK>");

//        fastORM.insertable(student)
//                .exec();

        List<Student> students = new ArrayList<>();
        students.add(student);
        students.add(student);
        fastORM.insertable(students)
                .exec();

        System.exit(0);
    }
}
