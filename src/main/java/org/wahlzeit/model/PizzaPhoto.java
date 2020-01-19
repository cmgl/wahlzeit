package org.wahlzeit.model;

import com.googlecode.objectify.annotation.Subclass;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Subclass
public class PizzaPhoto extends Photo{
    private String myPizzaName;
    private String[] myPizzaIngredients;

    private List pizza; // storage for *one* Pizza object, see setter

    public static final Tags PIZZA_TAG = new Tags("pizza");

    private static final Logger log = Logger.getLogger(PizzaPhoto.class.getName());

    public PizzaPhoto(){
        super();
        setTags( PIZZA_TAG );
    }

    public PizzaPhoto(PhotoId myId){
        super(myId);
        setTags( PIZZA_TAG );
    }

    public String getCaption(ModelConfig cfg) {
        String caption = "";

        if (null != getMyPizzaName() && !getMyPizzaName().isEmpty())
            caption += "Pizza name: " + getMyPizzaName() + ". ";

        caption += super.getCaption(cfg);
        return caption;
    }

    public void setPizzaName(String name){
        if(null == name || name.length() == 0){
            log.log(Level.WARNING, "pizza name not valid");
            throw new IllegalArgumentException("pizza name not valid");
        }

        if (null == pizza){
            myPizzaName = name;
        } else {
            Pizza pizzaObj = (Pizza) pizza.get(0);
            pizzaObj.setPizzaName(name);
        }
    }

    public String getMyPizzaName(){
        if (null == pizza){
            return myPizzaName;
        } else {
            Pizza pizzaObj = (Pizza) pizza.get(0);
            return pizzaObj.getPizzaName();
        }
    }

    public void setMyPizzaIngredients(String[] ingredients){
        if(null == ingredients || ingredients.length == 0){
            log.log(Level.WARNING, "pizza ingredients not valid");
            throw new IllegalArgumentException("pizza ingredients not valid");
        }
        if (null == pizza){
            myPizzaIngredients = ingredients;
        } else {
            Pizza pizzaObj = (Pizza) pizza.get(0);
            pizzaObj.setIngredients(ingredients);
        }
    }

    public String[] getMyPizzaIngredients(){
        if (null == pizza){
            return myPizzaIngredients;
        } else {
            Pizza pizzaObj = (Pizza) pizza.get(0);
            return pizzaObj.getIngredients();
        }
    }

    public void setPizza(Pizza pizzaObj){
        if (null == pizza){ // adding Pizza object for the first time
            pizza = new ArrayList();
            pizza.add(pizzaObj);
            myPizzaName = null;
            myPizzaIngredients = null;
        } else if (!pizza.contains(pizzaObj)){
            pizza.clear(); // remove current object
            pizza.add(pizzaObj); // store new object
        }
    }

    public Pizza getPizza(){
        if (null == pizza){
            return null;
        }
        return (Pizza) pizza.get(0);
    }

    public String getMyPizzaDescription(){
        String desc = "This pizza contains the following ingredients: ";

        if(null != getMyPizzaIngredients()){
            for(int i = 0; i<getMyPizzaIngredients().length; i++){
                desc += getMyPizzaIngredients()[i] + ", ";
            }
        }
        return desc;
    }
}
