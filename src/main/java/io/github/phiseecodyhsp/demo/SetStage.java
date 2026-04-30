package io.github.phiseecodyhsp.demo;

import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
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

    //TODO
    private class TransitionAnimation extends StackPane {
        private static final double TT_TIME = 3;

        private final ImageView left = new ImageView();
        private final ImageView right = new ImageView();
        private final ImageView illustrationView = new ImageView();
        private final Label musicName = new Label();
        private final Label music = new Label("Music");
        private final Label composer = new Label();
        private final Label illustration = new Label("Illustration");
        private final Label illustrator = new Label();
        private final Label noteDesign = new Label("Note Design");
        private final Label noteDesigner = new Label();

        private final TranslateTransition onLAdded = new TranslateTransition(Duration.seconds(TT_TIME), left);
        private final TranslateTransition onRAdded = new TranslateTransition(Duration.seconds(TT_TIME), right);
        private final TranslateTransition onLRemoved = new TranslateTransition(Duration.seconds(TT_TIME), left);
        private final TranslateTransition onRRemoved = new TranslateTransition(Duration.seconds(TT_TIME), right);

        private TransitionAnimation() {
            musicName.setTextFill(Color.WHITE);
            composer.setTextFill(Color.WHITE);
            illustrator.setTextFill(Color.WHITE);
            illustration.setTextFill(Color.WHITE);
            noteDesign.setTextFill(Color.WHITE);
            noteDesigner.setTextFill(Color.WHITE);
            musicName.setTextFill(Color.WHITE);
        }

        private void play(@NotNull SetStage.TransAnimaType type) {
            type.setImage(this);

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

            getChildren().clear();
            getChildren().addAll(left, right);
        }

        private void play(@NotNull SetStage.TransAnimaType type,
                          @NotNull String musicName,
                          @NotNull String composer,
                          String illustrator,
                          @NotNull String noteDesigner) {
            this.musicName.setText(musicName);
            this.composer.setText(composer);
            getChildren().clear();
            getChildren().addAll
                    (left,
                            right,
                            illustrationView,
                            this.musicName,
                            music,
                            this.composer,
                            noteDesign,
                            this.noteDesigner);
            this.noteDesigner.setText(noteDesigner);
            if (illustrator != null) {
                this.illustrator.setText(illustrator);
                getChildren().addAll(illustration, this.illustrator);
            }
            play(type);
        }

        private void play(@NotNull SetStage.TransAnimaType type, @NotNull Chart chart) {
            play(type, chart.music, chart.composer, chart.illustrator, chart.noteDesigner);
        }
    }

    //TODO: 素材替换
    public enum TransAnimaType {
        NORMAL(Resources.TrAnL, Resources.TrAnR),
        GRIEVOUS(Resources.TrAnL, Resources.TrAnR),
        FRACTURE(Resources.TrAnL, Resources.TrAnR),
        FINAL(Resources.TrAnL, Resources.TrAnR),
        ARGHENA(Resources.TrAnL, Resources.TrAnR),
        ALTER(Resources.TrAnL, Resources.TrAnR),
        DESIGNANT(Resources.TrAnL, Resources.TrAnR);

        private final String leftImagePath;
        private final String rightImagePath;

        TransAnimaType(String l, String r) {
            leftImagePath = l;
            rightImagePath = r;
        }

        private void setImage(TransitionAnimation anima) {
            anima.left.setImage(new Image(leftImagePath));
            anima.right.setImage(new Image(rightImagePath));
        }
    }
}
