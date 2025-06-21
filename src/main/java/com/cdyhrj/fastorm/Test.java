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
    public void run(String... args) {
        Student student = new Student();

        fastORM.updatable(student)
                .exec();

        System.exit(0);
    }
}
