package com.server.server.card.select;

import com.server.server.card.CardApiBaseTest;
import com.server.server.domain.Card;
import com.server.server.dto.CardDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("API. CardService. Select test")
public class CardSelectTest extends CardApiBaseTest {

    @BeforeEach
    void setUp() {
        createTestUserAndAddFourCard();
    }

    @Test
    @DisplayName("Select card")
    public void cardSelection() {
        CardDTO selectedCard = cardService.getUserCard("serverCardTestUsername", String.valueOf(9999_8888_2222_6666L));

        Card foundCard = cardRepo.findByNumber(9999_8888_2222_6666L);

        assertTrue(selectedCard.getNumber() == foundCard.getNumber()
                && selectedCard.getUsername().equals(foundCard.getUser().getUserName())
                && selectedCard.getOwner().equals(foundCard.getOwner()));
    }
}
