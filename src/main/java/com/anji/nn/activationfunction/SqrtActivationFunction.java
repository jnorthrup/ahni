package com.anji.nn.activationfunction;

/**
 * Square-root function.
 *
 * @author Oliver Coleman
 */
public class SqrtActivationFunction 
        implements ActivationFunction, DifferentiableFunction
{

    /**
     * identifying string
     */
    public final static String NAME = "sqrt";

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
    SqrtActivationFunction() {
        // no-op
    }

    /**
     * @see com.anji.nn.activationfunction.ActivationFunction#apply(double)
     */
    @Override
    public double apply(double input) {
        if (input > 0) {
            return Math.sqrt(input);
        }
        if (input < 0) {
            return -Math.sqrt(-input);
        }
        return 0;
    }
    
    @Override
    public double applyDiff(double x) {
        if (x > 0) {
            return 1 / 2 * Math.sqrt(x);
        } else if (x < 0) {
            return 1 / 2 * Math.sqrt(-x);
        } else 
            return 0;
    }

    /**
     * @see com.anji.nn.activationfunction.ActivationFunction#getMaxValue()
     */
    @Override
    public double getMaxValue() {
        return Double.MAX_VALUE;
    }

    /**
     * @see com.anji.nn.activationfunction.ActivationFunction#getMinValue()
     */
    @Override
    public double getMinValue() {
        return -Double.MAX_VALUE;
    }

    /**
     * @see com.anji.nn.activationfunction.ActivationFunction#cost()
     */
    @Override
    public long cost() {
        return 75;
    }
}
