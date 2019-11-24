package org.wahlzeit.model;

public class SphericCoordinate extends AbstractCoordinate {
    public double phi, theta, radius;

    public SphericCoordinate(double phi, double theta, double radius){
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
        this.phi = phi;
    }
    public void setTheta(double theta) {
        this.theta = theta;
    }
    public void setRadius(double radius) {
        this.radius = radius;
    }

    public CartesianCoordinate asCartesianCoordinate() {
        return new CartesianCoordinate(getX(), getY(), getZ());
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
        return this;
    }

    public double getCentralAngle(SphericCoordinate sphericCoordinate) {
        return doGetCentralAngle(sphericCoordinate);
    }

    private double doGetCentralAngle(Coordinate co) {
        SphericCoordinate sphericCoordinate = co.asSphericCoordinate();

        // absolute difference of longitude (theta)
        double differenceTheta = Math.abs(getTheta() - sphericCoordinate.getTheta());

        // calculate central angle
        double a = Math.cos(sphericCoordinate.getPhi()) * Math.sin(differenceTheta);
        double b = Math.cos(getPhi()) * Math.sin(sphericCoordinate.getPhi());
        double c = Math.sin(getPhi()) * Math.cos(sphericCoordinate.getPhi()) * Math.cos(differenceTheta);
        double d = Math.sin(getPhi()) * Math.sin(sphericCoordinate.getPhi());
        double e = Math.cos(getPhi()) * Math.cos(sphericCoordinate.getPhi()) * Math.cos(differenceTheta);
        return Math.atan( Math.sqrt( Math.pow(a,2) + Math.pow((b-c),2) ) / (d + e) );
    }
}