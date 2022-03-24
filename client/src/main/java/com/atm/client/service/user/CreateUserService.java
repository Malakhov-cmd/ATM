package com.atm.client.service.user;

import com.atm.client.dto.UserDTO;
import com.atm.client.service.sender.Sender;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CreateUserService {
    private Sender<UserDTO> userDTOSender;

    public Optional<UserDTO> createUserFromOauthData(
            OAuth2User principal
    ) {
        String login = principal.getAttribute("name");
        String password = getPassword(principal);

        log.info("Parsed values from oauth - username: " + login + " password: " + password);

        UserDTO userDTO = new UserDTO(login, password, null);

        return sendUserCreationRequestToServer(userDTO);
    }

    private String getPassword(OAuth2User principal) {
        Optional<String> optionalPassword = Optional.ofNullable(principal.getAttribute("sub"));

        return optionalPassword
                .orElseGet(() -> principal.getAttribute("id").toString());
    }

    public Optional<UserDTO> sendUserCreationRequestToServer(UserDTO userDTO) {
        return userDTOSender.sendCreationEntityRequestToServer(userDTO, "http://localhost:9090/user/registration", HttpMethod.POST);
    }
}
