package com.server.server.card.select;

import com.server.server.card.CardApiBaseTest;
import com.server.server.domain.Card;
import com.server.server.dto.CardDTO;
import com.server.server.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("API. CardService. Select all test")
public class CardSelectAllTest extends CardApiBaseTest {
    @BeforeEach
    void setUp() {
        createTestUserAndAddFourCard();
    }

    @Test
    @DisplayName("Select all card")
    public void cardSelectionAllCards() {
        Set<Long> selectedCardsNumbers = cardService
                .getUserCards(new UserDTO("serverCardTestUsername", "serverCardTestPassword", null))
                .stream()
                .map(CardDTO::getNumber)
                .collect(Collectors.toSet());

        Set<Long> userCards = userDetailsRepo
                .findByUserName("serverCardTestUsername")
                .getCards()
                .stream()
                .map(Card::getNumber)
                .collect(Collectors.toSet());

        assertTrue(userCards.containsAll(selectedCardsNumbers));
    }
}
