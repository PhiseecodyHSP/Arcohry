package io.github.phiseecodyhsp.demo;

import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class TransitionAnimation extends StackPane {
    private static final double TT_TIME = 0.25;

    private final ImageView l = new ImageView(Resources.TrAnL);
    private final ImageView r = new ImageView(Resources.TrAnR);
    private final TranslateTransition onLAdded = new TranslateTransition(Duration.seconds(TT_TIME), l);
    private final TranslateTransition onRAdded = new TranslateTransition(Duration.seconds(TT_TIME), r);
    private final TranslateTransition onLRemoved = new TranslateTransition(Duration.seconds(TT_TIME), l);
    private final TranslateTransition onRRemoved = new TranslateTransition(Duration.seconds(TT_TIME), r);

    public TransitionAnimation() {
        onLAdded.setOnFinished(_ -> {
            onLAdded.stop();
            onRAdded.stop();
            onLRemoved.playFromStart();
            onRRemoved.playFromStart();
        });
        onLRemoved.setOnFinished(_ -> getParentStackPane().getChildren().remove(this));

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

        getChildren().addAll(l, r);
    }

    private StackPane getParentStackPane() {
        return Util.getDesignatedParent(this, StackPane.class);
    }

    private void updateScale(Scene scene) {
        double scale = Math.min(scene.getWidth() / Util.getScreenWidth(), scene.getHeight() / Util.getScreenHeight());
        this.setScaleX(scale);
        this.setScaleY(scale);
    }

    public void play(StackPane stackPane) {
        stackPane.getChildren().add(this);
        onLRemoved.stop();
        onRRemoved.stop();
        onLAdded.playFromStart();
        onRAdded.playFromStart();
    }
}
