package com.atm.client;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ClientApplicationTests {

    @Test
    void contextLoads() {
        System.setProperty("TypeOfSpringAppsCommunication", "rest");
        System.setProperty("Profile", "dev");
    }

}
