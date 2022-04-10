package com.atm.client.card.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertFalse;

@DisplayName("Validation. Card. Negative scenario")
public class NegativeCardValidationTest {
    Pattern cardNumberPattern = Pattern.compile("(([2-6]([0-9]{3})?)(([0-9]{4}?){3}))");
    Pattern cardDatePattern = Pattern.compile("(0[1-9]|1[0-2])([0-9]{2})");
    Pattern cardCVVPattern = Pattern.compile("[0-9]{3}");
    Pattern cardOwnerPattern = Pattern.compile("(([A-Z]+)\\s([A-Z]+))");

    @Test
    @DisplayName("Insertion card number with symbols")
    public void insertionCardNumberWithSymbols(){
        assertFalse(cardNumberPattern.matcher("4444s88812345678").matches());
    }

    @Test
    @DisplayName("Insertion card number with signs")
    public void insertionCardNumberWithSigns(){
        assertFalse(cardNumberPattern.matcher("44448.8812345678").matches());
        assertFalse(cardNumberPattern.matcher("44448+8812345678").matches());
        assertFalse(cardNumberPattern.matcher("44448-8812345678").matches());
        assertFalse(cardNumberPattern.matcher("44448 88812345678").matches());
        assertFalse(cardNumberPattern.matcher("44448 8812345678").matches());
    }

    @Test
    @DisplayName("Insertion thirteen month")
    public void insertionThirteenMonth(){
        assertFalse(cardDatePattern.matcher("1326").matches());
    }

    @Test
    @DisplayName("Insertion years next century")
    public void insertionYearsNextCentury(){
        assertFalse(cardDatePattern.matcher("13101").matches());
    }

    @Test
    @DisplayName("Insertion date with signs")
    public void insertionDateWithSigns(){
        assertFalse(cardDatePattern.matcher("+1326").matches());
        assertFalse(cardDatePattern.matcher("+326").matches());
        assertFalse(cardDatePattern.matcher(".326").matches());
        assertFalse(cardDatePattern.matcher(" 326").matches());
        assertFalse(cardDatePattern.matcher("*326").matches());
    }

    @Test
    @DisplayName("Insertion name with special signs")
    public void insertionNameWithSpecialSigns(){
        assertFalse(cardOwnerPattern.matcher("+Jack Williams").matches());
        assertFalse(cardOwnerPattern.matcher("-Jack Williams").matches());
        assertFalse(cardOwnerPattern.matcher("*Jack Williams").matches());
        assertFalse(cardOwnerPattern.matcher(".Jack Williams").matches());

        assertFalse(cardOwnerPattern.matcher("Jack +Williams").matches());
        assertFalse(cardOwnerPattern.matcher("Jack -Williams").matches());
        assertFalse(cardOwnerPattern.matcher("Jack *Williams").matches());
        assertFalse(cardOwnerPattern.matcher("Jack .Williams").matches());

        assertFalse(cardOwnerPattern.matcher("Jack Williams+").matches());
        assertFalse(cardOwnerPattern.matcher("Jack Williams-").matches());
        assertFalse(cardOwnerPattern.matcher("Jack Williams*").matches());
        assertFalse(cardOwnerPattern.matcher("Jack Williams.").matches());

        assertFalse(cardOwnerPattern.matcher("Jack + Williams").matches());
        assertFalse(cardOwnerPattern.matcher("Jack - Williams").matches());
        assertFalse(cardOwnerPattern.matcher("Jack * Williams").matches());
        assertFalse(cardOwnerPattern.matcher("Jack . Williams").matches());

        assertFalse(cardOwnerPattern.matcher("J+ack Williams").matches());
        assertFalse(cardOwnerPattern.matcher("Jack Wi*lliams").matches());
        assertFalse(cardOwnerPattern.matcher("Jack Willia-ms").matches());
        assertFalse(cardOwnerPattern.matcher("J.ack Williams").matches());
    }

    @Test
    @DisplayName("Insertion name with digits")
    public void insertionNameWithDigits(){
        assertFalse(cardOwnerPattern.matcher("1Jack Williams").matches());
        assertFalse(cardOwnerPattern.matcher("Jack2 Williams").matches());
        assertFalse(cardOwnerPattern.matcher("Jack Will3iams").matches());
        assertFalse(cardOwnerPattern.matcher("Jack Williams4").matches());
    }

    @Test
    @DisplayName("Insertion CVV with more or less digits")
    public void insertionCVVWithMoreOrLessDigits(){
        assertFalse(cardCVVPattern.matcher("1").matches());
        assertFalse(cardCVVPattern.matcher("12").matches());
        assertFalse(cardCVVPattern.matcher("1111").matches());
    }

    @Test
    @DisplayName("Insertion CVV with signs")
    public void insertionCVVWithSigns(){
        assertFalse(cardCVVPattern.matcher("12+").matches());
        assertFalse(cardCVVPattern.matcher("1*3").matches());
        assertFalse(cardCVVPattern.matcher(".12").matches());
        assertFalse(cardCVVPattern.matcher("12 ").matches());
    }
}
