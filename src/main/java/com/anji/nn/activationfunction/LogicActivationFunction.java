package com.anji.nn.activationfunction;

/**
 * Absolute activation function.
 *
 * @author Oliver Coleman
 */
public abstract class LogicActivationFunction implements ActivationFunction, ActivationFunctionNonIntegrating {

    /**
     * Not used as this is a non-integrating function, returns 0.
     *
     * @see #apply(double[], double)
     */
    @Override
    public double apply(double input) {
        return 0;
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
        return 42;
    }
}
