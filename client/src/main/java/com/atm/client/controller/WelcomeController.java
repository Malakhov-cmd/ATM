package com.atm.client.controller;

import com.atm.client.service.CreateUserDTOService;
import lombok.AllArgsConstructor;
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

    private CreateUserDTOService createUserDTOService = new CreateUserDTOService();

    @GetMapping("/")
    public String loadTemplate(
            Model model,
            @AuthenticationPrincipal OAuth2User principal
    ) {
        log.info("load template");

        if (principal != null){
           model.addAttribute("user", createUserDTOService.createUserDTO(principal));
            log.info("load user from Oauth");
        }

        model.addAttribute("isDevMode", "dev".equals(activeProfile));
        return "index";
    }
}
