package com.server.server.controller;

import com.server.server.dto.OperationDTO;
import com.server.server.service.OperationService;
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
    public boolean createNewOperation(
            @RequestBody OperationDTO operationDTO
    ) {
        log.info("Request of withdraw." +
                " Incoming data -" +
                " card number: " + operationDTO.getCardNumber() +
                " operation type: " + operationDTO.getType() +
                " username: " + operationDTO.getUsername() +
                " value: " + operationDTO.getValue() +
                " date: " + operationDTO.getTime());

        return operationService.createOperation(operationDTO);
    }
}
