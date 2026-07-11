package io.github.phiseecodyhsp.arcstory.ui.util;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public class ScreenMetrics {

    /**
     * 获取主屏的宽高信息.
     *
     * <p>若同时获取宽高, 建议使用该方法, <b>因为这可以避免潜在的同步问题</b> (如分辨率更改).
     *
     * @return 搭载主屏宽高的 {@link Rectangle2D} 对象
     */
    public static Rectangle2D getPrimaryScreenBounds() {
        return Screen.getPrimary().getVisualBounds();
    }

    /**
     * 获取主屏的宽度.
     *
     * <p>若同时获取宽高, 建议使用 {@link #getPrimaryScreenBounds()}, 理由已经给出.
     *
     * @return 主屏宽度
     */
    public static double getPrimaryScreenWidth() {
        return Screen.getPrimary().getVisualBounds().getWidth();
    }

    /**
     * 获取主屏的高度.
     *
     * <p>若同时获取宽高, 建议使用 {@link #getPrimaryScreenBounds()}, 理由已经给出.
     *
     * @return 主屏高度
     */
    public static double getPrimaryScreenHeight() {
        return Screen.getPrimary().getVisualBounds().getHeight();
    }
}
