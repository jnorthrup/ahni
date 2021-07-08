/*
 * Copyright (C) 2004 Derek James and Philip Tucker
 * 
 * This file is part of ANJI (Another NEAT Java Implementation).
 * 
 * ANJI is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See
 * the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program; if
 * not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307 USA
 * 
 * Created on Aug 14, 2004 by Philip Tucker
 */
package com.anji.nn.activationfunction;

/**
 * @author Philip Tucker
 */
public class SignedClampedLinearActivationFunction 
        implements ActivationFunction, DifferentiableFunction
{

    /**
     * id string
     */
    public final static String NAME = "signed-clamped-linear";

    /**
     * @return 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return NAME;
    }

    /**
     * @see com.anji.nn.activationfunction.ActivationFunction#apply(double)
     */
    @Override
    public double apply(double input) {
        if (input <= -1.0) {
            return -1;
        } else if (input >= 1.0) {
            return 1;
        } else {
            return input;
        }
    }
    
    @Override
    public double applyDiff(double x) {
        if (x <= 1.0 || x >= 1.0) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * @see com.anji.nn.activationfunction.ActivationFunction#getMaxValue()
     */
    @Override
    public double getMaxValue() {
        return 1;
    }

    /**
     * @see com.anji.nn.activationfunction.ActivationFunction#getMinValue()
     */
    @Override
    public double getMinValue() {
        return -1;
    }

    /**
     * @see com.anji.nn.activationfunction.ActivationFunction#cost()
     */
    @Override
    public long cost() {
        return 42;
    }

}
