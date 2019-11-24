package org.wahlzeit.model;

public interface Coordinate {

    // removed getters from interface, thank you for your fast feedback!

    CartesianCoordinate asCartesianCoordinate();

    double getCartesianDistance(Coordinate co);

    SphericCoordinate asSphericCoordinate();

    double getCentralAngle(Coordinate co);

    boolean isEqual(Coordinate co);
}