package org.wahlzeit.model;

import org.junit.Test;

public class PizzaPhotoTest {

    @Test
    public void getCaption() {
        // arrange
        PizzaPhoto pizzaPhoto = new PizzaPhoto();
        String pizzaName = "Hawaii";

        // act
        pizzaPhoto.setPizzaName(pizzaName);

        // assert
        String string = pizzaPhoto.getCaption(new EnglishModelConfig());
        System.out.println(string);
    }

    @Test
    public void setPizzaName() {
    }

    @Test
    public void getMyPizzaName() {
    }

    @Test
    public void setMyPizzaDescription() {
    }

    @Test
    public void getMyPizzaDescription() {
    }

    @Test
    public void setMyPizzaIngredients() {
    }

    @Test
    public void getMyPizzaIngredients() {
    }
}