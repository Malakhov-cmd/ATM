package com.server.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CardDTO {
    private Long number;
    private String dateValid;
    private String owner;
    private String CVV;

    private Double balance;

    private String username;
}
