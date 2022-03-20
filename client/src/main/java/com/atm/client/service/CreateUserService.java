package com.atm.client.service;

import com.atm.client.dto.UserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Slf4j
public class CreateUserService {

    public UserDTO createUser(
            OAuth2User principal
    ) {
        String login = principal.getAttribute("name");
        String password = getPassword(principal);

        log.info("Parsed values from oauth - username: " + login + " password: " + password);

        return sendCreationUserRequestToServer(login, password).orElse(new UserDTO());
    }

    private String getPassword(OAuth2User principal) {
        Optional<String> optionalPassword = Optional.ofNullable(principal.getAttribute("sub"));

        return optionalPassword
                .orElseGet(() -> principal.getAttribute("id").toString());
    }

    public Optional<UserDTO> sendCreationUserRequestToServer(String login, String password) {
        UserDTO userDTO = new UserDTO(login, password);

        RestTemplate restTemplate = new RestTemplate();

        Optional<HttpEntity<String>> optionalHttpEntity = prepareHttpEntity(userDTO);

        if (optionalHttpEntity.isPresent()){
            HttpEntity<String> entity = optionalHttpEntity.get();
            sendLoginRequest(restTemplate, entity);

            return Optional.of(userDTO);
        } else {
            return Optional.empty();
        }
    }

    private Optional<HttpEntity<String>> prepareHttpEntity(UserDTO userDTO) {
        HttpHeaders headers = getHttpHeaders();

        try {
            return Optional.of(new HttpEntity<>(new ObjectMapper().writeValueAsString(userDTO), headers));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }

    private void sendLoginRequest(RestTemplate restTemplate, HttpEntity<String> entity) {
        ResponseEntity<String> loginResponse = restTemplate
                .exchange("http://localhost:9090/user/registration", HttpMethod.POST, entity, String.class);

        if (loginResponse.getStatusCode() == HttpStatus.OK) {
            log.info("Success login confirmed");
        } else {
            log.error("Request has been denied");
        }
    }
}
