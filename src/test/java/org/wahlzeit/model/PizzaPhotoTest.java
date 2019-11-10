package org.wahlzeit.model;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Work;
import org.junit.*;
import org.junit.rules.RuleChain;
import org.wahlzeit.model.persistence.DatastoreAdapter;
import org.wahlzeit.model.persistence.ImageStorage;
import org.wahlzeit.services.Language;
import org.wahlzeit.services.SessionManager;
import org.wahlzeit.testEnvironmentProvider.*;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class PizzaPhotoTest {

    @ClassRule
    public static RuleChain ruleChain = RuleChain.
            outerRule(new LocalDatastoreServiceTestConfigProvider()).
            around(new SysConfigProvider()).
            around(new UserSessionProvider());

    private static User user;

    @BeforeClass
    public static void setUp() {
        // create user
        ObjectifyService.run(new Work<Void>() {
            @Override
            public Void run() {
                user = new User("1001", "testuser2", "testuser@pm.me");
                return null;
            }
        });
    }

    @Test
    public void getCaption() {
        // arrange
        PizzaPhoto pizzaPhoto = new PizzaPhoto();
        String pizzaName = "Hawaii";
        String testuserNick = "testuserNick";
        String correctString = "Pizza name: Hawaii. Photo by <a href=\"/filter?userName=testuserNick\" rel=\"nofollow\">testuserNick</a>";

        // act
        pizzaPhoto.setPizzaName(pizzaName);
        pizzaPhoto.setOwnerId(user.getId());
        user.setNickName(testuserNick);
        user.setLanguage(Language.ENGLISH);
        String testString = pizzaPhoto.getCaption(user.getLanguageConfiguration());

        // assert
        assertEquals(correctString, testString);
    }

    @Test
    public void setPizzaName() {
    }

    @Test
    public void getMyPizzaName() {
    }

    @Test
    public void setMyPizzaDescription() {
    }

    @Test
    public void getMyPizzaDescription() {
    }

    @Test
    public void setMyPizzaIngredients() {
    }

    @Test
    public void getMyPizzaIngredients() {
    }
}