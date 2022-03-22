package com.atm.client.controller.login;

import com.atm.client.dto.UserDTO;
import com.atm.client.service.user.CreateUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@AllArgsConstructor
@Slf4j
public class LoginController {
    private CreateUserService createUserDTOService;

    @PostMapping("/default")
    public UserDTO addUserByDefaultAuth(
            @RequestParam String username,
            @RequestParam String password
    ) {
        UserDTO newUser = new UserDTO(username, password, null);

        log.info("Inputed data -" +
                " user name: " + newUser.getUsername() +
                " user password " + newUser.getPassword());

       return createUserDTOService
               .sendUserCreationRequestToServer(new UserDTO(username, password, null))
               .orElse(new UserDTO());
    }
}
