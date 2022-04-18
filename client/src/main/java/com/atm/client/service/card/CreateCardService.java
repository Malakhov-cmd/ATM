package com.atm.client.service.card;

import com.atm.client.dto.CardDTO;
import com.atm.client.service.sender.Sender;
import com.atm.client.service.validation.ValidatorService;
import com.atm.client.util.Links;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
@AllArgsConstructor
@Slf4j
public class CreateCardService {
    private Sender<CardDTO> cardDTOSender;
    private ValidatorService validatorService;

    private Environment env;

    private final KafkaTemplate<Long, CardDTO> kafkaCreateCardTemplate;
    private volatile CardDTO tmpCardDto;

    public Optional<CardDTO> createCard(
            Long number, Integer dateValid, String owner, Integer CVV, String username
    ) {
        if (Objects.equals(env.getProperty("TypeOfSpringAppsCommunication"), "rest"))
            return createCardRest(number, dateValid, owner, CVV, username);
        return createCardKafka(number, dateValid, owner, CVV, username);
    }

    private Optional<CardDTO> createCardRest(
            Long number, Integer dateValid, String owner, Integer CVV, String username
    ) {
        if (validatorService.validateCardData(number, dateValid, owner, CVV)) {
            CardDTO cardDTO = new CardDTO(number, dateValid.shortValue(), owner, CVV.shortValue(), 0.0, username, new ArrayList<>());

            return cardDTOSender.sendCreationEntityRequestToServer(cardDTO, Links.createCard.getLink(), HttpMethod.POST);
        }

        log.error("Validation is failed! Card has incorrect data. {}", cardDTOSender);
        return Optional.empty();
    }

    private Optional<CardDTO> createCardKafka(
            Long number, Integer dateValid, String owner, Integer CVV, String username
    ) {
        if (validatorService.validateCardData(number, dateValid, owner, CVV)) {
            CardDTO cardDTO = new CardDTO(number, dateValid.shortValue(), owner, CVV.shortValue(), 0.0, username, new ArrayList<>());

            this.tmpCardDto = new CardDTO();
            kafkaCreateCardTemplate.send("client.request.create.card", cardDTO);

            try {
                return Optional.of(getNewCardWhenConsumerBeReady().get());
            } catch (InterruptedException | ExecutionException e) {
                log.error("Error! type {}", e.getClass());
            }
        }

        log.error("Validation is failed! Card has incorrect data. {}", cardDTOSender);
        return Optional.empty();
    }

    private Future<CardDTO> getNewCardWhenConsumerBeReady() {
        return CompletableFuture
                .supplyAsync(() -> {
                    while (this.tmpCardDto.getNumber() == 0L) ;
                    return this.tmpCardDto;
                });
    }

    @KafkaListener(
            id = "Response",
            topics = {"server.response.create.card"},
            containerFactory = "singleFactory"
    )
    public void cardCreationConsume(CardDTO newCardFromServer) {
        this.tmpCardDto = newCardFromServer;
    }
}
