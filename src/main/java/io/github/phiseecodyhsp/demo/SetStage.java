package io.github.phiseecodyhsp.demo;

import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

public class SetStage extends Stage {
    private static final int WIDTH = 640;
    private static final int HEIGHT = WIDTH / 16 * 9;

    private final StackPane root = new StackPane();
    private final Scene scene = new Scene(root);
    private final TransitionAnimation anima = new TransitionAnimation();

    public SetStage(StackPane initialPane) {
        root.getChildren().add(initialPane);

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.F11) {
                setFullScreen(!isFullScreen());
            }
        });
        scene.widthProperty().addListener(_ -> updateScale());
        scene.heightProperty().addListener(_ -> updateScale());

        setWidth(WIDTH);
        setHeight(HEIGHT);
        setScene(scene);
        show();
    }

    private void updateScale() {
        double scale = Math.max(scene.getWidth() / Util.getScreenWidth(),
                scene.getHeight() / (Util.getScreenHeight()));
        root.setScaleX(scale);
        root.setScaleY(scale);
    }

    public void switchPane(TransAnimaType type, StackPane newPane) {
        anima.play(type);
        root.getChildren().set(0, newPane);
    }

    private class TransitionAnimation extends StackPane {
        private static final double TT_TIME = 3;

        private final ImageView l = new ImageView();
        private final ImageView r = new ImageView();
        private final TranslateTransition onLAdded = new TranslateTransition(Duration.seconds(TT_TIME), l);
        private final TranslateTransition onRAdded = new TranslateTransition(Duration.seconds(TT_TIME), r);
        private final TranslateTransition onLRemoved = new TranslateTransition(Duration.seconds(TT_TIME), l);
        private final TranslateTransition onRRemoved = new TranslateTransition(Duration.seconds(TT_TIME), r);

        private TransitionAnimation() {
            getChildren().addAll(l, r);
        }

        private void play(@NotNull SetStage.TransAnimaType type) {
            switch (type) {
                case NORMAL -> {
                    l.setImage(new Image(Resources.TrAnL));
                    r.setImage(new Image(Resources.TrAnR));
                }
            }

            root.getChildren().add(this);
            onLAdded.setOnFinished(_ -> {
                onLAdded.stop();
                onRAdded.stop();
                onLRemoved.playFromStart();
                onRRemoved.playFromStart();
            });
            onLRemoved.setOnFinished(_ -> root.getChildren().remove(this));
            onLRemoved.stop();
            onRRemoved.stop();
            onLAdded.playFromStart();
            onRAdded.playFromStart();
        }
    }

    public enum TransAnimaType {
        NORMAL,
        GRIEVOUS,
        FRACTURE,
        FINAL,
        ARGHENA,
        ALTER,
        DESIGNANT
    }
}
