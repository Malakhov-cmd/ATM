package com.server.server.controller;

import com.server.server.domain.Card;
import com.server.server.domain.User;
import com.server.server.dto.CardDTO;
import com.server.server.dto.UserDTO;
import com.server.server.repo.CardRepo;
import com.server.server.repo.UserDetailsRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/card")
@AllArgsConstructor
@Slf4j
public class CardController {
    private CardRepo cardRepo;
    private UserDetailsRepo userDetailsRepo;

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

        Optional<Card> findedCard = Optional
                .ofNullable(cardRepo.findByNumberAndCVV(
                        cardDTO.getNumber(),
                        cardDTO.getCVV()
                ));

        if (findedCard.isEmpty()) {
            Card newCard = new Card();

            newCard.setNumber(cardDTO.getNumber());
            newCard.setDateValid(cardDTO.getDateValid());
            newCard.setOwner(cardDTO.getOwner());
            newCard.setCVV(cardDTO.getCVV());

            cardRepo.save(newCard);

            User owner = userDetailsRepo.findByUserNameAndPassword(cardDTO.getUsername(), cardDTO.getPassword());

            owner.getCards().add(newCard);

            userDetailsRepo.save(owner);
        }
    }
}
