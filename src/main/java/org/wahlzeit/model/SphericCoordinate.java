package org.wahlzeit.model;

public class SphericCoordinate extends AbstractCoordinate implements Coordinate {
    private double phi, theta, radius;

    public SphericCoordinate(double phi, double theta, double radius){
        // check preconditions
        assertPhiValue(phi);
        assertThetaValue(theta);
        assertRadiusValue(radius);

        this.phi = phi;
        this.theta = theta;
        this.radius = radius;
    }

    public double getPhi() {
        return phi;
    }
    public double getTheta() {
        return theta;
    }
    public double getRadius() {
        return radius;
    }

    // use case of setters: GPS coordinates are inaccurate (bad GPS signal...) and correction required
    public void setPhi(double phi) {
        assertPhiValue(phi); // check preconditions
        this.phi = phi;
    }
    public void setTheta(double theta) {
        assertThetaValue(theta); // check preconditions
        this.theta = theta;
    }
    public void setRadius(double radius) {
        assertRadiusValue(radius); // check preconditions
        this.radius = radius;
    }

    public CartesianCoordinate asCartesianCoordinate() {
        CartesianCoordinate cartesianCoordinate = new CartesianCoordinate(getX(), getY(), getZ());
        cartesianCoordinate.assertClassInvariants(); // out of the class
        return cartesianCoordinate;
    }

    // helper methods for conversion to CartesianCoordinate
    private double getX() {
        return (radius * Math.sin(theta) * Math.cos(phi));
    }
    private double getY() {
        return (radius * Math.sin(theta) * Math.sin(phi));
    }
    private double getZ() {
        return (radius * Math.cos(theta));
    }

    public SphericCoordinate asSphericCoordinate() {
        assertClassInvariants();
        return this;
    }

    public double getCentralAngle(Coordinate co) {
        // check preconditions
        assert (co != null);

        SphericCoordinate sphericCoordinate = co.asSphericCoordinate();

        // check preconditions
        doAssertClassInvariants(sphericCoordinate);

        return doGetCentralAngle(sphericCoordinate);
    }

    private double doGetCentralAngle(SphericCoordinate sphericCoordinate) {
        // absolute difference of longitude (theta)
        double differenceTheta = Math.abs(getTheta() - sphericCoordinate.getTheta());

        assertThetaValue(differenceTheta);

        // calculate central angle
        double a = Math.cos(sphericCoordinate.getPhi()) * Math.sin(differenceTheta);
        double b = Math.cos(getPhi()) * Math.sin(sphericCoordinate.getPhi());
        double c = Math.sin(getPhi()) * Math.cos(sphericCoordinate.getPhi()) * Math.cos(differenceTheta);
        double d = Math.sin(getPhi()) * Math.sin(sphericCoordinate.getPhi());
        double e = Math.cos(getPhi()) * Math.cos(sphericCoordinate.getPhi()) * Math.cos(differenceTheta);
        return Math.atan( Math.sqrt( Math.pow(a,2) + Math.pow((b-c),2) ) / (d + e) );
    }

    public void assertClassInvariants() throws AssertionError {
        doAssertClassInvariants(this);
    }

    private void doAssertClassInvariants(SphericCoordinate sphericCoordinate) throws AssertionError {
        assertPhiValue(sphericCoordinate.getPhi());
        assertThetaValue(sphericCoordinate.getTheta());
        assertRadiusValue(sphericCoordinate.getRadius());
    }

    private void assertPhiValue(double phi) throws AssertionError {
        assert (!Double.isNaN(phi));
        assert (phi >= 0);
        assert (phi <= Math.PI);
    }
    private void assertThetaValue(double theta) throws AssertionError {
        assert (!Double.isNaN(theta));
        assert (theta >= 0);
        assert (theta <= 2 * Math.PI);
    }
    private void assertRadiusValue(double radius) throws AssertionError {
        assert (!Double.isNaN(radius));
        assert (radius >= 0);
    }
}