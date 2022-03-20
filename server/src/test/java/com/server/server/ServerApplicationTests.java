package com.server.server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Pattern;

@SpringBootTest
class ServerApplicationTests {

    Pattern cardNumberPattern = Pattern.compile("(([2-6]([0-9]{3})?)(([0-9]{4}?){3}))");
    Pattern cardDatePattern = Pattern.compile("(0[1-9]|1[0-2])([0-9]{2})");
    Pattern cardCVVPattern = Pattern.compile("[0-9]{3}");
    Pattern ownerPattern = Pattern.compile("(([A-Z]+)\\s([A-Z]+))");

    @Test
    void contextLoads() {
        Assertions.assertTrue(cardNumberPattern.matcher("2666111133332222").matches());
        Assertions.assertTrue(ownerPattern.matcher("GEORGE MALAKHOV".toUpperCase()).matches());
    }

}
