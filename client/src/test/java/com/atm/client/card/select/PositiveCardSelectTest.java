package com.atm.client.card.select;

import com.atm.client.card.CardApiBaseTest;
import com.atm.client.dto.CardDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("API. SelectCardService. Positive scenario")
public class PositiveCardSelectTest extends CardApiBaseTest {
    @BeforeEach
    void setUp() {
        initTestUserWithFourTestCard();
    }

    @Test
    @DisplayName("Select cards with valid data")
    public void testSelectCardsWithValidData() {
        List<Long> userCards = selectCardService
                .getCards("testNameCardTest")
                .stream()
                .map(CardDTO::getNumber)
                .collect(Collectors.toList());

        List<Long> insertedCardNumber = List.of(
                4444444444444444L,
                4444555555555555L,
                4444666666666666L,
                4444777777777777L);

        assertTrue(userCards
                .containsAll(insertedCardNumber));
    }

    @Test
    @DisplayName("Select card with valid data")
    public void testSelectCardWithValidData() {
        CardDTO userCard = selectCardService.getCard("testNameCardTest", String.valueOf( 4444444444444444L));

        assertTrue(userCard.getNumber() ==  4444444444444444L
                && userCard.getUsername().equals("testNameCardTest"));
    }
}
