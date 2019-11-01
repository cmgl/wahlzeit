/*
 * Copyright (c) 2006-2009 by Dirk Riehle, http://dirkriehle.com
 *
 * This file is part of the Wahlzeit photo rating application.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package org.wahlzeit.services.mailing;

import org.junit.*;
import org.wahlzeit.services.EmailAddress;

import static org.junit.Assert.*;

public class EmailServiceTest {

	private EmailService emailService = null;

	private EmailAddress validAddressFrom = null;
	private EmailAddress validAddressTo = null;
	private EmailAddress validAddressBcc = null;

	@Before
	public void setup() throws Exception {
		emailService = EmailServiceManager.getDefaultService();
		validAddressFrom = EmailAddress.getFromString("sender@test.de");
		validAddressTo = EmailAddress.getFromString("recipient@test.de");
		validAddressBcc = EmailAddress.getFromString("bcc@test.de");
	}

	@After
	public void tearDown() {
		EmailAddress.reset();
	}

	@Test
	public void testSendInvalidEmailIgnoreExceptions() {
		try {
			assertFalse(emailService.sendEmailIgnoreException(validAddressFrom, null, "lol", "hi"));
			assertFalse(emailService.sendEmailIgnoreException(null, validAddressTo, null, "body"));
			assertFalse(emailService.sendEmailIgnoreException(validAddressFrom, null, "hi", "       "));
			assertFalse(emailService.sendEmailIgnoreException(validAddressFrom, null, validAddressBcc,"hi", "       "));
			assertFalse(emailService.sendEmailIgnoreException(validAddressFrom, validAddressTo, null, "test"));
			assertFalse(emailService.sendEmailIgnoreException(validAddressFrom, validAddressTo, "hi", null));
			assertFalse(emailService.sendEmailIgnoreException(validAddressFrom, validAddressTo, null, null));
			assertFalse(emailService.sendEmailIgnoreException(validAddressFrom, validAddressFrom, "", ""));
			assertFalse(emailService.sendEmailIgnoreException(validAddressFrom, validAddressFrom, "hi", ""));
			assertFalse(emailService.sendEmailIgnoreException(validAddressFrom, validAddressFrom, "", "hi"));
		} catch (Exception ex) {
			Assert.fail("Silent mode does not allow exceptions");
		}
	}

	@Test
	public void testSendInvalidEmailThrowsException() {
		try {
			assertThrows(MailingException.class, () -> emailService.sendEmail(validAddressFrom, null, "lol", "hi"));
			assertThrows(MailingException.class, () -> emailService.sendEmail(null, validAddressFrom, null, "body"));
			assertThrows(MailingException.class, () -> emailService.sendEmail(validAddressFrom, null, "hi", "       "));
			assertThrows(MailingException.class, () -> emailService.sendEmail(validAddressFrom, null, validAddressBcc,"hi", "  " ));
		} catch (Exception ex) {
			Assert.fail("Silent mode does not allow exceptions");
		}
	}

	@Test
	public void testSendValidEmail() {
		try {
			assertTrue(emailService.sendEmailIgnoreException(validAddressFrom, validAddressFrom, "hi", "test"));
			assertTrue(emailService.sendEmailIgnoreException(validAddressFrom, validAddressTo, null,"hi", "       "));
			assertTrue(emailService.sendEmailIgnoreException(validAddressFrom, validAddressTo, validAddressBcc,"hi", "test"));
		} catch (Exception ex) {
			Assert.fail("Silent mode does not allow exceptions");
		}
	}
}