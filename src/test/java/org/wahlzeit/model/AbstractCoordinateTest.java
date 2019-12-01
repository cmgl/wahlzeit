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

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class AbstractCoordinateTest {

    private static CartesianCoordinate cartesianCo1;
    private static CartesianCoordinate cartesianCo2;
    private static SphericCoordinate sphericCo1;
    private static SphericCoordinate sphericCo2;

    @BeforeClass
    public static void setup() throws Exception {
        cartesianCo1 = new CartesianCoordinate(1, 2, 3);
        sphericCo1 = new SphericCoordinate(1.1071487177940904, 0.6405223126794246, 3.7416573867739413);

        sphericCo2 = new SphericCoordinate(0.90, 0.82, 8.77);
        cartesianCo2 = new CartesianCoordinate(3.9858556908757925, 5.022808802826915, 5.983079987912371);
    }

    @Test
    public void getCartesianDistance() {
        // arrange
        // in setUp()

        // act + assert
        assertEquals(5.191480856120584, cartesianCo1.getCartesianDistance(cartesianCo2), 0.00001);
        assertEquals(5.191480856120584, sphericCo1.getCartesianDistance(sphericCo2), 0.00001);
        assertEquals(5.191480856120584, cartesianCo1.getCartesianDistance(sphericCo2), 0.00001);
        // vice versa
        assertEquals(5.191480856120584, cartesianCo2.getCartesianDistance(cartesianCo1), 0.00001);
        assertEquals(5.191480856120584, sphericCo2.getCartesianDistance(sphericCo1), 0.00001);
        assertEquals(5.191480856120584, cartesianCo2.getCartesianDistance(sphericCo1), 0.00001);
        // 0 distance between same objects
        assertEquals(0, cartesianCo1.getCartesianDistance(cartesianCo1), 0.00001);
        assertEquals(0, sphericCo1.getCartesianDistance(sphericCo1), 0.00001);
        assertEquals(0, cartesianCo1.getCartesianDistance(sphericCo1), 0.00001);
        // design by contract
        assertThrows(AssertionError.class,
                ()->{
                    cartesianCo1.getCartesianDistance(null);
                });
    }

    @Test
    public void getCentralAngle() {
        // arrange
        double correctVal = 0.22784270886757363;

        // act + assert
        assertEquals(correctVal, sphericCo1.getCentralAngle(sphericCo2), 0.00001);
        assertEquals(correctVal, sphericCo2.getCentralAngle(sphericCo1), 0.00001);
        assertEquals(correctVal, cartesianCo2.getCentralAngle(cartesianCo1), 0.00001);
        // design by contract
        assertThrows(AssertionError.class,
                ()->{
                    sphericCo1.getCentralAngle(null);
                });
    }

    @Test
    public void isEqual() {
        // arrange
        // in setUp()

        // act + assert
        assertTrue(cartesianCo1.isEqual(cartesianCo1));
        assertTrue(cartesianCo1.isEqual(sphericCo1));
        assertTrue(sphericCo1.isEqual(sphericCo1));
        assertTrue(sphericCo1.isEqual(cartesianCo1));
        assertFalse(cartesianCo1.isEqual(cartesianCo2));
        assertFalse(cartesianCo2.isEqual(cartesianCo1));
        assertFalse(cartesianCo1.isEqual(sphericCo2));
        assertFalse(sphericCo1.isEqual(sphericCo2));
        assertFalse(sphericCo2.isEqual(sphericCo1));
        assertFalse(sphericCo1.isEqual(cartesianCo2));
        // design by contract
        assertThrows(AssertionError.class, ()->{ cartesianCo1.isEqual(null); });
        assertThrows(AssertionError.class, ()->{ sphericCo1.isEqual(null); });
    }
}
