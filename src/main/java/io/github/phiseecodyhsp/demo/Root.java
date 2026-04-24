package io.github.phiseecodyhsp.demo;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

public class Root extends StackPane {
    public Root() {
        List<Scene> oldScenes = new ArrayList<>();
        sceneProperty().addListener((_, _, scene) -> {
            if (scene != null) {
                updateScale(scene);
                if (!oldScenes.contains(scene)) {
                    oldScenes.add(scene);
                    scene.widthProperty().addListener(
                            (_, _, _) -> updateScale(scene));
                    scene.heightProperty().addListener(
                            (_, _, _) -> updateScale(scene));
                }
            }
        });
    }

    private void updateScale(Scene scene) {
        double scale = Math.max(scene.getWidth() / Util.getScreenWidth(),
                scene.getHeight() / (Util.getScreenHeight()));
        setScaleX(scale);
        setScaleY(scale);
    }
}
