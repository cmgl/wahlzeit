package org.wahlzeit.model;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class CartesianCoordinateTest {

    private static CartesianCoordinate cartesianCo1;
    private static CartesianCoordinate cartesianCo2;
    private static SphericCoordinate sphericCo1;
    private static SphericCoordinate sphericCo2;

    @BeforeClass
    public static void setup() {
        cartesianCo1 = CartesianCoordinate.getCartesianCoordinate(1, 2, 3);
        sphericCo1 = SphericCoordinate.getSphericCoordinate(1.1071487177940904, 0.6405223126794246, 3.7416573867739413);

        sphericCo2 = SphericCoordinate.getSphericCoordinate(0.90, 0.82, 8.77);
        cartesianCo2 = CartesianCoordinate.getCartesianCoordinate(3.9858556908757925, 5.022808802826915, 5.983079987912371);
    }

    @Test
    public void getX() {
        // arrange
        // in setUp()

        // act + assert
        assertEquals(cartesianCo1.getX(), 1,0.00001);
        assertEquals(cartesianCo2.getX(), 3.9858556908757925, 0.00001);
    }

    @Test
    public void getY() {
        // arrange
        // in setUp()

        // act + assert
        assertEquals(cartesianCo1.getY(), 2, 0.00001);
        assertEquals(cartesianCo2.getY(), 5.022808802826915, 0.00001);
    }

    @Test
    public void getZ() {
        // arrange
        // in setUp()

        // act + assert
        assertEquals(cartesianCo1.getZ(), 3, 0.00001);
        assertEquals(cartesianCo2.getZ(), 5.983079987912371, 0.00001);
    }

    @Test
    public void asCartesianCoordinate() {
        // arrange
        Coordinate caCo1 = cartesianCo1.asCartesianCoordinate();
        Coordinate caCo2 = cartesianCo2.asCartesianCoordinate();

        // act + assert
        assertTrue(caCo1.equals(cartesianCo1));
        assertTrue(caCo2.equals(cartesianCo2));
    }

    @Test
    public void asSphericCoordinate() {
        // arrange
        Coordinate spCo1 = cartesianCo1.asSphericCoordinate();
        Coordinate spCo2 = cartesianCo2.asSphericCoordinate();

        // act + assert
        assertTrue(spCo1.equals(sphericCo1));
        assertTrue(spCo2.equals(sphericCo2));
    }

    @Test
    public void immutabilityCheck() {
        // arrange
        CartesianCoordinate cartesianCoordinate1 = CartesianCoordinate.getCartesianCoordinate(1,1,1);
        CartesianCoordinate cartesianCoordinate2 = CartesianCoordinate.getCartesianCoordinate(1,1,1);
        CartesianCoordinate cartesianCoordinate3 = CartesianCoordinate.getCartesianCoordinate(2,2,2);
        CartesianCoordinate cartesianCoordinate4 = cartesianCoordinate1.setX(9);

        // act + assert

        // test Coordinate classes are immutable
        assertFalse(cartesianCoordinate4 == cartesianCoordinate1);

        // test Coordinate classes are shared
        assertTrue(cartesianCoordinate1 == cartesianCoordinate2);

        // test Coordinate objects are interchangeable
        assertTrue(cartesianCoordinate3.getX() == 2);
        assertTrue(cartesianCoordinate3.getY() == 2);
        assertTrue(cartesianCoordinate3.getZ() == 2);
        cartesianCoordinate3 = CartesianCoordinate.getCartesianCoordinate(3,3,3); // change Coordinate object
        assertTrue(cartesianCoordinate3.getX() == 3);
        assertTrue(cartesianCoordinate3.getY() == 3);
        assertTrue(cartesianCoordinate3.getZ() == 3);
    }
}