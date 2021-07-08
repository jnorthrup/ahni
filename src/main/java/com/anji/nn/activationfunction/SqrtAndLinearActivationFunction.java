package com.anji.nn.activationfunction;

/**
 * Square-root function for values with magnitude > 1, otherwise linear.
 *
 * @author Oliver Coleman
 */
public class SqrtAndLinearActivationFunction 
        implements ActivationFunction, DifferentiableFunction
{

    /**
     * identifying string
     */
    public final static String NAME = "sqrt-linear";

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return NAME;
    }

    /**
     * This class should only be accessd via ActivationFunctionFactory.
     */
    SqrtAndLinearActivationFunction() {
        // no-op
    }

    /**
     * @see com.anji.nn.activationfunction.ActivationFunction#apply(double)
     */
    @Override
    public double apply(double input) {
        if (input >= -1 && input <= 1) {
            return input;
        }
        if (input > 0) {
            return Math.sqrt(input);
        } else {
            return -Math.sqrt(-input);
        }
    }
    
    @Override
    public double applyDiff(double x) {
        if (x >= -1 && x <= 1) {
            return 1;
        }
        if (x > 0) {
            return 1 / 2 * Math.sqrt(x);
        } else {
            return 1 / 2 * Math.sqrt(-x);
        }
    }

    /**
     * @see com.anji.nn.activationfunction.ActivationFunction#getMaxValue()
     */
    public double getMaxValue() {
        return Double.MAX_VALUE;
    }

    /**
     * @see com.anji.nn.activationfunction.ActivationFunction#getMinValue()
     */
    public double getMinValue() {
        return -Double.MAX_VALUE;
    }

    /**
     * @see com.anji.nn.activationfunction.ActivationFunction#cost()
     */
    public long cost() {
        return 75;
    }
}
