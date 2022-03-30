package com.atm.client.operation.create;

import com.atm.client.dto.OperationDTO;
import com.atm.client.operation.OperationApiBaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("API. OperationService. Positive scenario")
public class PositiveOperationCreateTest extends OperationApiBaseTest {
    @BeforeEach
    void setUp() {
        initTestUserWithTestCard();
    }

    @Test
    @DisplayName("Create operation with valid data")
    public void testSelectCardWithValidData() {
        OperationDTO operationDTO = operationService.creationOperationRequest(5555555555555555L, "Top up", "testNameOperationTest", 500.0);

        assertTrue(operationDTO.getCardNumber() == 5555555555555555L &&
                operationDTO.getUsername().equals("testNameOperationTest"));
    }
}
