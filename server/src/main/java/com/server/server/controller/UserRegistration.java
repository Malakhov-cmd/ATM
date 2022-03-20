package com.server.server.controller;

import com.server.server.domain.User;
import com.server.server.dto.UserDTO;
import com.server.server.repo.UserDetailsRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user/registration")
@AllArgsConstructor
@Slf4j
public class UserRegistration {
    private UserDetailsRepo userDetailsRepo;

    @PostMapping()
    public void registerNewUser(
            @RequestBody UserDTO userDTO
    ) {
        log.info("Get the userDTO - username: " + userDTO.getUsername() + " password: " + userDTO.getPassword());

        Optional<User> findedUser = Optional
                .ofNullable(userDetailsRepo.findByUserNameAndPassword(
                        userDTO.getUsername(),
                        userDTO.getPassword()
                ));

        if (findedUser.isEmpty()) {
            User newUser = new User();
            newUser.setUserName(userDTO.getUsername());
            newUser.setPassword(userDTO.getPassword());

            userDetailsRepo.save(newUser);
        }
    }
}
