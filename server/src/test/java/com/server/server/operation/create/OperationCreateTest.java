package com.server.server.operation.create;

import com.server.server.domain.Card;
import com.server.server.dto.OperationDTO;
import com.server.server.operation.OperationApiBaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("API. OperationService. Create test")
public class OperationCreateTest extends OperationApiBaseTest {
    @BeforeEach
    void setUp() {
        createTestUserWithCard();
    }

    @Test
    @DisplayName("Create operation")
    public void createOperationTest() {
        Date operationDate = new Date();

        OperationDTO newOperation = new OperationDTO(9999_5555_5555_5555L, "Top up", "serverOperationTestUsername", 500.0, operationDate);
        operationService.createOperation(newOperation);

        Card foundedCard = cardRepo.findByNumber(9999_5555_5555_5555L);

        assertTrue(foundedCard
                .getOperations()
                .stream()
                .anyMatch(operation ->
                        operation.getCard().getUser().getUserName().equals("serverOperationTestUsername")
                                && operation.getCardNumber() == 9999_5555_5555_5555L
                                && operation.getTime().compareTo(operationDate) == 0));
    }
}
