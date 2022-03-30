package com.atm.client.card;

import com.atm.client.dto.UserDTO;
import com.atm.client.service.card.CreateCardService;
import com.atm.client.service.card.SelectCardService;
import com.atm.client.service.user.CreateUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CardApiBaseTest {
    @Autowired
    protected CreateCardService createCardService;
    @Autowired
    protected SelectCardService selectCardService;

    @Autowired
    protected CreateUserService createUserService;

    public void initTestUser() {
        UserDTO userDTO = new UserDTO("testNameCardTest", "testPasswordCardTest", null);
        createUserService.sendUserCreationRequestToServer(userDTO).get();
    }

    public void initTestUserWithFourTestCard() {
        UserDTO userDTO = new UserDTO("testNameCardTest", "testPasswordCardTest", null);
        createUserService.sendUserCreationRequestToServer(userDTO).get();

        createCardService.createCard(4444444444444444L, 222, "Test ownerCardTesting", 111, "testNameCardTest");
        createCardService.createCard(4444555555555555L, 333, "Test ownerCardTesting", 222, "testNameCardTest");
        createCardService.createCard(4444666666666666L, 444, "Test ownerCardTesting", 333, "testNameCardTest");
        createCardService.createCard(4444777777777777L, 555, "Test ownerCardTesting", 444, "testNameCardTest");

    }
}
