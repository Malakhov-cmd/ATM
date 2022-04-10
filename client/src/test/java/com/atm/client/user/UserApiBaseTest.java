package com.atm.client.user;

import com.atm.client.service.user.CreateUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserApiBaseTest {
    @Autowired
    protected CreateUserService createUserService;
}
