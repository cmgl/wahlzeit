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

package org.wahlzeit.services;

import junit.framework.TestCase;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import static org.junit.Assert.assertThrows;

/**
 * Test cases for the EmailAddress class.
 */
public class EmailAddressTest extends TestCase {

	/**
	 *
	 */
	public EmailAddressTest(String name) {
		super(name);
	}

	/**
	 *
	 */
	public void testGetEmailAddressFromValidString() {

		assertTrue(createEmailAddressIgnoreException("bingo@bongo.com"));
		assertTrue(createEmailAddressIgnoreException("bingo.bongo@bongo.com"));
		assertTrue(createEmailAddressIgnoreException("bingo_bongo@bango.com"));
	}

	/**
	 *
	 */
	public void testGetEmailAddressFromInvalidString() {

		assertFalse(createEmailAddressIgnoreException(" "));
		assertFalse(createEmailAddressIgnoreException("user@examplecom"));
		assertFalse(createEmailAddressIgnoreException("userexample.com"));
		assertFalse(createEmailAddressIgnoreException("user@example.com."));
		assertFalse(createEmailAddressIgnoreException("user@example@example.com"));
		assertFalse(createEmailAddressIgnoreException("ich gehe zur Uni"));

	}

	/**
	 *
	 */
	protected boolean createEmailAddressIgnoreException(String ea) {
		try {
			return EmailAddress.getFromString(ea) != null;
		} catch (IllegalArgumentException ex) {
			// creation failed
			return false;
		}
	}

	public void testNullStringThrowsException() {
		assertThrows(NullPointerException.class, () -> EmailAddress.getFromString(null));
	}

	/**
	 *
	 */
	public void testEmptyEmailAddress() {
		// arrange
		EmailAddress emptyPredefinedAddr = EmailAddress.EMPTY;

		// act + assert
		assertTrue(emptyPredefinedAddr.isEmpty());
	}

	/**
	 *
	 */
	public void testEmailAddressIsValid() {
		// arrange
		String validEmailAddressString = "userA@example.com";

		// act
		EmailAddress validEmailAddress = EmailAddress.getFromString(validEmailAddressString);

		// assert
		assertTrue(validEmailAddress.isValid());
	}

	/**
	 *
	 */
	public void testEmailAddressIsNotValid() {
		// arrange + act
		EmailAddress validEmailAddress = EmailAddress.EMPTY;

		// assert
		assertFalse(validEmailAddress.isValid());
	}

	/**
	 *
	 */
	public void testEmailAddressEqualsItself() {
		// arrange
		String emailAddressString = "userB@example.com";
		EmailAddress validEmailAddress = EmailAddress.getFromString(emailAddressString);

		// act + assert
		assertTrue(validEmailAddress.isEqual(validEmailAddress));
	}

	/**
	 *
	 */
	public void testIdenticalEmailAddressIsEqual() {
		// arrange
		String emailAddressString1 = "userC@example.com";
		EmailAddress emailAddress1 = EmailAddress.getFromString(emailAddressString1);
		String emailAddressString2 = "userC@example.com";
		EmailAddress emailAddress2 = EmailAddress.getFromString(emailAddressString2);

		// act + assert
		assertTrue(emailAddress1.isEqual(emailAddress2));
		assertTrue(emailAddress2.isEqual(emailAddress1));
	}

	/**
	 *
	 */
	public void testNotIdenticalEmailAddressesAreNotEqual() {
		// arrange
		String emailAddressString1 = "userD@example.com";
		EmailAddress validEmailAddress1 = EmailAddress.getFromString(emailAddressString1);
		String emailAddressString2 = "userE@example.com";
		EmailAddress validEmailAddress2 = EmailAddress.getFromString(emailAddressString2);

		// act + assert
		assertFalse(validEmailAddress1.isEqual(validEmailAddress2));
		assertFalse(validEmailAddress2.isEqual(validEmailAddress1));
	}

	/**
	 *
	 */
	public void testEmailAddressAsString() {
		// arrange
		String emailAddressString = "userF@example.com";
		EmailAddress validEmailAddress = EmailAddress.getFromString(emailAddressString);

		// act + assert
		assertTrue(validEmailAddress.asString().equals(emailAddressString));
	}

	/**
	 *
	 */
	public void testEmailAddressAsEnternetAddress() {
		// arrange
		String emailAddressString = "userG@example.com";
		EmailAddress validEmailAddress = EmailAddress.getFromString(emailAddressString);

		// act + assert
		assertTrue(validEmailAddress.asInternetAddress().equals( createInternetAddress(emailAddressString)));
	}

	protected InternetAddress createInternetAddress(String address){
		InternetAddress result = null;

		try {
			result = new InternetAddress(address);
		} catch (AddressException ex) {
			System.out.println(ex);
		}

		return result;
	}

	/**
	 *
	 */
	public void testEmailAddressReset() {
		// arrange
		String emailAddressString = "userH@example.com";
		EmailAddress.getFromString(emailAddressString);

		// act
		EmailAddress.reset();

		// assert
		assertTrue(EmailAddress.getFromString(emailAddressString).isValid());
	}
}