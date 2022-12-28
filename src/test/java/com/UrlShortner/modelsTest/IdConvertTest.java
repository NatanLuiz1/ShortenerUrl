package com.UrlShortner.modelsTest;

import com.UrlShortner.models.IdConvert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class IdConvertTest {

    @Test
    @DisplayName("Test of instance IdConvert")
public void TestInstanceIdConvert(){
        IdConvert id1 = IdConvert.INSTANCE;
        IdConvert id2 = IdConvert.INSTANCE;

        Assertions.assertEquals(id1, id2);
    }
}
