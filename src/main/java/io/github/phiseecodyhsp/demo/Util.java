package io.github.phiseecodyhsp.demo;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
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

    public static double px2FontSize(double px) {
        return (px * 72.0 / getDpi()) + 1;
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
