package com.server.server.controller;

import com.server.server.dto.UserDTO;
import com.server.server.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/registration")
@AllArgsConstructor
@Slf4j
public class UserRegistration {
    private UserService userService;

    @PostMapping()
    public void registerNewUser(
            @RequestBody UserDTO userDTO
    ) {
        log.info("Get the userDTO - username: " + userDTO.getUsername()
                + " password: " + userDTO.getPassword());

        userService.createUser(userDTO);
    }
}
