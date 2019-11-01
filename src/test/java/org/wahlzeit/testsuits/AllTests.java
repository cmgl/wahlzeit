package org.wahlzeit.testsuits;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.wahlzeit.handlers.TellFriendTest;
import org.wahlzeit.services.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        // handlers
        TellFriendTest.class,
        // model
        ModelTestSuite.class,
        // services
        EmailServiceTestSuite.class,
        LogBuilderTest.class,
        // utils
        UtilsTestSuite.class
})

public class AllTests {
}
