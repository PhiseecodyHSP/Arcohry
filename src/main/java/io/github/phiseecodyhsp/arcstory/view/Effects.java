package io.github.phiseecodyhsp.arcstory.view;

import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

public class Effects {

    /**
     * 向右下偏移的投射阴影.
     */
    public static final DropShadow SHADOW = new DropShadow(10.0D, 10.0D, 10.0D, new Color(0, 0, 0, 0.5));

    /**
     * 按钮背景阴影. 由于按钮被旋转了 45 度, 这里需要单独写一个阴影.
     */
    public static final DropShadow ROTATED_SHADOW = new DropShadow(15.0D, 10.0D, 3.0D, new Color(0, 0, 0, 0.5));

    /**
     * 外发光.
     */
    public static final DropShadow OUTER_GLOW = new DropShadow(10.0D, Color.WHITE);
}
