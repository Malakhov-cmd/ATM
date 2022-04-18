package com.server.server.service;

import com.server.server.dto.UserDTO;
import com.server.server.repo.UserDetailsRepo;
import com.server.server.service.utils.DataObjectParser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private UserDetailsRepo userDetailsRepo;

    private final KafkaTemplate<Long, UserDTO> kafkaCreateUserDTOTemplate;
    private DataObjectParser dataObjectParser;

    public synchronized void createUser(UserDTO userDTO) {
        if (!isAlreadyRegistered(userDTO)) {
            log.info("User with  this credential not found!");

            userDetailsRepo.save(dataObjectParser.userDTOtoUserDAO(userDTO));
            log.info("Successfully create new user.");
        }
    }

    private boolean isAlreadyRegistered(UserDTO userDTO) {
        return Optional
                .ofNullable(userDetailsRepo.findByUserName(userDTO.getUsername()))
                .isPresent();
    }

    @KafkaListener(
            id = "RequestCreateUser",
            topics = {"user.request.create"},
            containerFactory = "singleFactoryUser"
    )
    public void selectUserConsume(UserDTO userFromClientDTO) {
        createUser(userFromClientDTO);
        kafkaCreateUserDTOTemplate
                .send("server.request.create.user", userFromClientDTO);
    }
}
