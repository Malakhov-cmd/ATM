package com.server.server.service.utils;

import com.server.server.domain.Card;
import com.server.server.domain.Operation;
import com.server.server.domain.User;
import com.server.server.dto.CardDTO;
import com.server.server.dto.OperationDTO;
import com.server.server.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@Slf4j
public class DataObjectParser {
    public Card cardDTOtoCardDAO(CardDTO cardDTO) {
        Card newCard = new Card();

        newCard.setNumber(cardDTO.getNumber());
        newCard.setDateValid(cardDTO.getDateValid());
        newCard.setOwner(cardDTO.getOwner());
        newCard.setCVV(cardDTO.getCVV());
        newCard.setBalance(cardDTO.getBalance());

        return newCard;
    }

    public CardDTO cardDAOtoCardDTO(Card card) {
        return new CardDTO(card.getNumber(), card.getDateValid(), card.getOwner(), card.getCVV(), card.getBalance(), card.getUser().getUserName(),
                card.getOperations()
                        .stream()
                        .map(operation -> new OperationDTO(operation.getCardNumber(), operation.getType(), operation.getUsername(), operation.getValue(), operation.getTime()))
                        .collect(Collectors.toList()));
    }

    public Operation cardOperationDTOtoCardOperationDAO(OperationDTO operationDTO) {
        Operation newOperation = new Operation();

        newOperation.setCardNumber(operationDTO.getCardNumber());
        newOperation.setType(operationDTO.getType());
        newOperation.setUsername(operationDTO.getUsername());
        newOperation.setValue(operationDTO.getValue());
        newOperation.setTime(operationDTO.getTime());

        return newOperation;
    }

    public User userDTOtoUserDAO(UserDTO userDTO) {
        User newUser = new User();
        newUser.setUserName(userDTO.getUsername());
        newUser.setPassword(userDTO.getPassword());

        return newUser;
    }
}
