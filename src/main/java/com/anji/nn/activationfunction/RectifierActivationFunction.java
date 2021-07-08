/*
 *   YAHNI Yet Another HyperNEAT Implementation
 *   Copyright (C) 2020  Christian Lins <christian@lins.me>
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.anji.nn.activationfunction;

/**
 * Ramp activation function.
 *
 * @author Oliver Coleman
 * Edited by Christian Lins, 2020
 */
public class RectifierActivationFunction implements ActivationFunction, DifferentiableFunction {

    /**
     * identifying string
     */
    public final static String NAME = "relu";

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
    RectifierActivationFunction() {
        // no-op
    }

    /**
     * Returns 0 if the input <= 0, otherwise the input value.
     *
     * @see com.anji.nn.activationfunction.ActivationFunction#apply(double)
     */
    @Override
    public double apply(double x) {
        return Math.max(0, x);
    }
    
    @Override
    public double applyDiff(double x) {
        if (x < 0) {
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
        return Float.MAX_VALUE;
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
        return 42;
    }

}
