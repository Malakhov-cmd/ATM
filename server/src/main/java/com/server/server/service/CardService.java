package com.server.server.service;

import com.server.server.domain.Card;
import com.server.server.domain.User;
import com.server.server.dto.CardDTO;
import com.server.server.dto.UserDTO;
import com.server.server.repo.CardRepo;
import com.server.server.repo.UserDetailsRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class CardService {
    private CardRepo cardRepo;
    private UserDetailsRepo userDetailsRepo;

    public void createCard(CardDTO cardDTO) {
        Optional<Card> findedCard = Optional
                .ofNullable(cardRepo.findByNumberAndCVV(
                        cardDTO.getNumber(),
                        cardDTO.getCVV()
                ));

        if (findedCard.isEmpty()) {
            log.info("Card not found! Start process of creation");

            User owner = userDetailsRepo
                    .findByUserName(cardDTO.getUsername());

            Card newCard = fillingNewCardData(cardDTO, owner);
            cardRepo.save(newCard);

            userDetailsRepo.save(insertNewCardToUser(newCard, owner));
            log.info("Card successfully created");
        }
    }

    private Card fillingNewCardData(CardDTO cardDTO, User owner) {
        Card newCard = new Card();

        newCard.setNumber(cardDTO.getNumber());
        newCard.setDateValid(cardDTO.getDateValid());
        newCard.setOwner(cardDTO.getOwner());
        newCard.setCVV(cardDTO.getCVV());
        newCard.setBalance(0.0);

        newCard.setUser(owner);

        return newCard;
    }

    private User insertNewCardToUser(Card newCard, User owner) {
        Set<Card> userCards = owner.getCards();
        userCards.add(newCard);

        owner.setCards(userCards);

        return owner;
    }

    public Set<Card> getUserCards(UserDTO userDTO) {
        return userDetailsRepo
                .findByUserName(userDTO.getUsername())
                .getCards();
    }
}
