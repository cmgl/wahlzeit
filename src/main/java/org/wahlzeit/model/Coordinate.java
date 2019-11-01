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

import java.util.Objects;

public class Coordinate {
    private double x, y, z;

    public Coordinate(double x, double y, double z){
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

    public double getDistance(Coordinate co){
        return Math.sqrt( Math.pow((co.getX()-x),2) + Math.pow((co.getY()-y),2) + Math.pow((co.getZ()-z),2) );
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

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
}
