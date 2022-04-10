package com.atm.client.controller.card;

import com.atm.client.dto.CardDTO;
import com.atm.client.dto.UserDTO;
import com.atm.client.service.card.CreateCardService;
import com.atm.client.service.card.SelectCardService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/card")
@AllArgsConstructor
@Slf4j
public class CardController {
    private CreateCardService createCardService;
    private SelectCardService selectCardService;

    @PostMapping("/add")
    public CardDTO createCard(
            @RequestParam Long number,
            @RequestParam Integer dateValid,
            @RequestParam String owner,
            @RequestParam Integer CVV,
            @RequestParam String username
    ) {
        log.info("Request of card create registered." +
                " Incoming data -" +
                " card number: " + number +
                " card data valid: " + dateValid +
                " card owner name: " + owner +
                " card cvv code: " + CVV);

        return createCardService.createCard(number, dateValid, owner, CVV, username).orElse(new CardDTO());
    }

    @GetMapping("/get/all")
    public List<CardDTO> getAllUserCards(
            @RequestParam String username
    ) {
        log.info("Request of getting all cards." +
                " Incoming data -" +
                " username: " + username);

        return selectCardService.getCards(username);
    }

    @GetMapping("/get")
    public CardDTO getUserCard(
            @RequestParam String username,
            @RequestParam String cardNumber
    ) {
        log.info("Request of getting card." +
                " Incoming data -" +
                " username: " + username +
                " card number: " + cardNumber );

        return selectCardService.getCard(username, cardNumber);
    }
}
