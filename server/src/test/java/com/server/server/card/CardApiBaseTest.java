package com.server.server.card;

import com.server.server.dto.CardDTO;
import com.server.server.dto.UserDTO;
import com.server.server.repo.CardRepo;
import com.server.server.repo.UserDetailsRepo;
import com.server.server.service.CardService;
import com.server.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;

@SpringBootTest
@TestPropertySource(properties = {"PROFILE = test"})
public class CardApiBaseTest {
    @Autowired
    protected UserDetailsRepo userDetailsRepo;
    @Autowired
    protected CardRepo cardRepo;

    @Autowired
    protected CardService cardService;
    @Autowired
    private UserService userService;

    protected void createTestUser(){
        UserDTO newUser = new UserDTO("serverCardTestUsername", "serverCardTestPassword", null);

        userService.createUser(newUser);
    }

    protected void createTestUserAndAddFourCard(){
        createTestUser();

        short dateValid = 1223;
        short CVV = 111;

        CardDTO firstCard = new CardDTO(9999_8888_5555_6666L, dateValid, "Pieter Williams", CVV, 0.0, "serverCardTestUsername", new ArrayList<>());
        CardDTO secondCard = new CardDTO(9999_8888_4444_6666L, dateValid, "Jake Falcon", CVV, 0.0, "serverCardTestUsername", new ArrayList<>());
        CardDTO thirdCard = new CardDTO(9999_8888_3333_6666L, dateValid, "Fred Miki", CVV, 0.0, "serverCardTestUsername", new ArrayList<>());
        CardDTO fourthCard = new CardDTO(9999_8888_2222_6666L, dateValid, "Vito Scaletto", CVV, 0.0, "serverCardTestUsername", new ArrayList<>());

        cardService.createCard(firstCard);
        cardService.createCard(secondCard);
        cardService.createCard(thirdCard);
        cardService.createCard(fourthCard);
    }
}
