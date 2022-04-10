package com.server.server.card.create;

import com.server.server.card.CardApiBaseTest;
import com.server.server.domain.User;
import com.server.server.dto.CardDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("API. CardService. Create test")
public class CardCreateTest extends CardApiBaseTest {

    @BeforeEach
    void setUp() {
        createTestUser();
    }

    @Test
    @DisplayName("Create card")
    public void cardCreation() {
        short dateValid = 1223;
        short CVV = 111;

        CardDTO newCard = new CardDTO(9999_8888_7777_6666L, dateValid, "Pieter Williams", CVV, 0.0, "serverCardTestUsername", new ArrayList<>());

        cardService.createCard(newCard);

        User foundUser = userDetailsRepo.findByUserName("serverCardTestUsername");
        assertTrue(foundUser
                .getCards()
                .stream()
                .anyMatch(card -> card.getNumber() == 9999_8888_7777_6666L
                        && card.getUser().getUserName().equals("serverCardTestUsername")
                        && card.getOwner().equals("Pieter Williams")));
    }
}
