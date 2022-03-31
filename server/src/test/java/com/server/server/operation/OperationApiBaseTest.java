package com.server.server.operation;

import com.server.server.dto.CardDTO;
import com.server.server.dto.UserDTO;
import com.server.server.repo.CardRepo;
import com.server.server.repo.OperationRepo;
import com.server.server.service.CardService;
import com.server.server.service.OperationService;
import com.server.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;

@SpringBootTest
@TestPropertySource(properties = {"PROFILE = test"})
public class OperationApiBaseTest {
    @Autowired
    protected OperationRepo operationRepo;
    @Autowired
    protected CardRepo cardRepo;

    @Autowired
    protected OperationService operationService;
    @Autowired
    protected UserService userService;
    @Autowired
    protected CardService cardService;

    protected void createTestUserWithCard(){
        UserDTO newUser = new UserDTO("serverOperationTestUsername", "serverOperationTestPassword", null);
        userService.createUser(newUser);

        short dateValid = 1212;
        short CVV = 777;
        CardDTO newCard = new CardDTO(9999_5555_5555_5555L, dateValid, "Robert Pier", CVV, 0.0, "serverOperationTestUsername", new ArrayList<>());
        cardService.createCard(newCard);
    }
}
