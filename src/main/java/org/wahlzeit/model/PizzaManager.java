package org.wahlzeit.model;

import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PizzaManager {

    private static final Logger log = Logger.getLogger(Pizza.class.getName());

    private static Hashtable<String, Pizza> pizzas = new Hashtable<String, Pizza>();
    private static final PizzaManager pizzaManager = new PizzaManager();

    public static PizzaManager getInstance(){
        return pizzaManager;
    }

    public Pizza createPizza(String typeName, String pizzaName, String[] ingredients){
        if(null == typeName || typeName.equals("")){
            log.log(Level.WARNING, "typeName value argument not valid");
            throw new IllegalArgumentException("typeName value argument not valid");
        }
        if(null == pizzaName || pizzaName.equals("")){
            log.log(Level.WARNING, "pizzaName value argument not valid");
            throw new IllegalArgumentException("pizzaName value argument not valid");
        }
        if(null == ingredients || ingredients.length == 0){
            log.log(Level.WARNING, "ingredients value argument not valid");
            throw new IllegalArgumentException("ingredients value argument not valid");
        }

        String key = getKey(typeName, pizzaName);
        Pizza result;

        synchronized (this){
            if(pizzas.containsKey(key)){
                result = pizzas.get(key);
            } else {
                PizzaType pt = getPizzaType(typeName);
                result = pt.createInstance(pizzaName, ingredients);
                pizzas.put(key, result);
            }
        }

        return result;
    }

    private static String getKey(String typeName, String pizzaName){
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(typeName + ";");
        stringBuilder.append(pizzaName);

        String key = stringBuilder.toString();

        return key;
    }

    private PizzaType getPizzaType(String typeName){
        return new PizzaType(typeName);
    }

}
