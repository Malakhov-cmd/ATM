package com.atm.client.service.validation;

import com.atm.client.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@Slf4j
public class ValidatorService {
    private Pattern cardNumberPattern = Pattern.compile("(([2-6]([0-9]{3})?)(([0-9]{4}?){3}))");
    private Pattern cardDatePattern = Pattern.compile("(0[1-9]|1[0-2])([0-9]{2})");
    private Pattern cardCVVPattern = Pattern.compile("[0-9]{3}");
    private Pattern ownerPattern = Pattern.compile("(([A-Z]+)\\s([A-Z]+))");

    private Pattern moneyValuePattern = Pattern.compile("^(0|[1-9]\\d*)([.,]\\d+)?");

    public boolean validateCardData(Long number, Integer dateValid, String owner, Integer CVV) {
        if (number != null && dateValid != null && owner != null && CVV != null && owner.length() < 128)
            return cardNumberPattern.matcher(number.toString()).matches()
                    && cardDatePattern.matcher(dateValid.toString().length() == 3 ? "0" + dateValid : dateValid.toString()).matches()
                    && cardCVVPattern.matcher(CVV.toString()).matches()
                    && ownerPattern.matcher(owner.toUpperCase()).matches();
        return false;
    }

    public boolean isValidUsername(String username) {
        return username != null && username.length() > 0 && username.length() < 128;
    }

    public boolean isValidUsernameAndCardNumber(String username, String cardNumber) {
        return username != null && username.length() > 0 && username.length() < 128 &&
                cardNumber != null && cardNumber.length() > 0 &&
                cardNumberPattern.matcher(cardNumber).matches();
    }

    public boolean isOperationDataValid(Long cardNumber, String type, String username, Double value) {
        return  cardNumber != null && type != null && username != null && value != null && username.length() < 128 &&
                moneyValuePattern.matcher(value.toString()).matches() &&
                cardNumberPattern.matcher(cardNumber.toString()).matches() &&
                (type.equals("Top up") || type.equals("Withdraw"));
    }

    public boolean isValidUserDTO(UserDTO userDTO) {
        return userDTO.getUsername() != null && userDTO.getUsername().length() > 0 && userDTO.getUsername().length() < 128
                && userDTO.getPassword() != null && userDTO.getPassword().length() > 0 && userDTO.getPassword().length() < 128;
    }
}
