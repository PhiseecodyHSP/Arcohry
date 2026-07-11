package io.github.phiseecodyhsp.arcstory.view;

import io.github.phiseecodyhsp.arcstory.ui.util.ScreenMetrics;
import io.github.phiseecodyhsp.arcstory.util.MathUtil;

public final class StoryNodeUiConstants {

    private StoryNodeUiConstants() {}

    public static final double SIDE_LENGTH;
    public static final double DIAGONAL_LENGTH;
    public static final double BORDER_WIDTH;
    public static final double BORDER_ARC_SIZE;

    static {
        DIAGONAL_LENGTH = SIDE_LENGTH * MathUtil.SQRT_2;
        BORDER_WIDTH = 2.0D;
        BORDER_ARC_SIZE = 5.0D;
    }
}
