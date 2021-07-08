package com.anji.nn.activationfunction;

/**
 * Divide activation function (divides first input by second input).
 *
 * @author Oliver Coleman
 */
public class DivideActivationFunction implements ActivationFunction, ActivationFunctionNonIntegrating {

    /**
     * identifying string
     */
    public final static String NAME = "divide";

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
    DivideActivationFunction() {
        // no-op
    }

    /**
     * Not used, returns 0.
     */
    @Override
    public double apply(double input) {
        return 0;
    }

    /**
     * Return first input divided by second input (or just first input if no
     * second input).Output is capped to +/- Float.MAX_VALUE
     * @param input
     * @param bias
     * @return 
     */
    @Override
    public double apply(double[] input, double bias) {
        if (input.length > 0) {
            if (input.length < 2) {
                return input[0];
            }
            double v = input[0] / input[1];
            if (Double.isNaN(v) || Double.isInfinite(v)) {
                boolean pos = Math.signum(input[0]) == Math.signum(input[1]);
                return pos ? Float.MAX_VALUE : -Float.MAX_VALUE;
            }
            return Math.max(-Float.MAX_VALUE, Math.min(Float.MAX_VALUE, v));
        }
        return 0;
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
