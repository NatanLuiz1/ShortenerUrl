package com.UrlShortner.modelsTest;

import com.UrlShortner.models.UrlValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UrlValidatorTest {

//    @BeforeEach
//    public void UrlValidatorInstance(){
//        UrlValidator urlValidator = UrlValidator.INSTANCE;
//    }

    @Test
    @DisplayName("Test of true url")
    public void UrlTest(){
        UrlValidator urlValidator = UrlValidator.INSTANCE;
        boolean test = urlValidator.validateURL("https://google.com");
        Assertions.assertTrue(test);

    }
}
