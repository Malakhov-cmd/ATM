package com.atm.client.operation;

import com.atm.client.dto.UserDTO;
import com.atm.client.service.card.CreateCardService;
import com.atm.client.service.operation.OperationService;
import com.atm.client.service.user.CreateUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OperationApiBaseTest {
    @Autowired
    protected OperationService operationService;

    @Autowired
    protected CreateCardService createCardService;
    @Autowired
    protected CreateUserService createUserService;

    public void initTestUser() {
        UserDTO userDTO = new UserDTO("testNameOperationTest", "testPasswordOperationTest", null);
        createUserService.sendUserCreationRequestToServer(userDTO).get();
    }

    public void initTestUserWithTestCard() {
        UserDTO userDTO = new UserDTO("testNameOperationTest", "testPasswordOperationTest", null);
        createUserService.sendUserCreationRequestToServer(userDTO).get();

        createCardService.createCard(5555555555555555L, 555, "Test ownerOperationTest", 555, "testNameOperationTest");
    }
}
