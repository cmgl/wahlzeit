package org.wahlzeit.model;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class PizzaTypeTest {

    private static PizzaType pizzaTypeRund;
    private static PizzaType pizzaTypeSmall;
    private static PizzaType pizzaTypeMedium;
    private static PizzaType pizzaTypeBig;

    private static PizzaType pizzaTypeViereckig;
    private static PizzaType pizzaTypeFamily;
    private static PizzaType pizzaTypeParty;

    private static Pizza pizzaCapricciosa;
    private static Pizza pizzaHawaii;
    private static Pizza pizzaQuattroFormaggi;

    private static String[] ingredientsCapricciosa = {"Truthahnschinken", "Champignons", "Artischocken"};
    private static String[] ingredientsHawaii = {"Truthahnschinken", "Ananas"};
    private static String[] ingredientsQuattroFormaggi = {"Mozzarella", "Gouda", "Gorgonzola", "Schafskäse"};

    private static String stringEmpty = "";
    private static String[] stringArrayEmpty = new String[0];

    @BeforeClass
    public static void setUp() {
        pizzaTypeRund = new PizzaType("Rund");
        pizzaTypeSmall = new PizzaType("Small");
        pizzaTypeMedium = new PizzaType("Medium");
        pizzaTypeBig = new PizzaType("Big");

        pizzaTypeViereckig = new PizzaType("Viereckig");
        pizzaTypeFamily = new PizzaType("Family");
        pizzaTypeParty = new PizzaType("Party");
    }

    @Test
    public void createInstance() {
        // arrange + act + assert (should not throw any exception)
        pizzaCapricciosa = pizzaTypeParty.createInstance("Capricciosa", ingredientsHawaii);
    }

    @Test
    public void hasInstance() {
        // arrange
        pizzaHawaii = pizzaTypeFamily.createInstance("Hawaii", ingredientsHawaii);

        // act
        boolean result = pizzaTypeFamily.hasInstance(pizzaHawaii);

        // assert
        assertEquals(true, result);
    }

    @Test
    public void getTypeName() {
        // arrange
        String family = "Family";

        // act + arrange
        assertEquals(family, pizzaTypeFamily.getTypeName());
    }

    @Test
    public void getSuperType() {
        // arrange
        pizzaTypeSmall.setSuperType(pizzaTypeRund);

        // act
        PizzaType result = pizzaTypeSmall.getSuperType();

        // assert
        assertEquals(pizzaTypeRund, result);
    }

    @Test
    public void setSuperType() {
        // arrange
        pizzaTypeMedium.setSuperType(pizzaTypeRund);

        // act
        PizzaType result = pizzaTypeMedium.getSuperType();

        // assert
        assertEquals(pizzaTypeRund, result);
    }

    @Test
    public void getSubTypeIterator() {
        // arrange + act
        pizzaTypeBig.setSuperType(pizzaTypeRund);

        Iterator iterator = pizzaTypeRund.getSubTypeIterator();
        boolean flag = false;

        while(iterator.hasNext()){
            if(iterator.next() == pizzaTypeBig)
                flag = true;
        }

        // assert
        assertEquals(true, flag);
    }

    @Test
    public void addSubType() {
        // arrange + act
        pizzaTypeViereckig.addSubType(pizzaTypeFamily);
        pizzaTypeViereckig.addSubType(pizzaTypeParty);

        // assert
        assertEquals(pizzaTypeViereckig, pizzaTypeFamily.getSuperType());
        assertEquals(pizzaTypeViereckig, pizzaTypeParty.getSuperType());
    }

    @Test
    public void isSubtype() {
        // arrange + act + assert
        assertTrue(pizzaTypeViereckig.isSubtype());
    }
}