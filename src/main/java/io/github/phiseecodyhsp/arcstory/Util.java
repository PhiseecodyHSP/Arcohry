package io.github.phiseecodyhsp.arcstory;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;

public final class Util {
    private Util() {}

    public static final double SQRT_2;
    public static final double SQRT_3;

    static {
        SQRT_2 = Math.sqrt(2);
        SQRT_3 = Math.sqrt(3);
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

    public static SetStage getSetStage(Scene scene) {
        if (scene.getWindow() instanceof SetStage stage) {
            return stage;
        }
        throw new IllegalStateException();
    }

    public static SetStage getSetStage(StackPane pane) {
        if (pane.getScene() != null) {
            return getSetStage(pane.getScene());
        }
        throw new IllegalStateException();
    }
}
