package com.cdyhrj.fastorm;

import com.cdyhrj.fastorm.entity.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Test implements CommandLineRunner {
    private final FastORM fastORM;

    @Override
    public void run(String... args) throws Exception {
        Student student = new Student();
        student.setName("<UNK>");

        Long id = fastORM.insertable(student)
                .exec()
                .getId();

        System.out.println(id);

        System.exit(0);
    }
}
