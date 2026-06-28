package io.github.phiseecodyhsp.arcstory.ui.util;

import javafx.animation.Interpolator;

public class Interpolators {

    /**
     * 由快至慢的三次缓动.
     */
    public static final Interpolator CUBE_IN = new Interpolator() {
        @Override
        protected double curve(double v) {
            double d = 1.0D - v;
            return 1.0D - d * d * d;
        }
    };

    /**
     * 由慢至快的三次缓动.
     */
    public static final Interpolator CUBE_OUT = new Interpolator() {
        @Override
        protected double curve(double v) {
            return v * v * v;
        }
    };
}
