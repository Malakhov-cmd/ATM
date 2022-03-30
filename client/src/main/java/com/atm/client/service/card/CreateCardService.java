package com.atm.client.service.card;

import com.atm.client.dto.CardDTO;
import com.atm.client.service.sender.Sender;
import com.atm.client.service.validation.ValidatorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CreateCardService {
    private Sender<CardDTO> cardDTOSender;
    private ValidatorService validatorService;

    public Optional<CardDTO> createCard(Long number, Integer dateValid, String owner, Integer CVV, String username) {
        if (validatorService.validateCardData(number, dateValid, owner, CVV)) {
            CardDTO cardDTO = new CardDTO(number, dateValid.shortValue(), owner, CVV.shortValue(), 0.0, username, new ArrayList<>());

            return cardDTOSender.sendCreationEntityRequestToServer(cardDTO, "http://localhost:9090/card/create", HttpMethod.POST);
        }

        log.error("Validation is failed! Card has incorrect data.");
        return Optional.empty();
    }
}
