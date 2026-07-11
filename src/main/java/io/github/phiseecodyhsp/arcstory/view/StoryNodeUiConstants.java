package io.github.phiseecodyhsp.arcstory.view;

import io.github.phiseecodyhsp.arcstory.ui.screen.StoryScreen;
import io.github.phiseecodyhsp.arcstory.ui.util.ScreenMetrics;
import io.github.phiseecodyhsp.arcstory.util.MathUtil;
import io.github.phiseecodyhsp.arcstory.view.node.StoryNode;

/**
 * 存储用于计算 {@link StoryScreen} 中 Node 的形态, 排版等的常量的类.
 *
 * @author HSP
 */
public final class StoryNodeUiConstants {

    private StoryNodeUiConstants() {}

    /**
     * {@link StoryNode} 的边长.
     */
    public static final double SIDE_LENGTH;

    /**
     * {@link StoryNode} 的对角线长.
     */
    public static final double DIAGONAL_LENGTH;

    /**
     * {@link StoryNode} 的描边宽度.
     */
    public static final double BORDER_WIDTH;

    /**
     * {@link StoryNode} 的边框圆角程度.
     */
    public static final double BORDER_ARC_SIZE;

    /**
     * {@link TextPlayer} 的字体大小.
     */
    public static final double TEXT_PLAYER_FONT_SIZE;

    static {
        SIDE_LENGTH = ScreenMetrics.SCREEN_WIDTH * 0.05D;

        DIAGONAL_LENGTH = SIDE_LENGTH * MathUtil.SQRT_2;

        BORDER_WIDTH = 2.0D;

        BORDER_ARC_SIZE = 5.0D;

        TEXT_PLAYER_FONT_SIZE = DIAGONAL_LENGTH / 5.0D;
    }
}
