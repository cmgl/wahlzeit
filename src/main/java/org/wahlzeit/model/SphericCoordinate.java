package org.wahlzeit.model;

import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class SphericCoordinate extends AbstractCoordinate implements Coordinate {

    private static final Logger log = Logger.getLogger(CartesianCoordinate.class.getName());

    private static Hashtable<String, SphericCoordinate> cache = new Hashtable<String, SphericCoordinate>();

    public static SphericCoordinate getSphericCoordinate(double phi, double theta, double radius){
        String key = getKey(phi, theta, radius);
        SphericCoordinate sphericCoordinate;

        if(cache.containsKey(key)){
            sphericCoordinate = cache.get(key);
        } else {
            sphericCoordinate = new SphericCoordinate(phi, theta, radius);
            cache.put(key, sphericCoordinate);
        }
        return sphericCoordinate;
    }

    private static String getKey(double phi, double theta, double radius){
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(phi + ";");
        stringBuilder.append(theta + ";");
        stringBuilder.append(radius);

        String key = stringBuilder.toString();

        return key;
    }

    private final double phi, theta, radius;

    private SphericCoordinate(double phi, double theta, double radius){
        // check preconditions
        try { assertPhiValue(phi); } catch (AssertionError ex) {
            log.log(Level.WARNING, "phi value argument not valid");
            throw new IllegalArgumentException("phi value argument not valid");
        }
        try { assertThetaValue(theta); } catch (AssertionError ex) {
            log.log(Level.WARNING, "theta value argument not valid");
            throw new IllegalArgumentException("theta value argument not valid");
        }
        try { assertRadiusValue(radius); } catch (AssertionError ex) {
            log.log(Level.WARNING, "radius value argument not valid");
            throw new IllegalArgumentException("radius value argument not valid");
        }

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

    public CartesianCoordinate asCartesianCoordinate() {
        CartesianCoordinate cartesianCoordinate = CartesianCoordinate.getCartesianCoordinate(getX(), getY(), getZ());
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
        if(null == co){
            log.log(Level.WARNING, "Coordinate object argument cannot be null");
            throw new IllegalArgumentException("Coordinate object argument cannot be null");
        }

        SphericCoordinate sphericCoordinate = co.asSphericCoordinate();

        // check preconditions
        doAssertClassInvariants(sphericCoordinate);

        return doGetCentralAngle(sphericCoordinate);
    }

    private double doGetCentralAngle(SphericCoordinate sphericCoordinate) {
        // absolute difference of longitude (theta)
        double differenceTheta = Math.abs(getTheta() - sphericCoordinate.getTheta());

        try {
            assertThetaValue(differenceTheta);
        } catch (AssertionError ex) {
            log.log(Level.WARNING, "difference between thetas not valid");
            throw new ArithmeticException("difference between thetas not valid");
        }

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