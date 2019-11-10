package org.wahlzeit.model;

public class PizzaPhoto extends Photo{
    private String myPizzaName;
    private String myPizzaDescription;
    private String myPizzaIngredients;

    public static final Tags PIZZA_TAG = new Tags("pizza");

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
            caption += "Pizza name: " + myPizzaName;

        caption += super.getCaption(cfg);
        return caption;
    }

    public void setPizzaName(String name){
        myPizzaName = name;
    }

    public String getMyPizzaName(){
        return myPizzaName;
    }

    public void setMyPizzaDescription(String description){
        myPizzaDescription = description;
    }

    public String getMyPizzaDescription(){
        return myPizzaDescription;
    }

    public void setMyPizzaIngredients(String ingredients){
        myPizzaIngredients = ingredients;
    }

    public String getMyPizzaIngredients(){
        return myPizzaIngredients;
    }
}
