package com.cdyhrj.fastorm;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Test implements CommandLineRunner {
    private final FastORM fastORM;

    @Override
    public void run(String... args) {
        fastORM.updatable(Student.class)
                .set(Student::getCode, "abcde")
                .where()
                .andEq(Student::getId, 100)
                .ret()
                .exec();

        System.exit(0);
    }
}
