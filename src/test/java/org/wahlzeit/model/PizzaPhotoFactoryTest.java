package org.wahlzeit.model;

import org.junit.*;
import org.junit.rules.RuleChain;
import org.wahlzeit.testEnvironmentProvider.*;

import static org.junit.Assert.*;

public class PizzaPhotoFactoryTest {

    @ClassRule
    public static RuleChain ruleChain = RuleChain.
            outerRule(new LocalDatastoreServiceTestConfigProvider());

    private static PizzaPhotoFactory pizzaPhotoFactory;

    @Before
    public void setUp() {
        pizzaPhotoFactory = PizzaPhotoFactory.getInstance();
    }

    @Test
    public void getInstance() {
        // arrange + act
        // already in setUp

        // assert
        assertTrue(pizzaPhotoFactory instanceof PizzaPhotoFactory);
    }

    @Test
    public void createPhoto() {
        // arrange
        int idForPizzaPhoto = 88888888;

        // act
        PizzaPhoto pizzaPhotoNoId = pizzaPhotoFactory.createPhoto();
        PizzaPhoto pizzaPhotoWithId = pizzaPhotoFactory.createPhoto(new PhotoId(idForPizzaPhoto));
        int pizzaPhotoId = pizzaPhotoWithId.getId().asInt();

        // assert
        assertTrue(pizzaPhotoNoId != null);
        assertTrue(pizzaPhotoWithId != null);
        assertTrue(idForPizzaPhoto == pizzaPhotoId);
    }

    /**
     * A work-in-progress test case for loadPhoto()
     * Uncomment assert lines after the loading of Java objects from Google cloud storage is enabled back in PhotoFactory#loadPhoto()
     */
    @Test
    public void loadPhoto() {
        // arrange
        PizzaPhoto pizzaPhotoNoId = pizzaPhotoFactory.createPhoto();//new PizzaPhoto();
        int idFromPizzaPhoto = pizzaPhotoNoId.getId().asInt();
        int idForPizzaPhoto = 88888888;
        PizzaPhoto pizzaPhotoWithId = pizzaPhotoFactory.createPhoto(new PhotoId(idForPizzaPhoto));//new PizzaPhoto(new PhotoId(idForPizzaPhoto));

        // act
        PizzaPhoto loadPizzaPhotoNoId = pizzaPhotoFactory.loadPhoto(PhotoId.getIdFromInt(idFromPizzaPhoto));
        PizzaPhoto loadPizzaPhotoWithId = pizzaPhotoFactory.loadPhoto(PhotoId.getIdFromInt(idForPizzaPhoto));

        // assert
        //assertNotNull(loadPizzaPhotoNoId);
        //assertNotNull(loadPizzaPhotoWithId);
        //assertSame(loadPizzaPhotoNoId, pizzaPhotoNoId);
        //assertSame(loadPizzaPhotoWithId, pizzaPhotoWithId);

    }

    @Test
    public void createPhotoFilter() {
        // arrange + act
        PhotoFilter photoFilter = pizzaPhotoFactory.createPhotoFilter();
        Tags tags = photoFilter.getTags();

        // assert
        assertNotNull(photoFilter);
        assertEquals("pizza", tags.asString());

    }
}