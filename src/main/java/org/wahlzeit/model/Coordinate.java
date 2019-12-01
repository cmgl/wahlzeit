package org.wahlzeit.model;

public interface Coordinate {

    CartesianCoordinate asCartesianCoordinate();

    double getCartesianDistance(Coordinate co);

    SphericCoordinate asSphericCoordinate();

    double getCentralAngle(Coordinate co);

    boolean isEqual(Coordinate co);

    void assertClassInvariants() throws AssertionError;
}