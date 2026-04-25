package io.github.phiseecodyhsp.demo;

import javafx.stage.Screen;

public final class Util {
    private Util() {}

    public static double getDpi() {
        return Screen.getPrimary().getDpi();
    }

    public static double getScreenWidth() {
        return Screen.getPrimary().getBounds().getWidth();
    }

    public static double getScreenHeight() {
        return Screen.getPrimary().getBounds().getHeight();
    }

    public static int nextEven(double d) {
        int i = (int) d + 1;
        if (i % 2 == 0) {
            return i;
        }
        return i + 1;
    }

    public static int px2FontSize(int px) {
        return (int) (px * 72.0 / getDpi()) + 1;
    }
}
