package com.anji.nn.activationfunction;

/**
 * Negative linear activation function.
 */
public class NegatedLinearActivationFunction 
        implements ActivationFunction, DifferentiableFunction
{

    /**
     * identifying string
     */
    public final static String NAME = "negated-linear";

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
    NegatedLinearActivationFunction() {
        // no-op
    }

    /**
     * Return <code>input</code> with opposite sign.
     *
     * @see com.anji.nn.activationfunction.ActivationFunction#apply(double)
     */
    @Override
    public double apply(double input) {
        return -input;
    }
    
    @Override
    public double applyDiff(double x) {
        return -1;
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
        return -Float.MAX_VALUE;
    }

    /**
     * @see com.anji.nn.activationfunction.ActivationFunction#cost()
     */
    @Override
    public long cost() {
        return 42;
    }
}
