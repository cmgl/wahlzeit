package org.wahlzeit.model;

import com.google.appengine.api.images.Image;
import org.wahlzeit.services.LogBuilder;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class PizzaPhotoManager extends PhotoManager {

    protected static final PizzaPhotoManager instance = new PizzaPhotoManager();
    private static final Logger log = Logger.getLogger(PizzaPhotoManager.class.getName());

    /**
     * In-memory cache for photos
     */
    protected Map<PhotoId, PizzaPhoto> photoCache = new HashMap<>();

    /**
     *
     */
    public PizzaPhotoManager() {
        photoTagCollector = PizzaPhotoFactory.getInstance().createPhotoTagCollector();
    }

    /**
     *
     */
    public static PizzaPhotoManager getInstance() {
        return instance;
    }

    /**
     *
     */
    @Override
    public final PizzaPhoto getPhoto(PhotoId id) {
        return instance.getPhotoFromId(id);
    }

    /**
     * @methodtype get
     */
    @Override
    public PizzaPhoto getPhoto(String id) {
        return getPhoto(PhotoId.getIdFromString(id));
    }

    /**
     *
     */
    @Override
    public PizzaPhoto getPhotoFromId(PhotoId id) {
        if (id == null) {
            return null;
        }

        PizzaPhoto result = doGetPhotoFromId(id);

        if (result == null) {
            result = PizzaPhotoFactory.getInstance().loadPhoto(id);
            if (result != null) {
                doAddPhoto(result);
            }
        }

        return result;
    }

    /**
     * @methodtype get
     * @methodproperties primitive
     */
    @Override
    protected PizzaPhoto doGetPhotoFromId(PhotoId id) {
        return photoCache.get(id);
    }

    /**
     * @methodtype command
     * @methodproperties primitive
     */
    @Override
    protected void doAddPhoto(Photo myPhoto) throws IllegalArgumentException {
        if (myPhoto instanceof PizzaPhoto) {
            doAddPhoto(myPhoto);
        } else {
            throw new IllegalArgumentException("Only PizzaPhoto accepted");
        }
    }

    /**
     * @methodtype command
     * @methodproperties primitive
     */
    protected void doAddPhoto(PizzaPhoto myPhoto) {
        photoCache.put(myPhoto.getId(), myPhoto);
    }

    /**
     *
     */
    @Override
    public PizzaPhoto createPhoto(String filename, Image uploadedImage) throws Exception {
        PhotoId id = PhotoId.getNextId();
        PizzaPhoto result = (PizzaPhoto)PhotoUtil.createPhoto(filename, id, uploadedImage);
        addPhoto(result);
        return result;
    }

    /**
     * @methodtype get
     */
    @Override
    public Map getPhotoCache() {
        return photoCache;
    }

    /**
     *
     */
    @Override
    public PizzaPhoto getVisiblePhoto(PhotoFilter filter) {
        filter.generateDisplayablePhotoIds();
        return getPhotoFromId(filter.getRandomDisplayablePhotoId());
    }

    /**
     *
     */
    @Override
    public Set findPhotosByOwner(String ownerName) {
        Set<PizzaPhoto> result = new HashSet<PizzaPhoto>();
        readObjects(result, PizzaPhoto.class, PizzaPhoto.OWNER_ID, ownerName);

        for (Iterator<PizzaPhoto> i = result.iterator(); i.hasNext(); ) {
            doAddPhoto(i.next());
        }

        return result;
    }

    /**
     * @methodtype command
     */
    @Override
    public void addPhoto(Photo photo) throws IOException, IllegalArgumentException {
        if (photo instanceof PizzaPhoto) {
            try {
                addPhoto(photo);
            } catch (IOException ex) {
                throw ex;
            }
        } else {
            throw new IllegalArgumentException("Only PizzaPhoto accepted");
        }
    }

    /**
     * @methodtype command
     */
    public void addPhoto(PizzaPhoto photo) throws IOException {
        PhotoId id = photo.getId();
        assertIsNewPhoto(id);
        doAddPhoto(photo);

        GlobalsManager.getInstance().saveGlobals();
    }


}
