package io.github.phiseecodyhsp.arcstory;

import io.github.phiseecodyhsp.arcstory.storyMode.StoryPlayer;
import io.github.phiseecodyhsp.arcstory.storyMode.StoryUnlockConditionDisplayer;
import javafx.animation.Interpolator;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Screen;

public final class Util {
    private Util() {}

    public static final double SQRT_2;
    public static final double SQRT_3;
    public static final double PRIMARY_SCREEN_WIDTH;
    public static final double PRIMARY_SCREEN_HEIGHT;

    public static final Interpolator EASE_IN;
    public static final Interpolator EASE_OUT;

    public static final StoryPlayer STORY_PLAYER;
    public static final StoryUnlockConditionDisplayer CONDITION_DISPLAYER;

    static {
        SQRT_2 = Math.sqrt(2);
        SQRT_3 = Math.sqrt(3);
        PRIMARY_SCREEN_WIDTH = Screen.getPrimary().getBounds().getWidth();
        PRIMARY_SCREEN_HEIGHT = Screen.getPrimary().getBounds().getHeight();

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

        STORY_PLAYER = new StoryPlayer();
        CONDITION_DISPLAYER = new StoryUnlockConditionDisplayer();
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

    private static SetStage getSetStage(Scene scene) {
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
}
