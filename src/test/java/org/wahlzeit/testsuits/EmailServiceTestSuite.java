package org.wahlzeit.testsuits;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.wahlzeit.services.EmailAddressTest;
import org.wahlzeit.services.mailing.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        EmailServiceTest.class,
        EmailAddressTest.class
})

public class EmailServiceTestSuite {
}
