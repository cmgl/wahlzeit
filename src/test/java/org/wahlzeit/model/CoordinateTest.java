/*
 * Class CoordinateTest
 *
 * Version 1.0
 *
 * Date 2019-10-25
 *
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

package org.wahlzeit.model;

import junit.framework.TestCase;

public class CoordinateTest extends TestCase {

    /**
     *
     */
    public void testCoordinate(){
        Coordinate co0 = new Coordinate(1,1,1);
        Coordinate co1 = new Coordinate(1,2,3);
        assertTrue(co0.getDistance(co1) == 2.23606797749979);
        assertFalse(co0.equals(co1));
        assertTrue(co0.equals(co0));

        Coordinate co2 = new Coordinate(1,2,3);
        assertTrue(co1.hashCode() == co2.hashCode());
    }
}
