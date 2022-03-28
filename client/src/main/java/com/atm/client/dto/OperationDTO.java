package com.atm.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OperationDTO {
    private long cardNumber;
    private String type;
    private String username;
    private double value;

    private Date time;
}
