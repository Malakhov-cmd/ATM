package com.atm.client.controller.operation;

import com.atm.client.dto.OperationDTO;
import com.atm.client.service.operation.OperationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/operation")
@AllArgsConstructor
@Slf4j
public class OperationController {
    private OperationService operationService;

    @PostMapping("/create")
    public OperationDTO createNewOperation(
            @RequestParam Long cardNumber,
            @RequestParam String type,
            @RequestParam String username,
            @RequestParam Double value

    ) {
        log.info("Request of withdraw." +
                " Incoming data -" +
                " card number: " + cardNumber +
                " operation type: " + type +
                " username: " + username +
                " value: " + value);

        return operationService.creationOperationRequest(cardNumber, type, username, value);
    }
}
