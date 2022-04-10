package com.server.server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {"PROFILE = test"})
class ServerApplicationTests {
    @Test
    void test(){
    }
}
