package org.wahlzeit.model;

public abstract class AbstractCoordinate implements Coordinate {

    public abstract CartesianCoordinate asCartesianCoordinate();

    public double getCartesianDistance(Coordinate co) {
        CartesianCoordinate cartesianCoordinate = co.asCartesianCoordinate();
        return this.asCartesianCoordinate().getCartesianDistance(cartesianCoordinate);
    }

    public abstract SphericCoordinate asSphericCoordinate();

    public double getCentralAngle(Coordinate co) {
        SphericCoordinate sphericCoordinate = co.asSphericCoordinate();
        return this.asSphericCoordinate().getCentralAngle(sphericCoordinate);
    }

    public boolean isEqual(Coordinate co){
        CartesianCoordinate cartesianCoordinate = co.asCartesianCoordinate();
        if (!areDoublesEqual(cartesianCoordinate.getX(), this.asCartesianCoordinate().getX())) return false;
        if (!areDoublesEqual(cartesianCoordinate.getY(), this.asCartesianCoordinate().getY())) return false;
        return areDoublesEqual(cartesianCoordinate.getZ(), this.asCartesianCoordinate().getZ());
    }

    private static final double PRECISION = 1E-5;
    private static boolean areDoublesEqual(double value1, double value2) {
        if (Double.isNaN(value1) || Double.isNaN(value2))
            return false;
        return Math.abs(value1 - value2) < PRECISION;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null) return false;

        // check if object's class extends AbstractCoordinate
        Class<?> objSuperclass = o.getClass().getSuperclass();
        if (this.getClass().getSuperclass().getClass() != objSuperclass.getClass())
            return false;

        return isEqual((Coordinate) o);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        int prime = 31;

        temp = Double.doubleToLongBits(this.asCartesianCoordinate().getX());
        result = (int) (temp ^ (temp >>> 32));

        temp = Double.doubleToLongBits(this.asCartesianCoordinate().getY());
        result = prime * result + (int) (temp ^ (temp >>> 32));

        temp = Double.doubleToLongBits(this.asCartesianCoordinate().getZ());
        result = prime * result + (int) (temp ^ (temp >>> 32));

        return result;
    }
}
