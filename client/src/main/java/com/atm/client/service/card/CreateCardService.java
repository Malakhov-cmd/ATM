package com.atm.client.service.card;

import com.atm.client.dto.CardDTO;
import com.atm.client.dto.UserDTO;
import com.atm.client.service.sender.Sender;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Slf4j
public class CreateCardService {
    private Sender<CardDTO> cardDTOSender;

    Pattern cardNumberPattern = Pattern.compile("(([2-6]([0-9]{3})?)(([0-9]{4}?){3}))");
    Pattern cardDatePattern = Pattern.compile("(0[1-9]|1[0-2])([0-9]{2})");
    Pattern cardCVVPattern = Pattern.compile("[0-9]{3}");
    Pattern ownerPattern = Pattern.compile("(([A-Z]+)\\s([A-Z]+))");

    public CreateCardService(Sender<CardDTO> cardDTOSender) {
        this.cardDTOSender = cardDTOSender;
    }

    public Optional<CardDTO> createCard(
            Long number, String dateValid, String owner, String CVV, String username
    ){
        if(validate(number, dateValid, owner, CVV)){
            CardDTO cardDTO = new CardDTO(number, dateValid, owner, CVV, 0.0, username, new ArrayList<>());

            return cardDTOSender.sendCreationEntityRequestToServer(cardDTO, "http://localhost:9090/card/create", HttpMethod.POST);
        }
        log.error("Validation is failed! Card has incorrect data.");
        return Optional.empty();
    }

    private boolean validate( Long number, String dateValid, String owner, String CVV){
        return cardNumberPattern.matcher(number.toString()).matches()
                && cardDatePattern.matcher(dateValid).matches()
                && cardCVVPattern.matcher(CVV).matches()
                && ownerPattern.matcher(owner.toUpperCase()).matches();
    }

}
