package com.cdyhrj.fastorm;

import com.cdyhrj.fastorm.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ApplicationTests {
    @Test
    void contextLoads() {
        Student st =  new Student();
        System.out.println(st.getId());

        System.out.println("Hello World");
    }
}
