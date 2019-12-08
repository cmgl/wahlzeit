package org.wahlzeit.model;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class SphericCoordinateTest {

    private static CartesianCoordinate cartesianCo1;
    private static CartesianCoordinate cartesianCo2;
    private static SphericCoordinate sphericCo1;
    private static SphericCoordinate sphericCo2;

    @BeforeClass
    public static void setup() {
        cartesianCo1 = new CartesianCoordinate(1, 2, 3);
        sphericCo1 = new SphericCoordinate(1.1071487177940904, 0.6405223126794246, 3.7416573867739413);

        sphericCo2 = new SphericCoordinate(0.90, 0.82, 8.77);
        cartesianCo2 = new CartesianCoordinate(3.9858556908757925, 5.022808802826915, 5.983079987912371);
    }

    @Test
    public void getPhi() {
        // arrange
        // in setUp()

        // act + assert
        assertEquals(sphericCo1.getPhi(), 1.1071487177940904, 0.00001);
        assertEquals(sphericCo2.getPhi(), 0.90, 0.00001);
    }

    @Test
    public void getTheta() {
        // arrange
        // in setUp()

        // act + assert
        assertEquals(sphericCo1.getTheta(), 0.6405223126794246, 0.00001);
        assertEquals(sphericCo2.getTheta(), 0.82, 0.00001);
    }

    @Test
    public void getRadius() {
        // arrange
        // in setUp()

        // act + assert
        assertEquals(sphericCo1.getRadius(), 3.7416573867739413, 0.00001);
        assertEquals(sphericCo2.getRadius(), 8.77, 0.00001);
    }

    @Test
    public void asCartesianCoordinate() {
        // arrange
        Coordinate caCo1 = sphericCo1.asCartesianCoordinate();
        Coordinate caCo2 = sphericCo2.asCartesianCoordinate();

        // act + assert
        assertTrue(caCo1.equals(cartesianCo1));
        assertTrue(caCo2.equals(cartesianCo2));
    }

    @Test
    public void asSphericCoordinate() {
        // arrange
        Coordinate spCo1 = sphericCo1.asSphericCoordinate();
        Coordinate spCo2 = sphericCo2.asSphericCoordinate();

        // act + assert
        assertTrue(spCo1.equals(sphericCo1));
        assertTrue(spCo2.equals(sphericCo2));
    }
}