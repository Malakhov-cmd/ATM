package com.atm.client.service.operation;

import com.atm.client.dto.OperationDTO;
import com.atm.client.service.validation.ValidatorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
@AllArgsConstructor
@Slf4j
public class OperationService {
    private ValidatorService validatorService;

    public OperationDTO creationOperationRequest(Long cardNumber, String type, String username, Double value) {

        if (validatorService.isOperationDataValid(cardNumber, type, username, value)) {
            OperationDTO operationDTO = new OperationDTO(cardNumber, type, username, value, new Date());

            final String uri = "http://localhost:9090/operation/create";

            RestTemplate restTemplate = new RestTemplate();
            if (Boolean.TRUE.equals(restTemplate.postForEntity(uri, operationDTO, Boolean.class).getBody())){
                return operationDTO;
            }
        }
        return new OperationDTO();
    }
}
