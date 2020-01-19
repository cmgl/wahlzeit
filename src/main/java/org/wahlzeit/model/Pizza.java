package org.wahlzeit.model;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Pizza {

    private static final Logger log = Logger.getLogger(Pizza.class.getName());

    protected PizzaType pizzaType;
    protected String pizzaName;
    protected String[] ingredients;

    public Pizza(PizzaType pizzaType, String pizzaName, String[] ingredients){
        if(null == pizzaType){
            log.log(Level.WARNING, "pizzaType object argument cannot be null");
            throw new IllegalArgumentException("pizzaType object argument cannot be null");
        }
        if(null == pizzaName || pizzaName.equals("")){
            log.log(Level.WARNING, "pizzaName value argument not valid");
            throw new IllegalArgumentException("pizzaName value argument not valid");
        }
        if(null == ingredients || ingredients.length == 1){
            log.log(Level.WARNING, "ingredients value argument not valid");
            throw new IllegalArgumentException("ingredients value argument not valid");
        }

        this.pizzaType = pizzaType;
        this.pizzaName = pizzaName;
        this.ingredients = ingredients;
    }

    public PizzaType getPizzaType() {
        return pizzaType;
    }

    public String getPizzaName() {
        return pizzaName;
    }

    public String[] getIngredients() {
        return ingredients;
    }
}
