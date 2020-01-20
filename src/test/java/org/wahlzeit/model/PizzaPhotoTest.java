package org.wahlzeit.model;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Work;
import org.junit.*;
import org.junit.rules.RuleChain;
import org.wahlzeit.services.Language;
import org.wahlzeit.testEnvironmentProvider.*;

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
    public void pizzaSetterGetter() {
        // arrange
        PizzaType pizzaTypeFamily = new PizzaType("Family");
        String[] ingredientsHawaii = {"Truthahnschinken", "Ananas"};
        Pizza pizzaHawaii = pizzaTypeFamily.createInstance("Hawaii", ingredientsHawaii);
        PizzaPhoto pizzaPhoto = new PizzaPhoto();

        // act
        pizzaPhoto.setPizza(pizzaHawaii);
        Pizza result = pizzaPhoto.getPizza();

        // assert
        assertEquals(pizzaHawaii, result);
    }
}