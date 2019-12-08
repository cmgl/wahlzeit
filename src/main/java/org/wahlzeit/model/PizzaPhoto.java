package org.wahlzeit.model;

import com.googlecode.objectify.annotation.Subclass;

import java.util.logging.Level;
import java.util.logging.Logger;

@Subclass
public class PizzaPhoto extends Photo{
    private String myPizzaName;
    private String myPizzaDescription;
    private String myPizzaIngredients;

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

        if (null != myPizzaName && !myPizzaName.isEmpty())
            caption += "Pizza name: " + myPizzaName + ". ";

        caption += super.getCaption(cfg);
        return caption;
    }

    public void setPizzaName(String name){
        if(null == name || name.length() == 0){
            log.log(Level.WARNING, "pizza name not valid");
            throw new IllegalArgumentException("pizza name not valid");
        }
        myPizzaName = name;
    }

    public String getMyPizzaName(){
        return myPizzaName;
    }

    public void setMyPizzaDescription(String description){
        if(null == description || description.length() == 0){
            log.log(Level.WARNING, "pizza description not valid");
            throw new IllegalArgumentException("pizza description not valid");
        }
        myPizzaDescription = description;
    }

    public String getMyPizzaDescription(){
        return myPizzaDescription;
    }

    public void setMyPizzaIngredients(String ingredients){
        if(null == ingredients || ingredients.length() == 0){
            log.log(Level.WARNING, "pizza ingredients not valid");
            throw new IllegalArgumentException("pizza ingredients not valid");
        }
        myPizzaIngredients = ingredients;
    }

    public String getMyPizzaIngredients(){
        return myPizzaIngredients;
    }
}
