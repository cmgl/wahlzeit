package org.wahlzeit.model;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class PizzaPhotoManagerTest {

    @Test
    public void getInstance() {
        // act + arrange
        PizzaPhotoManager pizzaPhotoManager = PizzaPhotoManager.getInstance();

        // assert
        assertTrue(pizzaPhotoManager instanceof PizzaPhotoManager);
    }

    @Test
    public void getPhoto() {
    }

    @Test
    public void testGetPhoto() {
    }

    @Test
    public void getPhotoFromId() {
    }

    @Test
    public void createPhoto() {
    }

    @Test
    public void getPhotoCache() {
    }

    @Test
    public void getVisiblePhoto() {
    }

    @Test
    public void findPhotosByOwner() {
    }

    @Test
    public void addPhoto() {
    }

    @Test
    public void testAddPhoto() {
    }
}
