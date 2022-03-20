package com.server.server.controller;

import com.server.server.dto.CardDTO;
import com.server.server.service.CardService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/card")
@AllArgsConstructor
@Slf4j
public class CardController {
    private CardService cardService;

    @PostMapping("/create")
    public void registerNewUser(
            @RequestBody CardDTO cardDTO
    ) {
        log.info("Request of card create registered." +
                " Incoming data -" +
                " card number: " + cardDTO.getNumber() +
                " card data valid: " + cardDTO.getDateValid() +
                " card owner name: " + cardDTO.getOwner() +
                " card cvv code: " + cardDTO.getCVV());

        cardService.createCard(cardDTO);
    }
}
