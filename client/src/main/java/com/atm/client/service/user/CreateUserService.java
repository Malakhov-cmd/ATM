package com.atm.client.service.user;

import com.atm.client.dto.UserDTO;
import com.atm.client.service.sender.Sender;
import com.atm.client.service.validation.ValidatorService;
import com.atm.client.util.Links;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
@AllArgsConstructor
@Slf4j
public class CreateUserService {
    private Sender<UserDTO> userDTOSender;
    private ValidatorService validatorService;

    private PasswordEncoder passwordEncoder;

    private Environment env;

    private final KafkaTemplate<Long, UserDTO> kafkaUserDTOTemplate;
    private volatile UserDTO userDTO;

    public Optional<UserDTO> createUserFromOauthData(OAuth2User principal) {
        if (Objects.equals(env.getProperty("TypeOfSpringAppsCommunication"), "rest"))
            return createUserFromOauthDataRest(principal);
        return createUserFromOauthDataKafka(principal);
    }

    private Optional<UserDTO> createUserFromOauthDataRest(OAuth2User principal) {
        String login = principal.getAttribute("name");
        String password = getPassword(principal);

        log.info("Parsed values from oauth - username: " + login + " password: " + password);

        UserDTO userDTO = new UserDTO(login, passwordEncoder.encode(password), null);
        return sendUserCreationRequestToServer(userDTO);
    }

    private String getPassword(OAuth2User principal) {
        Optional<String> optionalPassword = Optional.ofNullable(principal.getAttribute("sub"));

        return optionalPassword
                .orElseGet(() -> principal.getAttribute("id").toString());
    }

    public Optional<UserDTO> sendUserCreationRequestToServer(UserDTO userDTO) {
        return validatorService.isValidUserDTO(userDTO) ?
                userDTOSender.sendCreationEntityRequestToServer(userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword())), Links.createUser.getLink(), HttpMethod.POST) :
                Optional.empty();
    }

    private Optional<UserDTO> createUserFromOauthDataKafka(OAuth2User principal) {
        String login = principal.getAttribute("name");
        String password = getPassword(principal);

        log.info("Parsed values from oauth - username: " + login + " password: " + password);

        UserDTO userDTO = new UserDTO(login, passwordEncoder.encode(password), null);

        this.userDTO = new UserDTO();
        kafkaUserDTOTemplate.send("user.request.create", userDTO);

        try {
            return Optional.of(getUserWhenConsumerBeReady().get());
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error! type {}", e.getClass());
        }
        return Optional.empty();
    }

    private Future<UserDTO> getUserWhenConsumerBeReady() {
        return CompletableFuture
                .supplyAsync(() -> {
                    while (userDTO.getUsername() == null) ;
                    return this.userDTO;
                });
    }

    @KafkaListener(
            id = "RequestCreateUser",
            topics = {"server.request.create.user"},
            containerFactory = "singleFactoryUser"
    )
    public void getUserConsumer(UserDTO userFromServer) {
        userDTO = userFromServer;
    }
}
