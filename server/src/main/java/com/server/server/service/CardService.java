package com.server.server.service;

import com.server.server.domain.Card;
import com.server.server.domain.User;
import com.server.server.dto.CardDTO;
import com.server.server.dto.UserDTO;
import com.server.server.repo.CardRepo;
import com.server.server.repo.UserDetailsRepo;
import com.server.server.service.utils.DataObjectParser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
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

    private final KafkaTemplate<Long, Set<CardDTO>> kafkaSelectAllTemplate;
    private final KafkaTemplate<Long, CardDTO> kafkaTemplateToCreateCard;

    public synchronized void createCard(CardDTO cardDTO) {
        Optional<Card> foundCard = Optional
                .ofNullable(cardRepo.findByNumber(cardDTO.getNumber()));

        if (foundCard.isEmpty()) {
            log.info("Card not found! Start process of creation");

            User owner = userDetailsRepo.findByUserName(cardDTO.getUsername());

            Card newCard = fillingNewCardData(cardDTO, owner);
            cardRepo.save(newCard);

            owner.getCards().add(newCard);

            userDetailsRepo.save(owner);
            log.info("Card successfully created");
        }
    }

    @KafkaListener(
            id = "Request",
            topics = {"client.request.create.card"},
            containerFactory = "singleFactory"
    )
    public void createCardConsume(CardDTO cardDTOFromClient) {
        createCard(cardDTOFromClient);
        kafkaTemplateToCreateCard.send("server.response.create.card", cardDTOFromClient);
    }

    private Card fillingNewCardData(CardDTO cardDTO, User owner) {
        return dataObjectParser.cardDTOtoCardDAO(cardDTO)
                .setUser(owner)
                .setOperations(new ArrayList<>());
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

    @KafkaListener(
            id = "RequestSelectAll",
            topics = {"client.request.selectAllCards"},
            containerFactory = "singleFactoryToSelectAll"
    )
    public void selectAllCardsConsume(String username) {
        kafkaSelectAllTemplate.send("server.request.selectAllCards", getUserCards(new UserDTO().setUsername(username)));
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

    @KafkaListener(
            id = "RequestSelectCard",
            topics = {"client.request.selectCard"},
            containerFactory = "singleFactory"
    )
    public void selectCardConsume(CardDTO cardDTOFromClient) {
        kafkaTemplateToCreateCard.send("server.request.selectCard", getUserCard(cardDTOFromClient.getUsername(), String.valueOf(cardDTOFromClient.getNumber())));
    }
}
