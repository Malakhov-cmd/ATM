package com.server.server;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.regex.Pattern;

@SpringBootTest
class ServerApplicationTests {

    Pattern cardNumberPattern = Pattern.compile("(([2-6]([0-9]{3})?)(([0-9]{4}?){3}))");
    Pattern cardDatePattern = Pattern.compile("(0[1-9]|1[0-2])([0-9]{2})");
    Pattern cardCVVPattern = Pattern.compile("[0-9]{3}");
    Pattern ownerPattern = Pattern.compile("(([A-Z]+)\\s([A-Z]+))");

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd : hh-mm-ss a");

    @Test
    void contextLoads() {
        Assertions.assertTrue(cardNumberPattern.matcher("2666111133332222").matches());
        Assertions.assertTrue(ownerPattern.matcher("GEORGE MALAKHOV".toUpperCase()).matches());
    }


    @SneakyThrows
    @Test
    void test(){
        Date date = new Date();
        System.out.println( dateFormat.format(date));
    }

}
