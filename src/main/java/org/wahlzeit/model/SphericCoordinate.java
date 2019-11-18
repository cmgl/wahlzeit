package org.wahlzeit.model;

public class SphericCoordinate implements Coordinate {
    public double phi, theta, radius;

    public SphericCoordinate(double phi, double theta, double radius){
        this.phi = phi;
        this.theta = theta;
        this.radius = radius;
    }

    @Override
    public double getX() {
        return (radius * Math.sin(theta) * Math.cos(phi));
    }
    @Override
    public double getY() {
        return (radius * Math.sin(theta) * Math.sin(phi));
    }
    @Override
    public double getZ() {
        return (radius * Math.cos(theta));
    }

    @Override
    public double getPhi() {
        return phi;
    }
    @Override
    public double getTheta() {
        return theta;
    }
    @Override
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

    @Override
    public boolean equals(Object o) {
        return asCartesianCoordinate().equals(o);
    }

    @Override
    public int hashCode() {
        return asCartesianCoordinate().hashCode();
    }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        return new CartesianCoordinate(getX(), getY(), getZ());
    }

    @Override
    public double getCartesianDistance(Coordinate co) {
        return doGetCartesianDistance(co);
    }

    private double doGetCartesianDistance(Coordinate co){
        return asCartesianCoordinate().getCartesianDistance(co);
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        return this;
    }

    @Override
    public double getCentralAngle(Coordinate co) {
        return doGetCentralAngle(co);
    }

    private double doGetCentralAngle(Coordinate co) {
        // absolute difference of longitude (theta)
        double differenceTheta = Math.abs(getTheta() - co.getTheta());

        // calculate central angle
        double a = Math.cos(co.getPhi()) * Math.sin(differenceTheta);
        double b = Math.cos(getPhi()) * Math.sin(co.getPhi());
        double c = Math.sin(getPhi()) * Math.cos(co.getPhi()) * Math.cos(differenceTheta);
        double d = Math.sin(getPhi()) * Math.sin(co.getPhi());
        double e = Math.cos(getPhi()) * Math.cos(co.getPhi()) * Math.cos(differenceTheta);
        return Math.atan( Math.sqrt( Math.pow(a,2) + Math.pow((b-c),2) ) / (d + e) );
    }

    @Override
    public boolean isEqual(Coordinate co){
        return asCartesianCoordinate().isEqual(co);
    }
}