package com.atm.client.config;

import com.atm.client.dto.CardDTO;
import com.atm.client.dto.OperationDTO;
import com.atm.client.dto.UserDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DTOsBeans {
    @Bean
    public UserDTO createUserDTO() {
        return new UserDTO();
    }

    @Bean
    public CardDTO createCardDTO() {
        return new CardDTO();
    }

    @Bean
    public OperationDTO createOperationDTO() {
        return new OperationDTO();
    }
}
