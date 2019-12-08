package org.wahlzeit.model;

import org.wahlzeit.services.LogBuilder;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PizzaPhotoFactory extends PhotoFactory {

    private static final Logger log = Logger.getLogger(PizzaPhotoFactory.class.getName());

    /**
     * Hidden singleton instance; needs to be initialized from the outside.
     */
    private static PizzaPhotoFactory instance = null;

    /**
     * Public singleton access method.
     */
    public static synchronized PizzaPhotoFactory getInstance() {
        if (instance == null) {
            log.config(LogBuilder.createSystemMessage().addAction("setting PizzaPhotoFactory").toString());
            setInstance(new PizzaPhotoFactory());
        }

        return instance;
    }

    /**
     * Method to set the singleton instance of PhotoFactory.
     */
    protected static synchronized void setInstance(PizzaPhotoFactory pizzaPhotoFactory) {
        if(null == pizzaPhotoFactory){
            log.log(Level.SEVERE, "attempt to initialize PizzaPhotoFactory as null object");
            throw new IllegalArgumentException("attempt to initialize PizzaPhotoFactory as null object");
        }

        if (instance != null) {
            throw new IllegalStateException("attempt to initalize PizzaPhotoFactory twice");
        }

        instance = pizzaPhotoFactory;
    }

    /**
     * @methodtype factory
     */
    @Override
    public PizzaPhoto createPhoto() {
        return new PizzaPhoto();
    }

    /**
     * Creates a new photo with the specified id
     */
    @Override
    public PizzaPhoto createPhoto(PhotoId id) {
        if(null == id || id.isNullId()){
            log.log(Level.WARNING, "PhotoId not valid");
            throw new IllegalArgumentException("PhotoId not valid");
        }
        return new PizzaPhoto(id);
    }

    /**
     * Loads a photo. The Java object is loaded from the Google Datastore, the Images in all sizes are loaded from the
     * Google Cloud storage.
     */
    @Override
    public PizzaPhoto loadPhoto(PhotoId id) {
        if(null == id || id.isNullId()){
            log.log(Level.WARNING, "PhotoId not valid");
            throw new IllegalArgumentException("PhotoId not valid");
        }
        Photo photo = super.loadPhoto(id);
        if (null == photo)
            return null;

        return (PizzaPhoto) photo;
    }

    @Override
    public PhotoFilter createPhotoFilter() {
        PhotoFilter filter = new PhotoFilter();
        filter.setTags(PizzaPhoto.PIZZA_TAG);
        return filter;
    }
}
