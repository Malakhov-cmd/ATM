package com.server.server.controller;

import com.server.server.dto.CardDTO;
import com.server.server.dto.UserDTO;
import com.server.server.service.CardService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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

    @GetMapping("/get/all")
    public Set<CardDTO> getUserCards(
            @RequestParam String username
    ) {
        log.info("Request of getting all cards." +
                " Incoming data -" +
                " username: " + username);

        return cardService.getUserCards(new UserDTO(username, null, null));
    }

    @GetMapping("/get")
    public CardDTO getUserCards(
            @RequestParam String username,
            @RequestParam String cardNumber
    ) {
        log.info("Request of getting card." +
                " Incoming data -" +
                " username: " + username +
                " card number: " + cardNumber );

        return cardService.getUserCard(username, cardNumber);
    }
}
