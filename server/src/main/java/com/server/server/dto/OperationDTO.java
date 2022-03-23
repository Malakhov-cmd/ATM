package com.server.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OperationDTO {
    private Long cardNumber;
    private String type;
    private String username;
    private Double value;

    private Date time;
}
