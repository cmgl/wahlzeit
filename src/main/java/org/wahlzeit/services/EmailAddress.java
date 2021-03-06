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

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * An email address provides a simple email address representation.
 * It is a value object and implemented as immutable.
 */
public class EmailAddress implements Serializable {

	/**
	 *
	 */
	protected static final Map<String, EmailAddress> instances = new HashMap<String, EmailAddress>();

	/**
	 *
	 */
	public static final EmailAddress EMPTY = doGetFromString(""); // after map initialization...
	/**
	 *
	 */
	protected String value;

	private EmailAddress() {
		// for Objectify to load
	}

	/**
	 *
	 */
	protected EmailAddress(String myAddress) {
		value = myAddress;
	}

	/**
	 *
	 */
	public static EmailAddress getFromString(String myValue) throws NullPointerException, IllegalArgumentException {
		if (myValue == null)
			throw new NullPointerException("e-mail address cannot be null!");

		try {
			return doGetFromString(myValue);
		} catch (IllegalArgumentException ex){
			throw ex;
		}
	}

	/**
	 *
	 */
	protected static EmailAddress doGetFromString(String myValue) throws IllegalArgumentException {
		// validate new email
		EmailAddress mailToValidate = new EmailAddress(myValue);
		if ( !myValue.isEmpty() && !mailToValidate.isValid() )
			throw new IllegalArgumentException("email address is not valid");

		// find in the list of email addresses
		EmailAddress result = instances.get(myValue);
		if (result == null) {
			synchronized (instances) {
				result = instances.get(myValue);
				if (result == null) {
					result = mailToValidate;
					instances.put(myValue, result);
				}
				else {
					throw new IllegalArgumentException("email address already exists");
				}
			}
		}
		// TODO: conflict with the TellFriendTest#testTellFriendPost() test method
		/*else {
			throw new IllegalArgumentException("email address already exists");
		}*/

		return result;
	}

	/**
	 *
	 */
	public String asString() {
		return value;
	}

	/**
	 *
	 */
	public InternetAddress asInternetAddress() {
		InternetAddress result = null;

		try {
			result = new InternetAddress(value);
		} catch (AddressException ex) {
			System.out.println(ex);
		}

		return result;
	}

	/**
	 * @methodtype boolean-query
	 */
	public boolean isEqual(EmailAddress emailAddress) {
		if(emailAddress == null)
			return false;

		return this.asString().equals( emailAddress.asString());
	}

	/**
	 *
	 */

	public boolean isValid() {
		if(this.isEmpty())
			return false;

		// inspired from: https://www.tutorialspoint.com/validate-email-address-in-java
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@(([\\w]+\\.)+[\\w]+[\\w]|localhost)$";
		return this.asString().matches(regex);
	}

	/**
	 *
	 */
	public boolean isEmpty() {
		return this.isEqual( EMPTY );
	}

	/**
	 *
	 */
	public static void reset() {
		instances.clear();
	}

}
