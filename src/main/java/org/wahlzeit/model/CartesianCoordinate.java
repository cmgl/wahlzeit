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

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CartesianCoordinate implements Coordinate {
    private double x, y, z;

    public CartesianCoordinate(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public double getX(){
        return x;
    }
    @Override
    public double getY(){
        return y;
    }
    @Override
    public double getZ(){
        return z;
    }

    @Override
    public double getPhi() {
        return Math.atan( y / x );
    }
    @Override
    public double getTheta() {
        return Math.atan( Math.sqrt( Math.pow(x,2) + Math.pow(y,2) ) / z );
    }
    @Override
    public double getRadius() {
        return Math.sqrt( Math.pow(x,2) + Math.pow(y,2) + Math.pow(z,2) );
    }

    // use case of setters: GPS coordinates are inaccurate (bad GPS signal...) and correction required
    public void setX(double x){
        this.x = x;
    }
    public void setY(double y){
        this.y = y;
    }
    public void setZ(double z){
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {

        // check if both classes implement same interface
        Class thisClass = this.getClass();
        Class objClass = o.getClass();
        Class[] thisClassInterfaces = thisClass.getInterfaces();
        Class[] objClassInterfaces = objClass.getInterfaces();

        if (this == o) return true;
        if (o == null || thisClassInterfaces[0] != objClassInterfaces[0]) return false;

        return isEqual((Coordinate) o);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        int prime = 31;

        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));

        temp = Double.doubleToLongBits(y);
        result = prime * result + (int) (temp ^ (temp >>> 32));

        temp = Double.doubleToLongBits(z);
        result = prime * result + (int) (temp ^ (temp >>> 32));

        return result;
    }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        return this;
    }

    @Override
    public double getCartesianDistance(Coordinate co) {
        return doGetCartesianDistance(co);
    }

    private double doGetCartesianDistance(Coordinate co){
        return Math.sqrt( Math.pow((co.getX()-x),2) + Math.pow((co.getY()-y),2) + Math.pow((co.getZ()-z),2) );
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        return new SphericCoordinate(getPhi(), getTheta(), getRadius());
    }

    @Override
    public double getCentralAngle(Coordinate co) {
        return doGetCentralAngle(co);
    }

    // https://en.wikipedia.org/wiki/Great-circle_distance#Formulae
    private double doGetCentralAngle(Coordinate co) {
        return asSphericCoordinate().getCentralAngle(co);
    }

    @Override
    public boolean isEqual(Coordinate co){
        if (!areDoublesEqual(co.getX(), x)) return false;
        if (!areDoublesEqual(co.getY(), y)) return false;
        return areDoublesEqual(co.getZ(), z);
    }

    private static final double PRECISION = 1E-5;
    private static boolean areDoublesEqual(double value1, double value2) {
        if (Double.isNaN(value1) || Double.isNaN(value2))
            return false;
        return Math.abs(value1 - value2) < PRECISION;
    }
}