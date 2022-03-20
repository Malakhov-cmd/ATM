package com.atm.client.controller.card;

import com.atm.client.dto.CardDTO;
import com.atm.client.service.card.CreateCardService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/card")
@AllArgsConstructor
@Slf4j
public class CardController {
    private CreateCardService createCardService;

    @PostMapping("/add")
    public CardDTO createCard(
            @RequestParam Long number,
            @RequestParam String dateValid,
            @RequestParam String owner,
            @RequestParam String CVV,
            @RequestParam String username,
            @RequestParam String password
    ) {
        log.info("Request of card create registered." +
                " Incoming data -" +
                " card number: " + number +
                " card data valid: " + dateValid +
                " card owner name: " + owner +
                " card cvv code: " + CVV);

        return createCardService.createCard(number, dateValid, owner, CVV, username, password).orElse(new CardDTO());
    }
}
