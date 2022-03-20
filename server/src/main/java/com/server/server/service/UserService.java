package com.server.server.service;

import com.server.server.domain.User;
import com.server.server.dto.UserDTO;
import com.server.server.repo.UserDetailsRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private UserDetailsRepo userDetailsRepo;

    public void createUser(UserDTO userDTO){
        Optional<User> findedUser = Optional
                .ofNullable(userDetailsRepo.findByUserNameAndPassword(
                        userDTO.getUsername(),
                        userDTO.getPassword()
                ));

        if (findedUser.isEmpty()) {
            log.info("User with  this credential not found!");

            User newUser = new User();
            newUser.setUserName(userDTO.getUsername());
            newUser.setPassword(userDTO.getPassword());

            userDetailsRepo.save(newUser);

            log.info("Successfully create new user.");
        }
    }
}
