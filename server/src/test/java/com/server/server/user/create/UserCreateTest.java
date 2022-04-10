package com.server.server.user.create;

import com.server.server.domain.User;
import com.server.server.dto.UserDTO;
import com.server.server.user.UserApiBaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("API. UserService. Create test")
public class UserCreateTest extends UserApiBaseTest {

    @Test
    @DisplayName("Create user")
    public void userCreation(){
        UserDTO newUser = new UserDTO("serverTestUsername", "serverTestPassword", null);

        userService.createUser(newUser);

        User foundUser = userDetailsRepo.findByUserName("serverTestUsername");
        assertTrue(foundUser.getUserName().equals("serverTestUsername") &&
                foundUser.getPassword().equals("serverTestPassword"));
    }
}
