package com.atm.client.service;

import com.atm.client.dto.UserDTO;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateUserDTOService {

    public UserDTO createUserDTO(
            OAuth2User principal
    ) {
        String login = principal.getAttribute("name");
        Optional<String> optionalPassword = Optional.ofNullable(principal.getAttribute("sub"));

        String password = optionalPassword
                .orElseGet(() -> principal.getAttribute("id").toString());

        return new UserDTO(login, password);
    }
}
