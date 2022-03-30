package com.server.server.service;

import com.server.server.domain.Card;
import com.server.server.domain.User;
import com.server.server.dto.CardDTO;
import com.server.server.dto.OperationDTO;
import com.server.server.dto.UserDTO;
import com.server.server.repo.CardRepo;
import com.server.server.repo.UserDetailsRepo;
import com.server.server.service.utils.DataObjectParser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CardService {
    private CardRepo cardRepo;
    private UserDetailsRepo userDetailsRepo;

    private DataObjectParser dataObjectParser;

    public void createCard(CardDTO cardDTO) {
        Optional<Card> findedCard = Optional
                .ofNullable(cardRepo.findByNumber(cardDTO.getNumber()));

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
        return dataObjectParser.cardDTOtoCardDAO(cardDTO)
                .setUser(owner)
                .setOperations(new ArrayList<>());
    }

    private User insertNewCardToUser(Card newCard, User owner) {
        Set<Card> userCards = owner.getCards();
        userCards.add(newCard);

        owner.setCards(userCards);

        return owner;
    }

    public Set<CardDTO> getUserCards(UserDTO userDTO) {
        return userDetailsRepo
                .findByUserName(userDTO.getUsername())
                .getCards()
                .stream()
                .sorted(Comparator.comparing(Card::getId).reversed())
                .map(dataObjectParser::cardDAOtoCardDTO)
                .collect(Collectors.toSet());
    }

    public CardDTO getUserCard(String username, String cardNumber) {
        Optional<Card> card = userDetailsRepo
                .findByUserName(username)
                .getCards()
                .stream()
                .filter((cardItem) -> cardItem.getNumber().equals(Long.valueOf(cardNumber)))
                .findFirst();

        return card.isPresent() ? dataObjectParser.cardDAOtoCardDTO(card.get()) : new CardDTO();
    }
}
