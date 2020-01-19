package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class PizzaTest {

    @Test
    public void test() {
        // arrange
        String string = "";
        String[] stringArray = new String[0];

        // act + assert
        assertThrows(IllegalArgumentException.class,
                () -> {
                    new Pizza(null, null, null);
                    new Pizza(null, string, stringArray);
                });
    }
}