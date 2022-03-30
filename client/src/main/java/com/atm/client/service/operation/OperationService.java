package com.atm.client.service.operation;

import com.atm.client.dto.OperationDTO;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.regex.Pattern;

@Service
@NoArgsConstructor
@Slf4j
public class OperationService {

    public OperationDTO creationOperationRequest(Long cardNumber, String type, String username, Double value) {

        if (isOperationDataValid(cardNumber, type, username, value)) {
            OperationDTO operationDTO = new OperationDTO(cardNumber, type, username, value, new Date());

            final String uri = "http://localhost:9090/operation/create";

            RestTemplate restTemplate = new RestTemplate();
            if (Boolean.TRUE.equals(restTemplate.postForEntity(uri, operationDTO, Boolean.class).getBody())){
                return operationDTO;
            }
        }
        return new OperationDTO();
    }

    private boolean isOperationDataValid(Long cardNumber, String type, String username, Double value) {
        Pattern moneyValuePattern = Pattern.compile("^(0|[1-9]\\d*)([.,]\\d+)?");
        Pattern cardNumberPattern = Pattern.compile("(([2-6]([0-9]{3})?)(([0-9]{4}?){3}))");

        return  cardNumber != null && type != null && username != null && value != null && username.length() < 128 &&
                moneyValuePattern.matcher(value.toString()).matches() &&
                cardNumberPattern.matcher(cardNumber.toString()).matches() &&
                (type.equals("Top up") || type.equals("Withdraw"));
    }
}
