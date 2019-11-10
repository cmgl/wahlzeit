package org.wahlzeit.model;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class PizzaPhotoFactoryTest {

    private static PizzaPhotoFactory pizzaPhotoFactory;

    @BeforeClass
    public static void setUp() throws Exception {
        pizzaPhotoFactory = PizzaPhotoFactory.getInstance();
    }

    @AfterClass
    public static void tearDown() throws Exception {

    }

    @Test
    public void getInstance() {
        // arrange + act
        // in setUp

        // assert
        assertTrue(pizzaPhotoFactory != null);
        assertTrue(pizzaPhotoFactory instanceof PizzaPhotoFactory);
    }

    @Test
    public void testCreatePhoto() {
        // arrange
        PizzaPhoto pizzaPhotoNoId = pizzaPhotoFactory.createPhoto();
        int idForPizzaPhoto = 88888888;
        PizzaPhoto pizzaPhotoWithId = pizzaPhotoFactory.createPhoto(new PhotoId(idForPizzaPhoto));

        // act
        int pizzaPhotoId = pizzaPhotoWithId.getId().asInt();

        // assert
        assertTrue(pizzaPhotoNoId != null);
        assertTrue(pizzaPhotoWithId != null);
        assertTrue(pizzaPhotoId == pizzaPhotoId);
    }

    @Test
    public void loadPhoto() {
    }

    @Test
    public void createPhotoFilter() {
    }

}