package com.atm.client.controller;

import com.atm.client.dto.UserDTO;
import com.atm.client.service.user.CreateUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
@Slf4j
public class WelcomeController {
    @Value("${spring.profiles.active}")
    private String activeProfile;

    private final CreateUserService createUserService = new CreateUserService();

    @GetMapping("/")
    public String loadTemplate(
            Model model,
            @AuthenticationPrincipal OAuth2User principal
    ) {
        log.info("Load template");

        if (principal != null) {
            log.info("Load user from Oauth");
            model.addAttribute("user", createUserService.createUserFromOauthData(principal).orElse(new UserDTO()));
        } else {
            log.info("User is not authorized");
            model.addAttribute("user", new UserDTO());
        }

        model.addAttribute("isDevMode", "dev".equals(activeProfile));
        return "index";
    }
}
