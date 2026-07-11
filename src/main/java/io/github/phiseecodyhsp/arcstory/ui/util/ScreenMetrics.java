package io.github.phiseecodyhsp.arcstory.ui.util;

public final class ScreenMetrics {

    private ScreenMetrics() {};

    private static final double ASPECT_RATIO;
    public static final double SCREEN_WIDTH;
    public static final double SCREEN_HEIGHT;

    static {
        ASPECT_RATIO = 16.0 / 9.0;
        SCREEN_WIDTH = 1920.0D;
        SCREEN_HEIGHT = SCREEN_WIDTH / ASPECT_RATIO;
    }
}
