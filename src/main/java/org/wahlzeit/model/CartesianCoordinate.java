/*
 * Class Coordinate
 *
 * Version 1.0
 *
 * Date 2019-10-25
 *
 * Copyright (c) 2006-2009 by Dirk Riehle, http://dirkriehle.com
 *
 * This file is part of the Wahlzeit photo rating application.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package org.wahlzeit.model;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CartesianCoordinate extends AbstractCoordinate implements Coordinate {
    private double x, y, z;

    private static final Logger log = Logger.getLogger(CartesianCoordinate.class.getName());

    public CartesianCoordinate(double x, double y, double z){
        // check preconditions
        try { assertXValue(x); } catch (AssertionError ex) {
            log.log(Level.WARNING, "x value argument not valid");
            throw new IllegalArgumentException("x value argument not valid");
        }
        try { assertYValue(y); } catch (AssertionError ex) {
            log.log(Level.WARNING, "y value argument not valid");
            throw new IllegalArgumentException("y value argument not valid");
        }
        try { assertZValue(z); } catch (AssertionError ex) {
            log.log(Level.WARNING, "z value argument not valid");
            throw new IllegalArgumentException("z value argument not valid");
        }

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public double getZ(){
        return z;
    }

    // use case of setters: GPS coordinates are inaccurate (bad GPS signal...) and correction required
    public void setX(double x){
        // check preconditions
        try { assertXValue(x); } catch (AssertionError ex) {
            log.log(Level.WARNING, "x value argument not valid");
            throw new IllegalArgumentException("x value argument not valid");
        }

        this.x = x;
    }
    public void setY(double y){
        // check preconditions
        try { assertYValue(y); } catch (AssertionError ex) {
            log.log(Level.WARNING, "y value argument not valid");
            throw new IllegalArgumentException("y value argument not valid");
        }

        this.y = y;
    }
    public void setZ(double z){
        // check preconditions
        try { assertZValue(z); } catch (AssertionError ex) {
            log.log(Level.WARNING, "z value argument not valid");
            throw new IllegalArgumentException("z value argument not valid");
        }

        this.z = z;
    }

    public CartesianCoordinate asCartesianCoordinate() {
        assertClassInvariants();
        return this;
    }

    public double getCartesianDistance(Coordinate co) {
        // check preconditions
        if(null == co){
            log.log(Level.WARNING, "Coordinate object argument cannot be null");
            throw new IllegalArgumentException("Coordinate object argument cannot be null");
        }

        CartesianCoordinate cartesianCoordinate = co.asCartesianCoordinate();

        // check preconditions
        doAssertClassInvariants(cartesianCoordinate);

        return doGetCartesianDistance(cartesianCoordinate);
    }

    private double doGetCartesianDistance(CartesianCoordinate co){
        return Math.sqrt( Math.pow((co.getX()-x),2) + Math.pow((co.getY()-y),2) + Math.pow((co.getZ()-z),2) );
    }

    public SphericCoordinate asSphericCoordinate() {
        SphericCoordinate sphericCoordinate = new SphericCoordinate(getPhi(), getTheta(), getRadius());
        sphericCoordinate.assertClassInvariants(); // out of the class
        return sphericCoordinate;
    }

    // helper methods for conversion to SphericCoordinate
    private double getPhi() {
        return Math.atan( y / x );
    }
    private double getTheta() {
        return Math.atan( Math.sqrt( Math.pow(x,2) + Math.pow(y,2) ) / z );
    }
    private double getRadius() {
        return Math.sqrt( Math.pow(x,2) + Math.pow(y,2) + Math.pow(z,2) );
    }

    public void assertClassInvariants() throws AssertionError {
        doAssertClassInvariants(this);
    }

    private void doAssertClassInvariants(CartesianCoordinate cartesianCoordinate) throws AssertionError {
        assertXValue(cartesianCoordinate.getPhi());
        assertYValue(cartesianCoordinate.getTheta());
        assertZValue(cartesianCoordinate.getRadius());
    }

    private void assertXValue(double x) throws AssertionError {
        assert (!Double.isNaN(x));
    }
    private void assertYValue(double y) throws AssertionError {
        assert (!Double.isNaN(y));
    }
    private void assertZValue(double z) throws AssertionError {
        assert (!Double.isNaN(z));
    }
}