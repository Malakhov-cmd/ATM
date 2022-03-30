package com.atm.client.operation.create;

import com.atm.client.dto.OperationDTO;
import com.atm.client.operation.OperationApiBaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("API. OperationService. Negative scenario")
public class NegativeOperationCreateTest extends OperationApiBaseTest {
    private String oversizeUsername = "testUsernameTextTextTextTextTextTextTextText" +
            "TextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextText" +
            "TextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextText";

    @BeforeEach
    void setUp() {
        initTestUserWithTestCard();
    }

    @Test
    @DisplayName("Create operation with null data")
    public void testSelectCardWithNullData() {
        OperationDTO operationDTO = operationService.creationOperationRequest(null, null, null, 0.0);

        assertTrue(operationDTO.getCardNumber() == 0L &&
                operationDTO.getUsername() == null);
    }

    @Test
    @DisplayName("Create operation with empty data")
    public void testSelectCardWithEmptyData() {
        OperationDTO operationDTO = operationService.creationOperationRequest(null, "", "", 0.0);

        assertTrue(operationDTO.getCardNumber() == 0L &&
                operationDTO.getUsername() == null);
    }

    @Test
    @DisplayName("Create operation with incorrect card number data")
    public void testSelectCardWitIncorrectCardNumberData() {
        OperationDTO operationDTO = operationService.creationOperationRequest(111L, "Top up", "testNameOperationTest", 500.0);

        assertTrue(operationDTO.getCardNumber() == 0L &&
                operationDTO.getUsername() == null);
    }

    @Test
    @DisplayName("Create operation with incorrect type data")
    public void testSelectCardWitIncorrectTypeData() {
        OperationDTO operationDTO = operationService.creationOperationRequest(5555555555555555L, "Incorrect", "testNameOperationTest", 500.0);

        assertTrue(operationDTO.getCardNumber() == 0L &&
                operationDTO.getUsername() == null);
    }

    @Test
    @DisplayName("Create operation with withdraw type on card with balance less than withdraw value")
    public void testSelectCardWitWithdrawTypeThanMoreThanBalanceData() {
        OperationDTO operationDTO = operationService.creationOperationRequest(5555555555555555L, "Withdraw", "testNameOperationTest", 5000.0);

        assertTrue(operationDTO.getCardNumber() == 0L &&
                operationDTO.getUsername() == null);
    }

    @Test
    @DisplayName("Create operation with oversize username value")
    public void testSelectCardWitOversizeUsernameData() {
        OperationDTO operationDTO = operationService.creationOperationRequest(5555555555555555L, "Top up", oversizeUsername, 5000.0);

        assertTrue(operationDTO.getCardNumber() == 0L &&
                operationDTO.getUsername() == null);
    }
}
