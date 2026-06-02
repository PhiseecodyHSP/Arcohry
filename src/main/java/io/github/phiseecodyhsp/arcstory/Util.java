package io.github.phiseecodyhsp.arcstory;

import javafx.animation.Interpolator;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;

public final class Util {
    private Util() {}

    public static final double SQRT_2;
    public static final double SQRT_3;

    public static final Interpolator EASE_IN;
    public static final Interpolator EASE_OUT;

    static {
        SQRT_2 = Math.sqrt(2);
        SQRT_3 = Math.sqrt(3);

        EASE_IN = new Interpolator() {
            @Override
            protected double curve(double v) {
                return 1 - (1 - v) * (1 - v) * (1 - v);
            }
        };
        EASE_OUT = new Interpolator() {
            @Override
            protected double curve(double v) {
                return v * v * v;
            }
        };
    }

    public static int doubleToEven(double d) {
        int i;
        if (d < 0) {
            i = (int) d - 1;
            return i % 2 == 0 ? i : i - 1;
        }
        if (d == 0) {
            return 0;
        } else {
            i = (int) d + 1;
            return i % 2 == 0 ? i : i + 1;
        }
    }

    public static double getScreenWidth() {
        return Screen.getPrimary().getBounds().getWidth();
    }

    public static double getScreenHeight() {
        return Screen.getPrimary().getBounds().getHeight();
    }

    public static String intToRoman(int num) {
        if (num == 0) {
            return "N";
        }

        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuilder roman = new StringBuilder();
        if (num < 0) {
            roman.append("-");
            num = -num;
        }
        for (int i = 0; i < values.length; i++) {
            while (num >= values[i]) {
                roman.append(symbols[i]);
                num -= values[i];
            }
        }
        System.out.println(new Object());
        return roman.toString();
    }

    public static SetStage getSetStage(Scene scene) {
        if (scene.getWindow() instanceof SetStage stage) {
            return stage;
        }
        throw new IllegalStateException();
    }

    public static SetStage getSetStage(Node node) {
        if (node.getScene() != null) {
            return getSetStage(node.getScene());
        }
        throw new IllegalStateException();
    }

    public static void addParentChecker(Node node, Parent parent) {
        node.parentProperty().addListener((_, _, p) -> {
            if (p != parent) {
                throw new IllegalStateException(node + "'s parent must be " + parent + ", but found " + p);
            }
        });
    }
}
