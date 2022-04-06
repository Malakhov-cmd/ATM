package com.atm.client.service.card;

import com.atm.client.dto.CardDTO;
import com.atm.client.dto.OperationDTO;
import com.atm.client.service.validation.ValidatorService;
import com.atm.client.util.Links;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SelectCardService {
    private ValidatorService validatorService;

    public List<CardDTO> getCards(String username) {
        return validatorService.isValidUsername(username) ?
                getAllUserCards(username) :
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

    public CardDTO getCard(String username, String cardNumber) {
        return validatorService.isValidUsernameAndCardNumber(username, cardNumber) ?
                getUserCard(username, cardNumber) :
                new CardDTO();
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
