package com.atm.client.service.operation;

import com.atm.client.dto.OperationDTO;
import com.atm.client.service.sender.Sender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.regex.Pattern;

@Service
@Slf4j
public class OperationService {
    Pattern moneyValuePattern = Pattern.compile("^(0|[1-9]\\d*)([.,]\\d+)?");

    public OperationDTO creationOperationRequest(Long cardNumber, String type, String username, Double value){
        if (moneyValuePattern.matcher(value.toString()).matches()){
            OperationDTO operationDTO  = new OperationDTO(cardNumber, type, username, value, new Date());

            Sender<OperationDTO> cardDTOSender = new Sender<>();
            if(cardDTOSender.sendCreationEntityRequestToServer(operationDTO, "http://localhost:9090/operation/create", HttpMethod.POST).isPresent()){
                return operationDTO;
            }
        }
        return new OperationDTO();
    }
}
