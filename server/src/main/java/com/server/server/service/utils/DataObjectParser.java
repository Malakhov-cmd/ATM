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
        return new Card()
                .setNumber(cardDTO.getNumber())
                .setDateValid(cardDTO.getDateValid())
                .setOwner(cardDTO.getOwner())
                .setCVV(cardDTO.getCVV())
                .setBalance(cardDTO.getBalance());
    }

    public CardDTO cardDAOtoCardDTO(Card card) {
        return new CardDTO(card.getNumber(), card.getDateValid(), card.getOwner(), card.getCVV(), card.getBalance(), card.getUser().getUserName(),
                card.getOperations()
                        .stream()
                        .map(operation -> new OperationDTO(operation.getCardNumber(), operation.getType(), operation.getUsername(), operation.getValue(), operation.getTime()))
                        .collect(Collectors.toList()));
    }

    public Operation cardOperationDTOtoCardOperationDAO(OperationDTO operationDTO) {
        return new Operation()
                .setCardNumber(operationDTO.getCardNumber())
                .setType(operationDTO.getType())
                .setUsername(operationDTO.getUsername())
                .setValue(operationDTO.getValue())
                .setTime(operationDTO.getTime());
    }

    public User userDTOtoUserDAO(UserDTO userDTO) {
        return new User()
                .setUserName(userDTO.getUsername())
                .setPassword(userDTO.getPassword());
    }
}
