package com.atm.client.card;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

public class CardValidationTest {
    Pattern cardNumberPattern = Pattern.compile("(([2-6]([0-9]{3})?)(([0-9]{4}?){3}))");
    Pattern cardDatePattern = Pattern.compile("(0[1-9]|1[0-2])([0-9]{2})");
    Pattern cardCVVPattern = Pattern.compile("[0-9]{3}");
    Pattern ownerPattern = Pattern.compile("(([A-Z]+)\\s([A-Z]+))");

    @Test
    public void insertionCardNumberWithSymbols(){
        Assertions.assertFalse(cardNumberPattern.matcher("4444s88812345678").matches());
    }

    @Test
    public void insertionCardNumberWithSigns(){
        Assertions.assertFalse(cardNumberPattern.matcher("44448.8812345678").matches());
        Assertions.assertFalse(cardNumberPattern.matcher("44448+8812345678").matches());
        Assertions.assertFalse(cardNumberPattern.matcher("44448-8812345678").matches());
        Assertions.assertFalse(cardNumberPattern.matcher("44448 88812345678").matches());
        Assertions.assertFalse(cardNumberPattern.matcher("44448 8812345678").matches());
    }

    @Test
    public void insertionThirteenMonth(){
        Assertions.assertFalse(cardDatePattern.matcher("1326").matches());
    }

    @Test
    public void insertionYearsNextCentury(){
        Assertions.assertFalse(cardDatePattern.matcher("13101").matches());
    }

    @Test
    public void insertionWithSigns(){
        Assertions.assertFalse(cardDatePattern.matcher("+1326").matches());
        Assertions.assertFalse(cardDatePattern.matcher("+326").matches());
        Assertions.assertFalse(cardDatePattern.matcher(".326").matches());
        Assertions.assertFalse(cardDatePattern.matcher(" 326").matches());
        Assertions.assertFalse(cardDatePattern.matcher("*326").matches());
    }

    @Test
    public void insertionNameWithSpecialSigns(){
        Assertions.assertFalse(ownerPattern.matcher("+Jack Williams").matches());
        Assertions.assertFalse(ownerPattern.matcher("-Jack Williams").matches());
        Assertions.assertFalse(ownerPattern.matcher("*Jack Williams").matches());
        Assertions.assertFalse(ownerPattern.matcher(".Jack Williams").matches());

        Assertions.assertFalse(ownerPattern.matcher("Jack +Williams").matches());
        Assertions.assertFalse(ownerPattern.matcher("Jack -Williams").matches());
        Assertions.assertFalse(ownerPattern.matcher("Jack *Williams").matches());
        Assertions.assertFalse(ownerPattern.matcher("Jack .Williams").matches());

        Assertions.assertFalse(ownerPattern.matcher("Jack Williams+").matches());
        Assertions.assertFalse(ownerPattern.matcher("Jack Williams-").matches());
        Assertions.assertFalse(ownerPattern.matcher("Jack Williams*").matches());
        Assertions.assertFalse(ownerPattern.matcher("Jack Williams.").matches());

        Assertions.assertFalse(ownerPattern.matcher("Jack + Williams").matches());
        Assertions.assertFalse(ownerPattern.matcher("Jack - Williams").matches());
        Assertions.assertFalse(ownerPattern.matcher("Jack * Williams").matches());
        Assertions.assertFalse(ownerPattern.matcher("Jack . Williams").matches());

        Assertions.assertFalse(ownerPattern.matcher("J+ack Williams").matches());
        Assertions.assertFalse(ownerPattern.matcher("Jack Wi*lliams").matches());
        Assertions.assertFalse(ownerPattern.matcher("Jack Willia-ms").matches());
        Assertions.assertFalse(ownerPattern.matcher("J.ack Williams").matches());
    }

    @Test
    public void insertionNameWithDigits(){
        Assertions.assertFalse(ownerPattern.matcher("1Jack Williams").matches());
        Assertions.assertFalse(ownerPattern.matcher("Jack2 Williams").matches());
        Assertions.assertFalse(ownerPattern.matcher("Jack Will3iams").matches());
        Assertions.assertFalse(ownerPattern.matcher("Jack Williams4").matches());
    }

    @Test
    public void insertionCVVWithMoreOrLessDigits(){
        Assertions.assertFalse(cardCVVPattern.matcher("1").matches());
        Assertions.assertFalse(cardCVVPattern.matcher("12").matches());
        Assertions.assertFalse(cardCVVPattern.matcher("1111").matches());
    }

    @Test
    public void insertionCVVWithSigns(){
        Assertions.assertFalse(cardCVVPattern.matcher("12+").matches());
        Assertions.assertFalse(cardCVVPattern.matcher("1*3").matches());
        Assertions.assertFalse(cardCVVPattern.matcher(".12").matches());
        Assertions.assertFalse(cardCVVPattern.matcher("12 ").matches());
    }
}
