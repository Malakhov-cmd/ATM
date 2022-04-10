package com.atm.client.card.create;

import com.atm.client.card.CardApiBaseTest;
import com.atm.client.dto.CardDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("API. CreateCardService. Negative scenario")
public class NegativeCardCreateTest extends CardApiBaseTest {
    private String oversizeOwnerName = "testOwnerNameTextTextTextTextTextTextTextText" +
            "TextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextText" +
            "TextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextText";

    @BeforeEach
    void setUp() {
        initTestUser();
    }

    @Test
    @DisplayName("Create card with null data")
    public void testCreateCardWithNullAttributes() {
        Optional<CardDTO> responseUserDto = createCardService.createCard(null, null, null, null, null);
        assertThrows(NoSuchElementException.class, responseUserDto::get);
    }

    @Test
    @DisplayName("Create card with empty data")
    public void testCreateCardWithEmptyAttributes() {
        Optional<CardDTO> responseUserDto = createCardService.createCard(null, null, "", null, "");
        assertThrows(NoSuchElementException.class, responseUserDto::get);
    }

    @Test
    @DisplayName("Create card with oversize owner name data")
    public void testCreateCardWithOversizeOwnerNameAttributes() {
        Optional<CardDTO> responseUserDto = createCardService.createCard(6666666666666666L, 1125, oversizeOwnerName, 999, "testNameCardTest");
        assertThrows(NoSuchElementException.class, responseUserDto::get);
    }
}
