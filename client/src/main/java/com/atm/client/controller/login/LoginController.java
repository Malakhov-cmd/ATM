package com.atm.client.controller.login;

import com.atm.client.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {
    @PostMapping("/default")
    public void addUserByDefaultAuth(
            @RequestParam String username,
            @RequestParam String password

    ) {
        UserDTO newUser = new UserDTO(username, password);

        log.info("Inputed data - " +
                "user name: " + newUser.getUsername() +
                " user password " + newUser.getPassword());
    }
}
