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

public class CartesianCoordinate extends AbstractCoordinate {
    public double x, y, z;

    public CartesianCoordinate(double x, double y, double z){
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
        this.x = x;
    }
    public void setY(double y){
        this.y = y;
    }
    public void setZ(double z){
        this.z = z;
    }

    public CartesianCoordinate asCartesianCoordinate() {
        return this;
    }

    public double getCartesianDistance(CartesianCoordinate cartesianCoordinate) {
        return doGetCartesianDistance(cartesianCoordinate);
    }

    private double doGetCartesianDistance(CartesianCoordinate co){
        return Math.sqrt( Math.pow((co.getX()-x),2) + Math.pow((co.getY()-y),2) + Math.pow((co.getZ()-z),2) );
    }

    public SphericCoordinate asSphericCoordinate() {
        return new SphericCoordinate(getPhi(), getTheta(), getRadius());
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
}