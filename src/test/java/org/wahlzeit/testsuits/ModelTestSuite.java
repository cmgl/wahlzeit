package org.wahlzeit.testsuits;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.wahlzeit.model.*;
import org.wahlzeit.model.persistence.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        // persistence tests
        DatastoreAdapterTest.class,

        AbstractCoordinateTest.class,
        AccessRightsTest.class,
        CartesianCoordinateTest.class,
        FlagReasonTest.class,
        GenderTest.class,
        GuestTest.class,
        PhotoFilterTest.class,
        PizzaManagerTest.class,
        PizzaPhotoFactoryTest.class,
        PizzaPhotoManagerTest.class,
        PizzaPhotoTest.class,
        PizzaTest.class,
        PizzaTypeTest.class,
        SphericCoordinateTest.class,
        TagsTest.class,
        UserStatusTest.class,
        ValueTest.class
})

public class ModelTestSuite {
}
