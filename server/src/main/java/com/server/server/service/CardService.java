package com.server.server.service;

import com.server.server.domain.Card;
import com.server.server.domain.User;
import com.server.server.dto.CardDTO;
import com.server.server.repo.CardRepo;
import com.server.server.repo.UserDetailsRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CardService {
    private CardRepo cardRepo;
    private UserDetailsRepo userDetailsRepo;

    public void createCard(CardDTO cardDTO){
        Optional<Card> findedCard = Optional
                .ofNullable(cardRepo.findByNumberAndCVV(
                        cardDTO.getNumber(),
                        cardDTO.getCVV()
                ));

        if (findedCard.isEmpty()) {
            log.info("Card not finded! Start process of creation");

            Card newCard = new Card();

            newCard.setNumber(cardDTO.getNumber());
            newCard.setDateValid(cardDTO.getDateValid());
            newCard.setOwner(cardDTO.getOwner());
            newCard.setCVV(cardDTO.getCVV());

            cardRepo.save(newCard);

            User owner = userDetailsRepo.findByUserNameAndPassword(cardDTO.getUsername(), cardDTO.getPassword());

            owner.getCards().add(newCard);

            userDetailsRepo.save(owner);
            log.info("Card successfully created");
        }
    }
}
