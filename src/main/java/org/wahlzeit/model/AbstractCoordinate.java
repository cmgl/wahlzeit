package org.wahlzeit.model;

public abstract class AbstractCoordinate implements Coordinate {

    public abstract CartesianCoordinate asCartesianCoordinate();

    public double getCartesianDistance(Coordinate co) {
        // test preconditions first
        assert (co != null);

        return this.asCartesianCoordinate().getCartesianDistance(co);
    }

    public abstract SphericCoordinate asSphericCoordinate();

    public double getCentralAngle(Coordinate co) {
        // check preconditions
        assert (co != null);

        return this.asSphericCoordinate().getCentralAngle(co);
    }

    public boolean isEqual(Coordinate co){
        // check preconditions
        assert (co != null);

        CartesianCoordinate cartesianCoordinate = co.asCartesianCoordinate();
        if (!areDoublesEqual(cartesianCoordinate.getX(), this.asCartesianCoordinate().getX())) return false;
        if (!areDoublesEqual(cartesianCoordinate.getY(), this.asCartesianCoordinate().getY())) return false;
        return areDoublesEqual(cartesianCoordinate.getZ(), this.asCartesianCoordinate().getZ());
    }

    private static final double PRECISION = 1E-5;
    private static boolean areDoublesEqual(double value1, double value2) {
        // check preconditions
        assert (!Double.isNaN(value1));
        assert (!Double.isNaN(value2));

        return Math.abs(value1 - value2) < PRECISION;
    }

    @Override
    public boolean equals(Object o) {
        // check preconditions
        assert(o != null);

        if (this == o) return true;

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

        // test postconditions
        assert (result >= Integer.MIN_VALUE);
        assert (Integer.MAX_VALUE >= result);

        return result;
    }

    public abstract void assertClassInvariants() throws AssertionError;
}
