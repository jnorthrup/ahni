
package com.anji.nn.activationfunction;

/**
 *
 * @author cLins
 */
public class ExponentialLinearFunction 
        implements ActivationFunction, DifferentiableFunction
{

    /**
     * identifying string
     */
    public final static String NAME = "elu";
    
    private double alpha = 1.0;

    /**
     * @return 
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return NAME;
    }
    
    @Override
    public double apply(double input) {
        if (input < 0) {
            return alpha * (Math.exp(input) - 1);
        } else {
            return input;
        }
    }
    
    @Override
    public double applyDiff(double x) {
        if (x < 0) {
            return alpha * Math.exp(x);
        } else {
            return 1;
        }
    }

    @Override
    public double getMaxValue() {
        return Double.MAX_VALUE;
    }

    @Override
    public double getMinValue() {
        return -alpha;
    }

    @Override
    public long cost() {
        return 42;
    }
    
}
