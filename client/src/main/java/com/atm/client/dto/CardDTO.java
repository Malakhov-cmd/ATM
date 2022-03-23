package com.atm.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class CardDTO {
    private Long number;
    private String dateValid;
    private String owner;
    private String CVV;

    private Double balance;

    private String username;

    List<OperationDTO> operationDTOList;
}
