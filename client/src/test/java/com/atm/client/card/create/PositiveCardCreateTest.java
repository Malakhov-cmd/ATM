package com.atm.client.card.create;

import com.atm.client.card.CardApiBaseTest;
import com.atm.client.dto.CardDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("API. CreateCardService. Positive scenario")
public class PositiveCardCreateTest extends CardApiBaseTest {

    @Test
    @DisplayName("Create card with valid data")
    public void testCreateCardWithValidAttributes() {
        initTestUser();

        Optional<CardDTO> responseUserDto = createCardService.createCard(4444555566667777L, 223, "Test ownerCardTesting", 111, "testNameCardTest");
        assertEquals("testNameCardTest", responseUserDto.get().getUsername());
    }
}
