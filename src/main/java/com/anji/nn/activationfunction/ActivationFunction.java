/*
 * Copyright (C) 2004  Derek James and Philip Tucker
 *
 * This file is part of ANJI (Another NEAT Java Implementation).
 *
 * ANJI is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * Created on Feb 26, 2004 by Philip Tucker
 */
package com.anji.nn.activationfunction;


/**
 * Abstracts activation function for neurons.
 *
 * @author Philip Tucker
 */
public enum ActivationFunction {
    /*
     * Copyright (C) 2004 Oliver Coleman
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
     */


    /**
     * Absolute activation function.
     *
     * @author Oliver Coleman
     */
    AbsoluteActivationFunction
            /*implements ActivationFunction, DifferentiableFunction*/ {

        /**
         * identifying string
         */
        public final String NAME = "absolute";

        /**
         * @see Object#toString()
         */
        @Override
        public String getName() {
            return NAME;
        }

//				/**
//				 * This class should only be accessd via ActivationFunctionFactory.
//				 */
//				AbsoluteActivationFunction() {
//					// no-op
//				}

        /**
         * Return absolute value of <code>input</code>, clamped to range [0, 1].
         *
         * @see com.anji.nn.activationfunction.ActivationFunction#apply(double)
         */
        @Override
        public Double apply(double input) {
            return Math.abs(input);
        }

        public Double applyDiff(double x) {
            if (x < 0.) {
                return -1.;
            } else {
                return 1.; // If x == 0 this is not correct but is probably really rare
            }
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#getMaxValue()
         */
        public double getMaxValue() {
            return Float.MAX_VALUE;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#getMinValue()
         */
        public double getMinValue() {
            return 0.;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#cost()
         */
        public long cost() {
            return 42;
        }
    },
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


    /**
     * Cosine activation function.
     *
     * @author Philip Tucker
     */
    CosineActivationFunction
            /*implements ActivationFunction, DifferentiableFunction*/ {

        /**
         * identifying string
         */
        public final String NAME = "cosine";

        /**
         * @return
         * @see Object#toString()
         */
        @Override
        public String getName() {
            return NAME;
        }


        /**
         * Returns cosine(input).
         */
        @Override
        public Double apply(double input) {
            return Math.cos(input);
        }

        @Override
        public Double applyDiff(double x) {
            return -Math.sin(x);
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#getMaxValue()
         */
        @Override
        public double getMaxValue() {
            return 1.;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#getMinValue()
         */
        @Override
        public double getMinValue() {
            return -1.;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#cost()
         */
        @Override
        public long cost() {
            return 42;
        }
    },


    /**
     * Divide activation function (divides first input by second input).
     *
     * @author Oliver Coleman
     */
    DivideActivationFunction /*implements ActivationFunction, ActivationFunctionNonIntegrating*/ {

        /**
         * identifying string
         */
        public final String NAME = "divide";

        /**
         * @return
         * @see Object#toString()
         */
        @Override
        public String getName() {
            return NAME;
        }


        /**
         * Not used, returns 0.
         */
        @Override
        public Double apply(double input) {
            return 0.;
        }

        /**
         * Return first input divided by second input (or just first input if no
         * second input).Output is capped to +/- Float.MAX_VALUE
         * @param input
         * @param bias
         * @return
         */
        @Override
        public Double apply(double[] input, double bias) {
            if (input.length > 0) {
                if (input.length < 2) {
                    return input[0];
                }
                double v = input[0] / input[1];
                if (Double.isNaN(v) || Double.isInfinite(v)) {
                    boolean pos = Math.signum(input[0]) == Math.signum(input[1]);
                    return Double.valueOf(pos ? Float.MAX_VALUE : -Float.MAX_VALUE);
                }
                return Math.max(-Float.MAX_VALUE, Math.min(Float.MAX_VALUE, v));
            }
            return 0.;
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
            return 0.;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#cost()
         */
        @Override
        public long cost() {
            return 42;
        }
    },
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


    /**
     * Modified classic sigmoid. Submitted to NEAT group by zenguyuno@yahoo.com from
     * EvSail ANN package.
     *
     * @author Philip Tucker
     */
    EvSailSigmoidActivationFunction
            /*implements ActivationFunction, DifferentiableFunction*/ {

        private final double SEP = 0.3f;
        private final double DENOMINATOR = 2 * SEP * SEP;

        /**
         * identifying string
         */
        public final String NAME = "evsail-sigmoid";

        /**
         * @return
         * @see Object#toString()
         */
        @Override
        public String getName() {
            return NAME;
        }

        /**
         * This class should only be accessd via ActivationFunctionFactory.
         */
//		EvSailSigmoidActivationFunction() {
//		// no-op
//		}

        /**
         * Approximation of classic sigmoid.
         *
         * @see com.anji.nn.activationfunction.ActivationFunction#apply(double)
         */
        @Override
        public Double apply(double input) {
            if (input <= -SEP) {
                return 0.;
            } else if (input <= 0) {
                double tmp = input + SEP;
                return (tmp * tmp) / DENOMINATOR;
            } else if (input < SEP) {
                double tmp = input - SEP;
                return 1 - ((tmp * tmp) / DENOMINATOR);
            } else {
                return 1.;
            }
        }

        @Override
        public Double applyDiff(double x) {
            if (x <= -SEP) {
                return 0.;
            } else if (x <= 0) {
                return (2 * (SEP + x)) / DENOMINATOR;
            } else if (x < SEP) {
                return -(2 * (-SEP + x)) / DENOMINATOR;
            } else {
                return 0.;
            }
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#getMaxValue()
         */
        @Override
        public double getMaxValue() {
            return 1.;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#getMinValue()
         */
        @Override
        public double getMinValue() {
            return 0.;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#cost()
         */
        @Override
        public long cost() {
            return 166;
        }
    },


    /**
     * @author cLins
     */
    ExponentialLinearFunction
            /*implements ActivationFunction, DifferentiableFunction
             */ {

        /**
         * identifying string
         */
        public final String NAME = "elu";

        private final double alpha = 1.0;

        /**
         * @return
         * @see Object#toString()
         */
        @Override
        public String getName() {
            return NAME;
        }

        @Override
        public Double apply(double input) {
            if (input < 0) {
                return alpha * (Math.exp(input) - 1);
            } else {
                return input;
            }
        }

        @Override
        public Double applyDiff(double x) {
            if (x < 0) {
                return alpha * Math.exp(x);
            } else {
                return 1.;
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

    },
    /*
     * Copyright (C) 2004 Oliver Coleman
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
     */


    /**
     * Gaussian activation function.
     *
     * @author Oliver Coleman
     */
    GaussianActivationFunction
            /*implements ActivationFunction, DifferentiableFunction*/ {

        private final double SLOPE = 1;

        /**
         * identifying string
         */
        public final String NAME = "gaussian";

        /**
         * @see Object#toString()
         */
        public String getName() {
            return NAME;
        }

        /**
         * This class should only be accessd via ActivationFunctionFactory.
         */
//        GaussianActivationFunction() {
//            // no-op
//        }

        /**
         * Return <code>input</code> with Gaussian function transformation.
         *
         * @see com.anji.nn.activationfunction.ActivationFunction#apply(double)
         */
        @Override
        public Double apply(double input) {
            return Math.exp(-(input * input * SLOPE));
        }

        @Override
        public Double applyDiff(double x) {
            return -2 * Math.exp(-SLOPE * x * x) * SLOPE * x;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#getMaxValue()
         */
        @Override
        public double getMaxValue() {
            return 1.;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#getMinValue()
         */
        @Override
        public double getMinValue() {
            return 0.;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#cost()
         */
        @Override
        public long cost() {
            return 42;
        }
    },
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


    /**
     * Inverse absolute value.
     *
     * @author Philip Tucker
     */
    InverseAbsActivationFunction
            /*implements ActivationFunction, DifferentiableFunction */ {

        private final double SLOPE = 0.3f;

        /**
         * identifying string
         */
        public final String NAME = "inverse-abs";

        /**
         * @return
         * @see Object#toString()
         */
        @Override
        public String getName() {
            return NAME;
        }

        /**
         * This class should only be accessd via ActivationFunctionFactory.
         */
//        InverseAbsActivationFunction() {
//            // no-op
//        }

        /**
         * Inverse absolute value.
         *
         * @see com.anji.nn.activationfunction.ActivationFunction#apply(double)
         */
        @Override
        public Double apply(double input) {
            return 1 / (SLOPE * Math.abs(input) + 1);
        }

        @Override
        public Double applyDiff(double x) {
            // As given by Wolfram Alpha
            return -(SLOPE * x) / (Math.abs(x) * Math.pow(1 + SLOPE * Math.abs(x), 2));
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#getMaxValue()
         */
        @Override
        public double getMaxValue() {
            return 1.;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#getMinValue()
         */
        @Override
        public double getMinValue() {
            return 0.;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#cost()
         */
        @Override
        public long cost() {
            return 75;
        }
    },
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


    /**
     * Linear activation function.
     *
     * @author Philip Tucker
     */
    LinearActivationFunction
            /*implements ActivationFunction, DifferentiableFunction */ {

        /**
         * identifying string
         */
        public final String NAME = "linear";

        /**
         * @return
         * @see Object#toString()
         */
        @Override
        public String getName() {
            return NAME;
        }

        /**
         * This class should only be accessd via ActivationFunctionFactory.
         */
//        LinearActivationFunction() {
//            // no-op
//        }

        /**
         * Return <code>input</code> with no transformation.
         *
         * @see com.anji.nn.activationfunction.ActivationFunction#apply(double)
         */
        @Override
        public Double apply(double input) {
            return input;
        }

        @Override
        public Double applyDiff(double input) {
            return 1.;
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

    },


    /**
     * Logic AND activation function.
     *
     * @author Oliver Coleman
     */
    LogicAndActivationFunction /* extends;

	LogicActivationFunction */ {

        /**
         * identifying string
         */
        public final String NAME = "and";

        /**
         * @return
         * @see Object#toString()
         */
        @Override
        public String getName() {
            return NAME;
        }

        /**
         * This class should only be accessed via ActivationFunctionFactory.
         */
//		LogicAndActivationFunction() {
//		}

        /**
         * Returns the result of a logical AND over all inputs, where an input value
         * greater than or equal to 0.5 is considered logical true, and less than
         * 0.5 false.
         *
         * @param input
         * @param bias
         * @return 1 or 0 depending on result of logic operation.
         */
        @Override
        public Double apply(double[] input, double bias) {
            boolean result = false;
            for (int i = 0; i < input.length; i++) {
                result &= input[i] >= 0.5;
            }
            return Double.valueOf(result ? 1 : 0);
        }

        @Override
        public double getMaxValue() {
            return 1.;
        }

        @Override
        public double getMinValue() {
            return 0.;
        }


        @Override
        public long cost() {
            return 42;
        }
    },


    /**
     * Logic OR activation function.
     *
     * @author Oliver Coleman
     */
    LogicOrActivationFunction /*extends LogicActivationFunction */ {

        /**
         * identifying string
         */
        public final String NAME = "or";

        /**
         * @see Object#toString()
         */
        public String getName() {
            return NAME;
        }

        /**
         * This class should only be accessed via ActivationFunctionFactory.
         */
//		LogicOrActivationFunction() {
//		}

        /**
         * Returns the result of a logical OR over all inputs, where an input value
         * greater than or equal to 0.5 is considered logical true, and less than
         * 0.5 false.
         *
         * @return 1 or 0 depending on result of logic operation.
         */
        public Double apply(double[] input, double bias) {
            boolean result = false;
            for (int i = 0; i < input.length; i++) {
                result |= input[i] >= 0.5;
            }
            return Double.valueOf(result ? 1 : 0);
        }

        @Override
        public double getMaxValue() {
            return 1.;
        }

        @Override
        public double getMinValue() {
            return 0.;
        }

        @Override
        public long cost() {
            return 42;
        }

    },


    /**
     * Logic XOR activation function.
     *
     * @author Oliver Coleman
     */
    LogicXORActivationFunction /*extends LogicActivationFunction */ {

        /**
         * identifying string
         */
        public final String NAME = "xor";

        /**
         * @see Object#toString()
         */
        public String getName() {
            return NAME;
        }

        @Override
        public double getMaxValue() {
            return 1.;
        }

        @Override
        public double getMinValue() {
            return 0.;
        }

        @Override
        public long cost() {
            return 42;
        }

        /**
         * This class should only be accessed via ActivationFunctionFactory.
         */
//        LogicXORActivationFunction() {
//        }

        /**
         * Returns the result of a logical XOR over all inputs, where an input value
         * greater than or equal to 0.5 is considered logical true, and less than
         * 0.5 false.
         *
         * @return 1 or 0 depending on result of logic operation.
         */
        public Double apply(double[] input, double bias) {
            boolean result = false;
            for (int i = 0; i < input.length; i++) {
                result ^= input[i] >= 0.5;
            }
            return Double.valueOf(result ? 1 : 0);
        }
    },


    /**
     * Multiply activation function.
     *
     * @author Oliver Coleman
     */
    MultiplyActivationFunction /*implements ActivationFunction, ActivationFunctionNonIntegrating*/ {

        /**
         * identifying string
         */
        public final String NAME = "multiply";

        /**
         * @see Object#toString()
         */
        public String getName() {
            return NAME;
        }

        /**
         * This class should only be accessd via ActivationFunctionFactory.
         */
//		MultiplyActivationFunction() {
//		 no-op
//		}

        /**
         * Not used, use {@link #apply(double[], double)} as this is a
         * non-integrating function.
         */
        public Double apply(double input) {
            return 0.;
        }

        /**
         * Return result of inputs multiplied together.
         */
        public Double apply(double[] input, double bias) {
            if (input.length == 0) {
                return 0.;
            }
            double result = input[0];
            for (int i = 1; i < input.length; i++) {
                result *= input[i];
            }
            return result;
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
            return 42;
        }
    },


    /**
     * Negative linear activation function.
     */
    NegatedLinearActivationFunction
            /*implements ActivationFunction, DifferentiableFunction*/ {

        /**
         * identifying string
         */
        public final String NAME = "negated-linear";

        /**
         * @return
         * @see Object#toString()
         */
        @Override
        public String getName() {
            return NAME;
        }

        /**
         * This class should only be accessd via ActivationFunctionFactory.
         */
//		NegatedLinearActivationFunction() {
//		// no-op
//		}

        /**
         * Return <code>input</code> with opposite sign.
         *
         * @see com.anji.nn.activationfunction.ActivationFunction#apply(double)
         */
        @Override
        public Double apply(double input) {
            return -input;
        }

        @Override
        public Double applyDiff(double x) {
            return -1.;
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
    },


    /**
     * Square-root function.
     *
     * @author Oliver Coleman
     */
    PowerActivationFunction /*implements ActivationFunction, ActivationFunctionNonIntegrating*/ {

        /**
         * identifying string
         */
        public final String NAME = "power";

        /**
         * @return
         * @see Object#toString()
         */
        @Override
        public String getName() {
            return NAME;
        }

        /**
         * This class should only be accessd via ActivationFunctionFactory.
         */
//		PowerActivationFunction() {
//		// no-op
//		}

        /**
         * Not used, returns 0.
         */
        @Override
        public Double apply(double input) {
            return 0.;
        }

        /**
         * Return first input raised to the power of the absolute value of the
         * second input (or just first input if no second input).
         */
        @Override
        public Double apply(double[] input, double bias) {
            if (input.length < 2) {
                return input[0];
            }
            double v = Math.pow(input[0], Math.abs(input[1]));
            if (Double.isNaN(v)) {
                return 0.;
            }
            if (Double.isInfinite(v)) {
                return v < 0 ? -Double.MAX_VALUE / 2 : Double.MAX_VALUE / 2;
            }
            return v;
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
    },


    /**
     * Reciprocal function (inverse).
     *
     * @author Oliver Coleman
     */
    RecipriocalActivationFunction
            /*implements ActivationFunction, DifferentiableFunction */ {

        /**
         * identifying string
         */
        public final String NAME = "reciprocal";

        /**
         * @return
         * @see Object#toString()
         */
        @Override
        public String getName() {
            return NAME;
        }

        /**
         * This class should only be accessd via ActivationFunctionFactory.
         */
//		RecipriocalActivationFunction() {
//		// no-op
//		}

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#apply(double)
         */
        @Override
        public Double apply(double input) {
            double val = 1 / input;
            if (Double.isNaN(val)) {
                return input < 0 ? getMinValue() : getMaxValue();
            }
            if (val < getMinValue()) {
                val = getMinValue();
            } else if (val > getMaxValue()) {
                val = getMaxValue();
            }
            return val;
        }

        @Override
        public Double applyDiff(double x) {
            double val = -1 / (x * x);
            if (Double.isNaN(val)) {
                return getMinValue();
            }
            return val;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#getMaxValue()
         */
        @Override
        public double getMaxValue() {
            return Double.MAX_VALUE * 0.1;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#getMinValue()
         */
        @Override
        public double getMinValue() {
            return -getMaxValue();
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#cost()
         */
        @Override
        public long cost() {
            return 75;
        }
    },
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


    /**
     * Ramp activation function.
     *
     * @author Oliver Coleman
     * Edited by Christian Lins, 2020
     */
    RectifierActivationFunction /*implements ActivationFunction, DifferentiableFunction*/ {

        /**
         * identifying string
         */
        public final String NAME = "relu";

        /**
         * @return
         * @see Object#toString()
         */
        @Override
        public String getName() {
            return NAME;
        }

        /**
         * This class should only be accessd via ActivationFunctionFactory.
         */
//        RectifierActivationFunction() {
//            // no-op
//        }

        /**
         * Returns 0 if the input <= 0, otherwise the input value.
         *
         * @see com.anji.nn.activationfunction.ActivationFunction#apply(double)
         */
        @Override
        public Double apply(double x) {
            return Math.max(0, x);
        }

        @Override
        public Double applyDiff(double x) {
            if (x < 0) {
                return 0.;
            } else {
                return 1.;
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
            return 0.;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#cost()
         */
        @Override
        public long cost() {
            return 42;
        }

    },
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


    /**
     * Classic Sigmoid.
     *
     * @author Philip Tucker
     */
    SigmoidActivationFunction /*implements ActivationFunction, DifferentiableFunction*/ {

        /**
         * identifying string
         */
        public final String NAME = "sigmoid";

        /**
         * @return
         * @see Object#toString()
         */
        @Override
        public String getName() {
            return NAME;
        }

        /**
         * This class should only be accessed via ActivationFunctionFactory.
         */
//        SigmoidActivationFunction() {
//        }
        @Override
        public Double apply(double input) {
            return 1.0 / (1.0 + Math.exp(-input));
        }

        @Override
        public Double applyDiff(double input) {
            double fn = apply(input);
            return fn * (1 - fn);
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#getMaxValue()
         */
        @Override
        public double getMaxValue() {
            return 1.;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#getMinValue()
         */
        @Override
        public double getMinValue() {
            return 0.;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#cost()
         */
        @Override
        public long cost() {
            return 497;
        }
    },
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


    /**
     * @author Philip Tucker
     */
    SignedClampedLinearActivationFunction
            /*implements ActivationFunction, DifferentiableFunction*/ {

        /**
         * id string
         */
        public final String NAME = "signed-clamped-linear";

        /**
         * @return
         * @see java.lang.Object#toString()
         */
        @Override
        public String getName() {
            return NAME;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#apply(double)
         */
        @Override
        public Double apply(double input) {
            if (input <= -1.0) {
                return -1.;
            } else if (input >= 1.0) {
                return 1.;
            } else {
                return input;
            }
        }

        @Override
        public Double applyDiff(double x) {
            if (x <= 1.0 || x >= 1.0) {
                return 0.;
            } else {
                return 1.;
            }
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#getMaxValue()
         */
        @Override
        public double getMaxValue() {
            return 1.;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#getMinValue()
         */
        @Override
        public double getMinValue() {
            return -1.;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#cost()
         */
        @Override
        public long cost() {
            return 42;
        }

    },
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


    /**
     * Step activation function.
     *
     * @author Philip Tucker
     */
    SignedStepActivationFunction
            /*implements ActivationFunction, DifferentiableFunction*/ {

        /**
         * identifying string
         */
        public final String NAME = "signed-step";

        /**
         * @return
         * @see Object#toString()
         */
        @Override
        public String getName() {
            return NAME;
        }

        /**
         * This class should only be accessd via ActivationFunctionFactory.
         */
//        SignedStepActivationFunction() {
//            // no-op
//        }

        /**
         * @return -1 if <code>input</code>< 0, 1 otherwise @see com.an
         * ji.nn.activationfunction.ActivationFunction#apply(double)
         */
        @Override
        public Double apply(double input) {
            return Double.valueOf((input <= 0) ? -1 : 1);
        }

        @Override
        public Double applyDiff(double x) {
            return 0.;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#getMaxValue()
         */
        @Override
        public double getMaxValue() {
            return 1.;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#getMinValue()
         */
        @Override
        public double getMinValue() {
            return -1.;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#cost()
         */
        @Override
        public long cost() {
            return 40;
        }
    },
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


    /**
     * Sine activation function.
     *
     * @author Philip Tucker
     */
    SineActivationFunction
            /*implements ActivationFunction, DifferentiableFunction*/ {

        /**
         * identifying string
         */
        public final String NAME = "sine";

        /**
         * @return
         * @see Object#toString()
         */
        @Override
        public String getName() {
            return NAME;
        }

        /**
         * This class should only be accessd via ActivationFunctionFactory.
         */
//        SineActivationFunction() {
//            // no-op
//        }

        /**
         * Returns sine(input).
         */
        @Override
        public Double apply(double input) {
            return Math.sin(input);
        }

        @Override
        public Double applyDiff(double x) {
            return Math.cos(x);
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#getMaxValue()
         */
        @Override
        public double getMaxValue() {
            return 1.;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#getMinValue()
         */
        @Override
        public double getMinValue() {
            return -1.;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#cost()
         */
        @Override
        public long cost() {
            return 42;
        }
    },


    /**
     * Square-root function.
     *
     * @author Oliver Coleman
     */
    SqrtActivationFunction
            /*implements ActivationFunction, DifferentiableFunction*/ {

        /**
         * identifying string
         */
        public final String NAME = "sqrt";

        /**
         * @return
         * @see Object#toString()
         */
        @Override
        public String getName() {
            return NAME;
        }

        /**
         * This class should only be accessd via ActivationFunctionFactory.
         */
//        SqrtActivationFunction() {
//            // no-op
//        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#apply(double)
         */
        @Override
        public Double apply(double input) {
            if (input > 0) {
                return Math.sqrt(input);
            }
            if (input < 0) {
                return -Math.sqrt(-input);
            }
            return 0.;
        }

        @Override
        public Double applyDiff(double x) {
            if (x > 0) {
                return 1 / 2 * Math.sqrt(x);
            } else if (x < 0) {
                return 1 / 2 * Math.sqrt(-x);
            } else
                return 0.;
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
    },


    /**
     * Square-root function for values with magnitude > 1, otherwise linear.
     *
     * @author Oliver Coleman
     */
    SqrtAndLinearActivationFunction
            /*implements ActivationFunction, DifferentiableFunction*/ {

        /**
         * identifying string
         */
        public final String NAME = "sqrt-linear";

        /**
         * @see Object#toString()
         */
        @Override
        public String getName() {
            return NAME;
        }

        /**
         * This class should only be accessd via ActivationFunctionFactory.
         */
//        SqrtAndLinearActivationFunction() {
//            // no-op
//        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#apply(double)
         */
        @Override
        public Double apply(double input) {
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
        public Double applyDiff(double x) {
            if (x >= -1 && x <= 1) {
                return 1.;
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
    },
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


    /**
     * Steepened Sigmoid with slope of 4.9.
     *
     * @author Philip Tucker
     */
    SteepSigmoidActivationFunction
            /*implements ActivationFunction, DifferentiableFunction*/ {

        private final double SLOPE = 4.9;

        /**
         * identifying string
         */
        public final String NAME = "sigmoid-steep";

        /**
         * @see Object#toString()
         */
        public String getName() {
            return NAME;
        }

        /**
         * This class should only be accessed via ActivationFunctionFactory.
         */
//        SteepSigmoidActivationFunction() {
//        }
        @Override
        public Double apply(double input) {
            return 1.0 / (1.0 + Math.exp(-(input * SLOPE)));
        }

        @Override
        public Double applyDiff(double x) {
            return (SLOPE * Math.exp(-SLOPE * x)) / Math.pow(1 + Math.exp(-SLOPE * x), 2);
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#getMaxValue()
         */
        @Override
        public double getMaxValue() {
            return 1.;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#getMinValue()
         */
        @Override
        public double getMinValue() {
            return 0.;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#cost()
         */
        public long cost() {
            return 497;
        }
    },
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


    /**
     * Step activation function.
     *
     * @author Philip Tucker
     */
    StepActivationFunction
            /*implements ActivationFunction, DifferentiableFunction*/ {

        /**
         * identifying string
         */
        public final String NAME = "step";

        /**
         * @see Object#toString()
         */
        @Override
        public String getName() {
            return NAME;
        }

        /**
         * This class should only be accessd via ActivationFunctionFactory.
         */
//        StepActivationFunction() {
//            // no-op
//        }

        /**
         * @return 0. if <code>input</code>< 0, 1 otherwise @see com.an
         * ji.nn.activationfunction.ActivationFunction#apply(double)
         */
        @Override
        public Double apply(double input) {
            return Double.valueOf((input <= 0) ? 0 : 1);
        }

        @Override
        public Double applyDiff(double x) {
            return 0.;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#getMaxValue()
         */
        public double getMaxValue() {
            return 1.;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#getMinValue()
         */
        public double getMinValue() {
            return 0.;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#cost()
         */
        public long cost() {
            return 40;
        }
    },
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


    /**
     * Hyperbolic tangent.
     *
     * @author Philip Tucker
     */
    TanhActivationFunction /*implements ActivationFunction, DifferentiableFunction*/ {

        /**
         * identifying string
         */
        public final String NAME = "tanh";

        /**
         * @return
         * @see Object#toString()
         */
        @Override
        public String getName() {
            return NAME;
        }

        /**
         * This class should only be accessd via ActivationFunctionFactory.
         */
//        TanhActivationFunction() {
//            // no-op
//        }

        /**
         * Hyperbolic tangent.
         *
         * @param x
         * @see com.anji.nn.activationfunction.ActivationFunction#apply(double)
         */
        @Override
        public Double apply(double x) {
            return -1 + (2 / (1 + Math.exp(-2 * (x))));
        }

        @Override
        public Double applyDiff(double x) {
            return 0.5 * Math.log((1 + x) / (1 - x));
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#getMaxValue()
         */
        public double getMaxValue() {
            return 1.;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#getMinValue()
         */
        public double getMinValue() {
            return -1.;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#cost()
         */
        public long cost() {
            return 385;
        }

    },
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


    /**
     * Hyperbolic tangent modified to have a "well" around 0. This can be used for
     * control neurons for which we would ilke the neural netowkr to be able easily
     * to rest at 0.
     *
     * @author Philip Tucker
     */
    TanhCubicActivationFunction
            /*implements ActivationFunction, DifferentiableFunction*/ {

        /**
         * identifying string
         */
        public final String NAME = "tanh-cubic";

        /**
         * @return
         * @see Object#toString()
         */
        @Override
        public String getName() {
            return NAME;
        }

        /**
         * This class should only be accessd via ActivationFunctionFactory.
         */
//        TanhCubicActivationFunction() {
//            // no-op
//        }

        /**
         * Hyperbolic tangent of cubic.
         *
         * @param x
         * @see com.anji.nn.activationfunction.ActivationFunction#apply(double)
         */
        @Override
        public Double apply(double x) {
            return -1 + (2 / (1 + Math.exp(Math.pow(-x, 3))));
        }

        @Override
        public Double applyDiff(double x) {
            // As given by Wolfram Alpha
            return (6 * Math.exp(-Math.pow(x, 3)) * Math.pow(x, 2)) /
                    Math.pow(1 + Math.exp(Math.pow(-x, 3)), 2);
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#getMaxValue()
         */
        @Override
        public double getMaxValue() {
            return 1.;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#getMinValue()
         */
        @Override
        public double getMinValue() {
            return -1.;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#cost()
         */
        public long cost() {
            return 1231;
        }
    },
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


    /**
     * Modified classic sigmoid. Copied from
     * <a href="http://www.jooneworld.com/">JOONE</a> <code>SigmoidLayer</code>.
     *
     * @author Philip Tucker
     */
    BipolarSigmoidActivationFunction
            /*implements ActivationFunction */ {

        private final double SLOPE = 2;

        /**
         * identifying string
         */
        public final String NAME = "sigmoid-bipolar";

        /**
         * @return
         * @see Object#toString()
         */
        @Override
        public String getName() {
            return NAME;
        }

        /**
         * This class should only be accessd via ActivationFunctionFactory.
         */
//        BipolarSigmoidActivationFunction() {
//            // no-op
//        }

        /**
         * Modified classic sigmoid.
         *
         * @see com.anji.nn.activationfunction.ActivationFunction#apply(double)
         */
        @Override
        public Double apply(double input) {
            return 2.0 / (1.0 + Math.exp(-(input * SLOPE))) - 1.0;
        }

        public Double applyDiff(double x) {
            // As given by Wolfram Alpha
            return (2.0 * Math.exp(-SLOPE * x) * SLOPE) / Math.pow(1 + Math.exp(-SLOPE * x), 2);
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#getMaxValue()
         */
        public double getMaxValue() {
            return 1.;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#getMinValue()
         */
        public double getMinValue() {
            return -1.;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#cost()
         */
        public long cost() {
            return 497;
        }
    },
    /*
     * Copyright (C) 2004 Oliver Coleman
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
     */


    /**
     * Absolute activation function.
     *
     * @author Oliver Coleman
     */
    ClampedAbsoluteActivationFunction
            /*implements ActivationFunction, DifferentiableFunction*/ {

        /**
         * identifying string
         */
        public final String NAME = "clamped-absolute";

        /**
         * @see Object#toString()
         */
        public String getName() {
            return NAME;
        }

        /**
         * This class should only be accessd via ActivationFunctionFactory.
         */
//        ClampedAbsoluteActivationFunction() {
//            // no-op
//        }

        /**
         * Return absolute value of <code>input</code>, clamped to range [0, 1].
         *
         * @see com.anji.nn.activationfunction.ActivationFunction#apply(double)
         */
        @Override
        public Double apply(double input) {
            return Math.min(Math.abs(input), 1);
        }

        @Override
        public Double applyDiff(double x) {
            if (x < 0) {
                if (x >= -1) {
                    return -1.;
                } else {
                    return 0.;
                }
            } else {
                // we ignore x == 0 here
                if (x >= 1) {
                    return 1.;
                } else {
                    return 0.;
                }
            }
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#getMaxValue()
         */
        @Override
        public double getMaxValue() {
            return 1.;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#getMinValue()
         */
        public double getMinValue() {
            return 0.;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#cost()
         */
        public long cost() {
            return 42;
        }
    },
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


    /**
     * @author Philip Tucker
     */
    ClampedLinearActivationFunction
            /*implements ActivationFunction, DifferentiableFunction */ {

        /**
         * unique ID string
         */
        public final String NAME = "clamped-linear";

        /**
         * @return
         * @see java.lang.Object#toString()
         */
        @Override
        public String getName() {
            return NAME;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#apply(double)
         */
        @Override
        public Double apply(double input) {
            if (input <= 0) {
                return 0.;
            } else if (input >= 1) {
                return 1.;
            } else {
                return input;
            }
        }

        @Override
        public Double applyDiff(double x) {
            if (x <= 0 || x >= 1) {
                return 0.;
            } else {
                return 1.;
            }
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#getMaxValue()
         */
        @Override
        public double getMaxValue() {
            return 1.;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#getMinValue()
         */
        @Override
        public double getMinValue() {
            return 0.;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#cost()
         */
        public long cost() {
            return 42;
        }

    },


    /**
     * @author Oliver Coleman
     */
    ConvertToSignedActivationFunction
            /*implements ActivationFunction, DifferentiableFunction*/ {

        /**
         * unique ID string
         */
        public final String NAME = "sign";

        /**
         * @return
         * @see java.lang.Object#toString()
         */
        @Override
        public String getName() {
            return NAME;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#apply(double)
         */
        @Override
        public Double apply(double input) {
            if (input <= 0) {
                input = 0;
            } else if (input >= 1) {
                input = 1;
            }
            return (input * 2) - 1;
        }

        @Override
        public Double applyDiff(double x) {
            if (x <= 0 || x >= 1) {
                return 0.;
            } else {
                return 2.;
            }
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#getMaxValue()
         */
        @Override
        public double getMaxValue() {
            return 1.;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#getMinValue()
         */
        @Override
        public double getMinValue() {
            return -1.;
        }

        /**
         * @see com.anji.nn.activationfunction.ActivationFunction#cost()
         */
        @Override
        public long cost() {
            return 42;
        }

    }, LogicActivationFunction /*extends ActivationFunction implements ActivationFunctionNonIntegrating */ {
        /**
         * Not used as this is a non-integrating function, returns 0.
         *
         * @see #apply(double[], double)
         */
        @Override
        public Double apply(double input) {
            return 0.;
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

    };

    public final String NAME;

    ActivationFunction() {
        this.NAME = getName();
    }

    /**
     * Apply activation function to input.
     *
     * @param input
     * @return double result of applying activation function to <code>input</code>
     */
    public Double apply(double input) {
        return null;
    }

    public Double apply(double[] input, double bias) {
        return null;
    }

    public Double applyDiff(double x) {
        return apply(x);
    }

    /**
     * @return ceiling value for this function
     */
    abstract public double getMaxValue();

    /**
     * @return floor value for this function
     */
    abstract public double getMinValue();

    /**
     * @return number corresponding to cost of activation in resources
     */
    abstract public long cost();

    public String getName() {
        return name();
    }
}
