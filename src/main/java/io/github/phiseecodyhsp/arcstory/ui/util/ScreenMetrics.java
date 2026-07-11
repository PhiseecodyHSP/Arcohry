package io.github.phiseecodyhsp.arcstory.ui.util;

import io.github.phiseecodyhsp.arcstory.ui.base.AppWindow;

/**
 * 存储屏幕宽高常量的类.
 *
 * @author HSP
 */
public final class ScreenMetrics {

    private ScreenMetrics() {}

    /**
     * 基准宽高比.
     */
    private static final double ASPECT_RATIO;

    /**
     * 基准屏幕宽度, 根据这个常量来设定 {@link AppWindow} 中的 updateScale() 方法和需要铺满整个窗口的 Node 的大小等.
     */
    public static final double SCREEN_WIDTH;

    /**
     * 基准屏幕高度, 作用见上.
     */
    public static final double SCREEN_HEIGHT;

    static {
        ASPECT_RATIO = 16.0 / 9.0;

        SCREEN_WIDTH = 1920.0D;

        SCREEN_HEIGHT = SCREEN_WIDTH / ASPECT_RATIO;
    }
}
