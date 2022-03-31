package com.server.server.user;

import com.server.server.repo.UserDetailsRepo;
import com.server.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {"PROFILE = test"})
public class UserApiBaseTest {
    @Autowired
    protected UserDetailsRepo userDetailsRepo;

    @Autowired
    protected UserService userService;
}
