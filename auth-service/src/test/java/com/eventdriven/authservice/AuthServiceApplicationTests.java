package com.eventdriven.authservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@ComponentScan(basePackages = {"com.eventdriven.authservice", "com.eventdriven.common.mapper"})
class AuthServiceApplicationTests {

    @Test
    void contextLoads() {
        // Context yükleniyor mu test ediyoruz
    }
}
