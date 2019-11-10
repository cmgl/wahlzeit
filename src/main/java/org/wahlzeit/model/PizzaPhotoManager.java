package org.wahlzeit.model;

import com.google.appengine.api.images.Image;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Work;
import org.wahlzeit.model.persistence.ImageStorage;
import org.wahlzeit.services.LogBuilder;
import org.wahlzeit.services.Persistent;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * A photo manager provides access to and manages photos.
 */
public class PizzaPhotoManager extends PhotoManager {

    /**
     *
     */
    protected static final PizzaPhotoManager instance = new PizzaPhotoManager();

    private static final Logger log = Logger.getLogger(PizzaPhotoManager.class.getName());

    /**
     * In-memory cache for photos
     */
    protected Map<PhotoId, PizzaPhoto> photoCache = new HashMap<>();

    /**
     *
     */
    protected PhotoTagCollector photoTagCollector = null;

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
    public boolean hasPhoto(String id) {
        return hasPhoto(PhotoId.getIdFromString(id));
    }

    /**
     *
     */
    @Override
    public boolean hasPhoto(PhotoId id) {
        return getPhoto(id) != null;
    }

    /**
     *
     */
    @Override
    public PizzaPhoto getPhoto(PhotoId id) {
        return instance.getPhotoFromId(id);
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
     * @methodtype get
     */
    @Override
    public PizzaPhoto getPhoto(String id) {
        return getPhoto(PhotoId.getIdFromString(id));
    }

    /**
     * @methodtype init Loads all Photos from the Datastore and holds them in the cache
     */
    @Override
    public void init() {
        loadPhotos();
    }

    /**
     * @methodtype command
     *
     * Load all persisted photos. Executed when Wahlzeit is restarted.
     */
    @Override
    public void loadPhotos() {
        Collection<PizzaPhoto> existingPhotos = ObjectifyService.run(new Work<Collection<PizzaPhoto>>() {
            @Override
            public Collection<PizzaPhoto> run() {
                Collection<PizzaPhoto> existingPhotos = new ArrayList<PizzaPhoto>();
                readObjects(existingPhotos, PizzaPhoto.class);
                return existingPhotos;
            }
        });

        for (PizzaPhoto photo : existingPhotos) {
            if (!doHasPhoto(photo.getId())) {
                log.config(LogBuilder.createSystemMessage().
                        addParameter("Load PizzaPhoto with ID", photo.getIdAsString()).toString());
                loadScaledImages(photo);
                doAddPhoto(photo);
            } else {
                log.config(LogBuilder.createSystemMessage().
                        addParameter("Already loaded PizzaPhoto", photo.getIdAsString()).toString());
            }
        }

        log.info(LogBuilder.createSystemMessage().addMessage("All photos loaded.").toString());
    }

    /**
     * @methodtype boolean-query
     * @methodproperty primitive
     */
    @Override
    protected boolean doHasPhoto(PhotoId id) {
        return photoCache.containsKey(id);
    }

    /**
     * @methodtype command
     *
     * Loads all scaled Images of this Photo from Google Cloud Storage
     */
    @Override
    protected void loadScaledImages(Photo photo) throws IllegalArgumentException {
        if (photo instanceof PizzaPhoto) {
            loadScaledImages(photo);
        } else {
            throw new IllegalArgumentException("Only PizzaPhoto accepted");
        }
    }

    /**
     * @methodtype command
     *
     * Loads all scaled Images of this Photo from Google Cloud Storage
     */
    protected void loadScaledImages(PizzaPhoto photo) {
        String photoIdAsString = photo.getId().asString();
        ImageStorage imageStorage = ImageStorage.getInstance();

        for (PhotoSize photoSize : PhotoSize.values()) {
            log.config(LogBuilder.createSystemMessage().
                    addAction("loading image").
                    addParameter("image size", photoSize.asString()).
                    addParameter("photo ID", photoIdAsString).toString());
            if (imageStorage.doesImageExist(photoIdAsString, photoSize.asInt())) {
                try {
                    Serializable rawImage = imageStorage.readImage(photoIdAsString, photoSize.asInt());
                    if (rawImage != null && rawImage instanceof Image) {
                        photo.setImage(photoSize, (Image) rawImage);
                    }
                } catch (IOException e) {
                    log.warning(LogBuilder.createSystemMessage().
                            addParameter("size", photoSize.asString()).
                            addParameter("photo ID", photoIdAsString).
                            addException("Could not load image although it exists", e).toString());
                }
            } else {
                log.config(LogBuilder.createSystemMessage().
                        addParameter("Size does not exist", photoSize.asString()).toString());
            }
        }
    }

    /**
     *
     */
    @Override
    public void savePhoto(Photo photo) throws IllegalArgumentException {
        if (photo instanceof PizzaPhoto) {
            savePhoto(photo);
        } else {
            throw new IllegalArgumentException("Only PizzaPhoto accepted");
        }
    }

    /**
     *
     */
    public void savePhoto(PizzaPhoto photo) {
        updateObject(photo);
    }

    @Override
    protected void updateDependents(Persistent obj) {
        if (obj instanceof PizzaPhoto) {
            PizzaPhoto photo = (PizzaPhoto) obj;
            saveScaledImages(photo);
            updateTags(photo);
            UserManager userManager = UserManager.getInstance();
            Client owner = userManager.getClientById(photo.getOwnerId());
            userManager.saveClient(owner);
        }
    }

    /**
     * @methodtype helper
     */
    @Override
    public List<Tag> addTagsThatMatchCondition(List<Tag> tags, String condition) {
        readObjects(tags, Tag.class, Tag.TEXT, condition);
        return tags;
    }

    /**
     * @methodtype command
     *
     * Persists all available sizes of the Photo. If one size exceeds the limit of the persistence layer, e.g. > 1MB for
     * the Datastore, it is simply not persisted.
     */
    @Override
    public void saveScaledImages(Photo photo) throws IllegalArgumentException {
        if (photo instanceof PizzaPhoto) {
            saveScaledImages(photo);
        } else {
            throw new IllegalArgumentException("Only PizzaPhoto accepted");
        }
    }

    /**
     * @methodtype command
     *
     * Persists all available sizes of the Photo. If one size exceeds the limit of the persistence layer, e.g. > 1MB for
     * the Datastore, it is simply not persisted.
     */
    protected void saveScaledImages(PizzaPhoto photo) {
        String photoIdAsString = photo.getId().asString();
        ImageStorage imageStorage = ImageStorage.getInstance();
        PhotoSize photoSize;
        int it = 0;
        boolean moreSizesExist = true;
        do{
            photoSize = PhotoSize.values()[it];
            it++;
            Image image = photo.getImage(photoSize);
            if (image != null) {
                try {
                    if (!imageStorage.doesImageExist(photoIdAsString, photoSize.asInt())) {
                        imageStorage.writeImage(image, photoIdAsString, photoSize.asInt());
                    }
                } catch (Exception e) {
                    log.warning(LogBuilder.createSystemMessage().
                            addException("Problem when storing image", e).toString());
                    moreSizesExist = false;
                }
            } else {
                log.config(LogBuilder.createSystemMessage().
                        addParameter("No image for size", photoSize.asString()).toString());
                moreSizesExist = false;
            }
        } while (it < PhotoSize.values().length && moreSizesExist);
    }

    /**
     * Removes all tags of the Photo (obj) in the datastore that have been removed by the user and adds all new tags of
     * the photo to the datastore.
     */
    @Override
    public void updateTags(Photo photo) throws IllegalArgumentException {
        if (photo instanceof PizzaPhoto) {
            updateTags(photo);
        } else {
            throw new IllegalArgumentException("Only PizzaPhoto accepted");
        }
    }

    /**
     * Removes all tags of the Photo (obj) in the datastore that have been removed by the user and adds all new tags of
     * the photo to the datastore.
     */
    protected void updateTags(PizzaPhoto photo) {
        // delete all existing tags, for the case that some have been removed
        deleteObjects(Tag.class, Tag.PHOTO_ID, photo.getId().asString());

        // add all current tags to the datastore
        Set<String> tags = new HashSet<String>();
        photoTagCollector.collect(tags, photo);
        for (Iterator<String> i = tags.iterator(); i.hasNext(); ) {
            Tag tag = new Tag(i.next(), photo.getId().asString());
            log.config(LogBuilder.createSystemMessage().addParameter("Writing Tag", tag.asString()).toString());
            writeObject(tag);
        }
    }

    /**
     *
     */
    @Override
    public void savePhotos() throws IOException{
        updateObjects(photoCache.values());
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
    public Set findPhotosByOwner(String ownerName) {
        Set<PizzaPhoto> result = new HashSet<PizzaPhoto>();
        readObjects(result, PizzaPhoto.class, PizzaPhoto.OWNER_ID, ownerName);
        System.out.println(result);

        for (Iterator<PizzaPhoto> i = result.iterator(); i.hasNext(); ) {
            doAddPhoto(i.next());
        }

        return result;
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
    public PizzaPhoto createPhoto(String filename, Image uploadedImage) throws Exception {
        PhotoId id = PhotoId.getNextId();
        PizzaPhoto result = (PizzaPhoto) PhotoUtil.createPhoto(filename, id, uploadedImage);
        addPhoto(result);
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

    /**
     * @methodtype assertion
     */
    @Override
    protected void assertIsNewPhoto(PhotoId id) {
        if (hasPhoto(id)) {
            throw new IllegalStateException("Photo already exists!");
        }
    }

}
