package com.atm.client.card.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Validation. Card. Positive scenario")
public class PositiveCardValidationTest {
    Pattern cardNumberPattern = Pattern.compile("(([2-6]([0-9]{3})?)(([0-9]{4}?){3}))");
    Pattern cardDatePattern = Pattern.compile("(0[1-9]|1[0-2])([0-9]{2})");
    Pattern cardCVVPattern = Pattern.compile("[0-9]{3}");
    Pattern cardOwnerPattern = Pattern.compile("(([A-Z]+)\\s([A-Z]+))");

    @Test
    @DisplayName("Insertion card number with valid symbols")
    public void insertionCardNumberWithValidSymbols(){
        assertTrue(cardNumberPattern.matcher("4444888833335555").matches());
    }

    @Test
    @DisplayName("Insertion card date with valid symbols")
    public void insertionCardDateWithValidSymbols(){
        assertTrue(cardDatePattern.matcher("0123").matches());
        assertTrue(cardDatePattern.matcher("0903").matches());
        assertTrue(cardDatePattern.matcher("1100").matches());
        assertTrue(cardDatePattern.matcher("1212").matches());
    }

    @Test
    @DisplayName("Insertion card CVV with valid symbols")
    public void insertionCardCVVWithValidSymbols(){
        assertTrue(cardCVVPattern.matcher("123").matches());
        assertTrue(cardCVVPattern.matcher("999").matches());
    }

    @Test
    @DisplayName("Insertion card owner with valid symbols")
    public void insertionCardOwnerWithValidSymbols(){
        assertTrue(cardOwnerPattern.matcher("James Cook".toUpperCase()).matches());
        assertTrue(cardOwnerPattern.matcher("Ji Soon".toUpperCase()).matches());
        assertTrue(cardOwnerPattern.matcher("Andy Dupry".toUpperCase()).matches());
    }
}
