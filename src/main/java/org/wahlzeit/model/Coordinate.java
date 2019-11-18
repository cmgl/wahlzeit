package org.wahlzeit.model;

public interface Coordinate {

    double getX();
    double getY();
    double getZ();

    double getPhi();
    double getTheta();
    double getRadius();

    CartesianCoordinate asCartesianCoordinate();

    double getCartesianDistance(Coordinate co);

    SphericCoordinate asSphericCoordinate();

    double getCentralAngle(Coordinate co);

    boolean isEqual(Coordinate co);
}