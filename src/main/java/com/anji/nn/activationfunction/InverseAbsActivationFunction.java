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
 * Created on Feb 26, 2004 by Philip Tucker
 */
package com.anji.nn.activationfunction;

/**
 * Inverse absolute value.
 *
 * @author Philip Tucker
 */
public class InverseAbsActivationFunction 
        implements ActivationFunction, DifferentiableFunction 
{

    private final static double SLOPE = 0.3f;

    /**
     * identifying string
     */
    public final static String NAME = "inverse-abs";

    /**
     * @return 
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return NAME;
    }

    /**
     * This class should only be accessd via ActivationFunctionFactory.
     */
    InverseAbsActivationFunction() {
        // no-op
    }

    /**
     * Inverse absolute value.
     *
     * @see com.anji.nn.activationfunction.ActivationFunction#apply(double)
     */
    @Override
    public double apply(double input) {
        return 1 / (SLOPE * Math.abs(input) + 1);
    }
    
    @Override
    public double applyDiff(double x) {
        // As given by Wolfram Alpha
        return -(SLOPE * x) / (Math.abs(x) * Math.pow(1 + SLOPE * Math.abs(x), 2));
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
        return 0;
    }

    /**
     * @see com.anji.nn.activationfunction.ActivationFunction#cost()
     */
    @Override
    public long cost() {
        return 75;
    }
}
