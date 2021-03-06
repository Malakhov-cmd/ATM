package com.server.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CardDTO {
    private long number;
    private short dateValid;
    private String owner;
    private short CVV;

    private double balance;

    private String username;

    List<OperationDTO> operationDTOList;
}
