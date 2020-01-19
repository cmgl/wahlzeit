package org.wahlzeit.model;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class PizzaManagerTest {

    private static PizzaManager pizzaManager;

    @BeforeClass
    public static void setUp() {
        pizzaManager = PizzaManager.getInstance();
    }

    @Test
    public void getInstance() {
        // arrange + act
        PizzaManager pizzaManager2 = PizzaManager.getInstance();

        // assert
        assertEquals(pizzaManager2, pizzaManager);
    }

    @Test
    public void createPizza() {
        // arrange
        String string = "";
        String[] stringArray = new String[0];

        // act + assert
        assertThrows(IllegalArgumentException.class,
                () -> {
                    pizzaManager.createPizza(null, null, null);
                    pizzaManager.createPizza(string, string, stringArray);
                });
    }
}