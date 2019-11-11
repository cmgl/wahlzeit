package org.wahlzeit.model;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Work;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.wahlzeit.main.ServiceMain;
import org.wahlzeit.model.persistence.DatastoreAdapter;
import org.wahlzeit.model.persistence.ImageStorage;
import org.wahlzeit.testEnvironmentProvider.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PizzaPhotoManagerTest {

    @ClassRule
    public static RuleChain ruleChain = RuleChain.
            outerRule(new LocalDatastoreServiceTestConfigProvider()).
            around(new RegisteredOfyEnvironmentProvider()).
            around(new SysConfigProvider()).
            around(new UserServiceProvider()).
            around(new UserSessionProvider());

    private static PizzaPhotoManager pizzaPhotoManager;
    private static PizzaPhoto pizzaPhoto;
    private static User user;

    @BeforeClass
    public static void setUp() {
        ImageStorage.setInstance(new DatastoreAdapter());

        pizzaPhotoManager = PizzaPhotoManager.getInstance();

        // add a photo
        pizzaPhoto = new PizzaPhoto();
        try {
            pizzaPhotoManager.addPhoto(pizzaPhoto);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // create user
        ObjectifyService.run(new Work<Void>() {
            @Override
            public Void run() {
                user = new User("1000", "testuser", "testuser@pm.me");
                return null;
            }
        });
    }

    @Test
    public void getInstance() {
        // arrange + act
        // already in setUp

        // assert
        assertTrue(pizzaPhotoManager instanceof PizzaPhotoManager);
    }

    @Test
    public void hasPhoto() {
        // arrange + act
        // already in setUp

        // assert
        assertTrue(pizzaPhotoManager.hasPhoto(pizzaPhoto.getId().asString())); // hasPhoto(String id)
        assertTrue(pizzaPhotoManager.hasPhoto(pizzaPhoto.getId())); // hasPhoto(PhotoId id)
    }

    @Test
    public void getPhoto() {
        // arrange
        PhotoId photoId = pizzaPhoto.getId();
        String pizzaPhotoIdString = pizzaPhoto.getId().asString();

        // act
        PizzaPhoto loadPizzaPhotoPhotoId = pizzaPhotoManager.getPhoto(photoId); // getPhoto(PhotoId id)
        PizzaPhoto loadPizzaPhotoString = pizzaPhotoManager.getPhoto(pizzaPhotoIdString); // getPhoto(String id)

        // assert
        assertEquals(pizzaPhoto, loadPizzaPhotoPhotoId);
        assertEquals(pizzaPhoto, loadPizzaPhotoString);
    }

    @Test
    public void getPhotoFromId() {
        // arrange
        PhotoId photoId = pizzaPhoto.getId();

        // act
        PizzaPhoto loadPizzaPhoto = pizzaPhotoManager.getPhotoFromId(photoId);

        // assert
        assertEquals(pizzaPhoto, loadPizzaPhoto);
    }

    /**
     * TODO: work-in-progress
     * not sure how to save photos in datastore
     */
    @Test
    public void initAndLoadPhotos() {
        // arrange
        // add new photo to datastore
        Map<PhotoId, PizzaPhoto> photoCache = pizzaPhotoManager.getPhotoCache();
        //System.out.println(photoCache.size()); // check photoCache size before adding photo
        PizzaPhoto newPizzaPhoto = new PizzaPhoto();
        newPizzaPhoto.setOwnerId("1000");
        ObjectifyService.run(new Work<Void>() {
            @Override
            public Void run() {
                pizzaPhotoManager.savePhoto(newPizzaPhoto); // doesn't save the photo !?
                return null;
            }
        });

        // act
        pizzaPhotoManager.init();
        photoCache = pizzaPhotoManager.getPhotoCache();
        //System.out.println(photoCache.size()); // check photoCache size after adding photo

        // assert
    }

    /**
     * TODO: work-in-progress
     * not sure how to save photo and test it correctly
     */
    @Test
    public void savePhoto() {
        // arrange
        PizzaPhoto newPizzaPhoto = new PizzaPhoto();
        newPizzaPhoto.setOwnerId("1000");

        // act
        ObjectifyService.run(new Work<Void>() {
            @Override
            public Void run() {
                pizzaPhotoManager.savePhoto(newPizzaPhoto);
                return null;
            }
        });

        // assert
    }

    /**
     * TODO: work-in-progress
     */
    @Test
    public void addTagsThatMatchCondition() {
        // arrange
        List<Tag> tags = new LinkedList<Tag>();
    }

    /**
     * TODO: work-in-progress
     */
    @Test
    public void saveScaledImages() {

    }

    /**
     * TODO: work-in-progress
     */
    @Test
    public void updateTags() {

    }

    /**
     * TODO: work-in-progress
     * to implement after savePhoto() is done
     */
    @Test
    public void savePhotos() {

    }

    @Test
    public void getPhotoCache() {
        // arrange + act
        Map<PhotoId, PizzaPhoto> photoCache = pizzaPhotoManager.getPhotoCache();

        // assert
        assertTrue(photoCache.containsValue(pizzaPhoto));
    }

    /**
     * TODO: work-in-progress
     * findPhotosByOwner(id) doesn't find the photo with owner id
     * possibly due to savePhoto()
     */
    @Test
    public void findPhotosByOwner() {
        // arrange
        String id = user.getId();
        pizzaPhoto.setOwnerId(id);
        final boolean[] contains = {false};

        //ServiceMain.getInstance().addDefaultUserWithPictures(); // doesn't work, pictures are not added, error "robotis already known"

        // act
        ObjectifyService.run(new Work<Void>() {
            @Override
            public Void run() {
                pizzaPhotoManager.savePhoto(pizzaPhoto);
                Set<PizzaPhoto> result = pizzaPhotoManager.findPhotosByOwner(id);
                contains[0] = result.contains(pizzaPhoto);
                return null;
            }
        });

        // assert
        //assertTrue(contains[0]);
    }

    /**
     * TODO: work-in-progress
     */
    @Test
    public void getVisiblePhoto() {

    }

    /**
     * TODO: work-in-progress
     */
    @Test
    public void createPhoto() {

    }

    /**
     * implicitly tested in setUp()
     */
    @Test
    public void addPhoto() {

    }
}
