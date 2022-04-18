package com.atm.client.service.card;

import com.atm.client.dto.CardDTO;
import com.atm.client.dto.OperationDTO;
import com.atm.client.service.validation.ValidatorService;
import com.atm.client.util.Links;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SelectCardService {
    private ValidatorService validatorService;

    private Environment env;

    private final KafkaTemplate<Long, String> kafkaSelectAllCardsTemplate;
    private volatile List<CardDTO> tmpUserCards;

    private final KafkaTemplate<Long, CardDTO> kafkaSelectCardTemplate;
    private volatile CardDTO cardDTO;

    public List<CardDTO> getCards(String username) {
        if (Objects.equals(env.getProperty("TypeOfSpringAppsCommunication"), "rest"))
            return getCardsRest(username);
        return getCardsKafka(username);
    }

    private List<CardDTO> getCardsRest(String username) {
        return validatorService.isValidUsername(username) ?
                getAllUserCards(username) :
                new ArrayList<>();
    }

    private List<CardDTO> getCardsKafka(String username) {
        return validatorService.isValidUsername(username) ?
                getAllUserCardsKafka(username) :
                new ArrayList<>();
    }

    private List<CardDTO> getAllUserCards(String username) {
        ResponseEntity<String> result = sendRequestToFindAllUserCards(username);

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return Arrays.asList(objectMapper.readValue(result.getBody(), CardDTO[].class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private List<CardDTO> getAllUserCardsKafka(String username) {
        this.tmpUserCards.clear();
        kafkaSelectAllCardsTemplate.send("client.request.selectAllCards", username);

        try {
            return getUserCardsWhenConsumerBeReady().get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error! type {}", e.getClass());
        }
        return new ArrayList<>();
    }

    private Future<List<CardDTO>> getUserCardsWhenConsumerBeReady() {
        return CompletableFuture
                .supplyAsync(() -> {
                    while (this.tmpUserCards.isEmpty()) ;
                    return this.tmpUserCards;
                });
    }

    @KafkaListener(
            id = "RequestSelectAll",
            topics = {"server.request.selectAllCards"},
            containerFactory = "singleFactorySelectAll"
    )
    public void selectAllCardsConsume(Set<CardDTO> cardsFromServer) {
        tmpUserCards.addAll(cardsFromServer);
    }

    public CardDTO getCard(String username, String cardNumber) {
        if (Objects.equals(env.getProperty("TypeOfSpringAppsCommunication"), "rest"))
            return getCardRest(username, cardNumber);
        return getCardKafka(username, cardNumber);
    }

    private CardDTO getCardRest(String username, String cardNumber) {
        return validatorService.isValidUsernameAndCardNumber(username, cardNumber) ?
                getUserCard(username, cardNumber) :
                new CardDTO();
    }

    private CardDTO getCardKafka(String username, String cardNumber) {
        return validatorService.isValidUsernameAndCardNumber(username, cardNumber) ?
                getCardKafkaProducer(username, cardNumber) :
                new CardDTO();
    }

    private CardDTO getCardKafkaProducer(String username, String cardNumber) {
        this.cardDTO = new CardDTO();
        kafkaSelectCardTemplate
                .send("client.request.selectCard", new CardDTO().setUsername(username).setNumber(Long.parseLong(cardNumber)));

        try {
            return getCardWhenConsumerBeReady().get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error! type {}", e.getClass());
        }

        return new CardDTO();
    }

    private Future<CardDTO> getCardWhenConsumerBeReady() {
        return CompletableFuture
                .supplyAsync(() -> {
                    while (this.cardDTO.getNumber() == 0L) ;
                    return this.cardDTO;
                });
    }

    @KafkaListener(
            id = "RequestSelectCard",
            topics = {"server.request.selectCard"},
            containerFactory = "singleFactory"
    )
    public void selectCardConsume(CardDTO cardFromServer) {
        this.cardDTO = cardFromServer;
    }

    private CardDTO getUserCard(String username, String cardNumber) {
        ResponseEntity<String> result = sendRequestToFindUserCard(username, cardNumber);

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            CardDTO fundedCard = objectMapper.readValue(result.getBody(), CardDTO.class);
            fundedCard.setOperationDTOList(fundedCard
                    .getOperationDTOList()
                    .stream()
                    .sorted(Comparator.comparing(OperationDTO::getTime))
                    .collect(Collectors.toList()));
            return fundedCard;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new CardDTO();
    }

    private ResponseEntity<String> sendRequestToFindAllUserCards(String username) {
        final String uri = Links.findAllUserCards.getLink() + username;

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(uri, String.class);
    }

    private ResponseEntity<String> sendRequestToFindUserCard(String username, String cardNumber) {
        final String uri = Links.findUserCard.getLink() + username + "&cardNumber=" + cardNumber;

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(uri, String.class);
    }
}
