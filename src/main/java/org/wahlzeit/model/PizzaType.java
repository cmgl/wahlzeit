package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PizzaType extends DataObject {

    private static final Logger log = Logger.getLogger(PizzaType.class.getName());

    private String typeName;

    protected PizzaType superType = null;
    protected Set<PizzaType> subTypes = new HashSet<PizzaType>();

    public PizzaType(String typeName){
        if(null == typeName || typeName.equals("")){
            log.log(Level.WARNING, "typeName value argument not valid");
            throw new IllegalArgumentException("typeName value argument not valid");
        }
        this.typeName = typeName;
    }

    public Pizza createInstance(String pizzaName, String[] ingredients){
        return new Pizza(this, pizzaName, ingredients);
    }

    public boolean hasInstance(Pizza pizza) {
        if(null == pizza){
            log.log(Level.WARNING, "pizza object argument cannot be null");
            throw new IllegalArgumentException("pizza object argument cannot be null");
        }

        if (pizza.getPizzaType() == this) {
            return true;
        }
        for (PizzaType type : subTypes) {
            if (type.hasInstance(pizza)) {
                return true;
            }
        }

        return false;
    }

    public String getTypeName() {
        return typeName;
    }

    public PizzaType getSuperType() {
        return superType;
    }

    public void setSuperType(PizzaType pt){
        Iterator iterator = pt.getSubTypeIterator();
        boolean flag = false;

        while(iterator.hasNext()){
            if(iterator.next() == this)
                flag = true;
        }

        if(flag == false){
            pt.addSubType(this);
        }

        superType = pt;
    }

    public Iterator<PizzaType> getSubTypeIterator() {
        return subTypes.iterator();
    }

    public void addSubType(PizzaType pt) {
        if(null == pt){
            log.log(Level.WARNING, "tried to set null sub-type");
            throw new IllegalArgumentException("tried to set null sub-type");
        }
        subTypes.add(pt);
        pt.setSuperType(this);
    }

    public boolean isSubtype(){
        return null != superType;
    }
}