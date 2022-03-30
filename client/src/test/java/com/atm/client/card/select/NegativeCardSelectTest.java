package com.atm.client.card.select;

import com.atm.client.card.CardApiBaseTest;
import com.atm.client.dto.CardDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("API. SelectCardService. Negative scenario")
public class NegativeCardSelectTest extends CardApiBaseTest {
    private final String oversizeUsername = "testUsernameOverSizeTEXTTEXTTEXT" +
            "TEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXT" +
            "TEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXT";

    @BeforeEach
    void setUp() {
        initTestUserWithFourTestCard();
    }

    @Test
    @DisplayName("Select cards with null data")
    public void testSelectCardsWithNullData() {
        List<CardDTO> userCards = selectCardService.getCards(null);

        assertTrue(userCards.isEmpty());
    }

    @Test
    @DisplayName("Select card with null data")
    public void testSelectCardWithNullData() {
        CardDTO userCard = selectCardService.getCard(null, null);

        assertTrue(userCard.getUsername() == null && userCard.getNumber() == 0L);
    }

    @Test
    @DisplayName("Select cards with empty data")
    public void testSelectCardsWithEmptyData() {
        List<CardDTO> userCards = selectCardService.getCards("");

        assertTrue(userCards.isEmpty());
    }

    @Test
    @DisplayName("Select card with empty data")
    public void testSelectCardWithEmptyData() {
        CardDTO userCard = selectCardService.getCard("", "");

        assertTrue(userCard.getUsername() == null && userCard.getNumber() == 0L);
    }

    @Test
    @DisplayName("Select card with incorrect card data")
    public void testSelectCardWithIncorrectCardData() {
        CardDTO userCard = selectCardService.getCard("", "1234");

        assertTrue(userCard.getUsername() == null && userCard.getNumber() == 0L);
    }

    @Test
    @DisplayName("Select card with oversize username card data")
    public void testSelectCardWithOversizeData() {
        CardDTO userCard = selectCardService.getCard(oversizeUsername, "444444444444444");

        assertTrue(userCard.getUsername() == null && userCard.getNumber() == 0L);
    }
}
