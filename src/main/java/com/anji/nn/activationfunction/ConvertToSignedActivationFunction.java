package com.anji.nn.activationfunction;

/**
 * @author Oliver Coleman
 */
public class ConvertToSignedActivationFunction 
        implements ActivationFunction, DifferentiableFunction
{

    /**
     * unique ID string
     */
    public final static String NAME = "sign";

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
        if (input <= 0) {
            input = 0;
        } else if (input >= 1) {
            input = 1;
        }
        return (input * 2) - 1;
    }
    
    @Override
    public double applyDiff(double x) {
        if (x <= 0 || x >= 1) {
            return 0;
        } else {
            return 2;
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
