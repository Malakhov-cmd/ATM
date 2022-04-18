package com.atm.client.service.operation;

import com.atm.client.dto.OperationDTO;
import com.atm.client.service.validation.ValidatorService;
import com.atm.client.util.Links;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
@AllArgsConstructor
@Slf4j
public class OperationService {
    private ValidatorService validatorService;

    private Environment env;

    private final KafkaTemplate<Long, OperationDTO> kafkaCreateOperationDTOTemplate;
    private volatile OperationDTO operationDTO;

    public OperationDTO creationOperationRequest(
            Long cardNumber, String type, String username, Double value
    ) {
        if (Objects.equals(env.getProperty("TypeOfSpringAppsCommunication"), "rest"))
            return creationOperationRequestRest(cardNumber, type, username, value);
        return creationOperationRequestKafka(cardNumber, type, username, value);
    }

    private OperationDTO creationOperationRequestRest(Long cardNumber, String type, String username, Double value) {

        if (validatorService.isOperationDataValid(cardNumber, type, username, value)) {
            OperationDTO operationDTO = new OperationDTO(cardNumber, type, username, value, new Date());

            RestTemplate restTemplate = new RestTemplate();
            if (Boolean.TRUE.equals(restTemplate.postForEntity(Links.createOperation.getLink(), operationDTO, Boolean.class).getBody())) {
                return operationDTO;
            }
        }
        return new OperationDTO();
    }

    private OperationDTO creationOperationRequestKafka(
            Long cardNumber, String type, String username, Double value
    ) {
        operationDTO = new OperationDTO();

        if (validatorService.isOperationDataValid(cardNumber, type, username, value)) {
            OperationDTO operationDTO = new OperationDTO(cardNumber, type, username, value, new Date());

            kafkaCreateOperationDTOTemplate.send("operation.request.selectCard", operationDTO);

            try {
                return getOperationWhenConsumerBeReady().get();
            } catch (InterruptedException | ExecutionException e) {
                log.error("Error! type {}", e.getClass());
            }
        }
        return new OperationDTO();
    }

    private Future<OperationDTO> getOperationWhenConsumerBeReady() {
        return CompletableFuture
                .supplyAsync(() -> {
                    while (operationDTO.getCardNumber() == 0L) ;
                    return this.operationDTO;
                });
    }

    @KafkaListener(
            id = "RequestCreateOperation",
            topics = {"server.request.create.operation"},
            containerFactory = "singleFactoryOperation"
    )
    public void operationConsume(OperationDTO operationFromServer) {
        operationDTO = operationFromServer;
    }

}
