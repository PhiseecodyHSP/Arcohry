package io.github.phiseecodyhsp.demo;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

public class SetStage extends Stage {
    private static final double WIDTH = (int) Util.getScreenWidth() / 2.0;
    private static final int HEIGHT = (int) (WIDTH / 16 * 9);

    private StackPane lastPane;
    private StackPane currentPane;
    private final StackPane root = new StackPane();
    private final Scene scene = new Scene(root);
    private final TransitionAnimation anima = new TransitionAnimation();

    public SetStage(StackPane initialPane) {
        root.getChildren().add(initialPane);
        lastPane = null;
        currentPane = initialPane;

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
        anima.play(type, newPane);
        lastPane = currentPane;
        currentPane = newPane;
    }

    public void playChart(@NotNull TransAnimaType type,
                          String musicName,
                          String composer,
                          @NotNull String illustrationPath,
                          String illustrator,
                          String noteDesigner,
                          @NotNull Chart.Paradigms paradigms) {
        anima.play(type, musicName, composer,illustrationPath, illustrator, noteDesigner, paradigms);
    }

    public void playChart(@NotNull TransAnimaType type, @NotNull Chart chart) {
        playChart(type,
                chart.music,
                chart.composer,
                chart.illustrationPath,
                chart.illustrator,
                chart.noteDesigner,
                chart.paradigms);
    }

    //TODO
    private class TransitionAnimation extends StackPane {
        private static final double TRANS_TIME = 1;
        private static final double SHADOW_OPACITY = 0.5;
        private static final int LABEL_DISPLACEMENT = 0;
        private static final double HIGHEST_ILLUSTRATION_SCALE = 0;
        private static final double PARADIGMS_OPACITY = 0.5;

        private final ImageView left = new ImageView();
        private final ImageView right = new ImageView();
        private final ImageView illustrationView = new ImageView();
        private final ImageView musicNameShadow = new ImageView();
        private final Rectangle shadow = new Rectangle();
        private final Rectangle paradigms = new Rectangle();
        private final Label musicName = new Label();
        private final Label music = new Label("Music");
        private final Label composer = new Label();
        private final Label illustration = new Label("Illustration");
        private final Label illustrator = new Label();
        private final Label noteDesign = new Label("Note Design");
        private final Label noteDesigner = new Label();
        private final StackPane labelPane = new StackPane();
        private final StackPane pane = new StackPane(shadow, musicNameShadow, paradigms, illustrationView, labelPane);

        private final TranslateTransition onLAdded = new TranslateTransition(Duration.seconds(TRANS_TIME), left);
        private final TranslateTransition onRAdded = new TranslateTransition(Duration.seconds(TRANS_TIME), right);
        private final TranslateTransition onLRemoved = new TranslateTransition(Duration.seconds(TRANS_TIME), left);
        private final TranslateTransition onRRemoved = new TranslateTransition(Duration.seconds(TRANS_TIME), right);
        private final TranslateTransition onLabelPaneAdded =
                new TranslateTransition(Duration.seconds(TRANS_TIME), labelPane);
        private final ScaleTransition onIllustrationAdded =
                new ScaleTransition(Duration.seconds(TRANS_TIME), illustrationView);
        private final ScaleTransition onIllustrationRemoved =
                new ScaleTransition(Duration.seconds(TRANS_TIME), illustrationView);
        private final FadeTransition onPaneAdded = new FadeTransition(Duration.seconds(TRANS_TIME), pane);
        private final FadeTransition onPaneRemoved = new FadeTransition(Duration.seconds(TRANS_TIME), pane);


        private TransitionAnimation() {
            shadow.setFill(Color.BLACK);
            shadow.setOpacity(SHADOW_OPACITY);

            musicName.setTextFill(Color.WHITE);
            composer.setTextFill(Color.WHITE);
            illustrator.setTextFill(Color.WHITE);
            illustration.setTextFill(Color.WHITE);
            noteDesign.setTextFill(Color.WHITE);
            noteDesigner.setTextFill(Color.WHITE);
            musicName.setTextFill(Color.WHITE);
        }

        private void play(@NotNull SetStage.TransAnimaType type, StackPane newPane) {
            type.setImage(this);

            root.getChildren().add(this);
            onLAdded.setOnFinished(_ -> {
                onLAdded.stop();
                onRAdded.stop();
                onLRemoved.playFromStart();
                onRRemoved.playFromStart();
                Resources.TRANSANIMA_END_SOUND.play();
                root.getChildren().set(0, newPane);
            });
            onLRemoved.setOnFinished(_ -> root.getChildren().remove(this));
            onLRemoved.stop();
            onRRemoved.stop();
            onLAdded.playFromStart();
            onRAdded.playFromStart();
            Resources.TRANSANIMA_STRAT_SOUND.play();

            getChildren().clear();
            getChildren().addAll(left, right);
        }

        //TODO
        private void play(@NotNull SetStage.TransAnimaType type,
                          String musicName,
                          String composer,
                          @NotNull String illustrationPath,
                          String illustrator,
                          String noteDesigner,
                          @NotNull Chart.Paradigms paradigms) {
            type.setImage(this);

            this.musicName.setText(musicName);
            this.composer.setText(composer);
            illustrationView.setImage(new Image(illustrationPath));
            this.illustrator.setText(illustrator);
            paradigms.setParadigms(this.paradigms);

            root.getChildren().add(this);
            onLAdded.setOnFinished(_ -> {
                onLAdded.stop();
                onRAdded.stop();
                onLabelPaneAdded.stop();
                onIllustrationAdded.stop();
                onPaneAdded.stop();
                onLRemoved.playFromStart();
                onRRemoved.playFromStart();
                onIllustrationRemoved.playFromStart();
                onPaneRemoved.playFromStart();
                Resources.TRANSANIMA_END_SOUND.play();
            });
            onLRemoved.setOnFinished(_ -> root.getChildren().remove(this));
            onLRemoved.stop();
            onRRemoved.stop();
            onIllustrationRemoved.stop();
            onPaneRemoved.stop();
            onLAdded.playFromStart();
            onRAdded.playFromStart();
            onLabelPaneAdded.playFromStart();
            onIllustrationAdded.playFromStart();
            onPaneAdded.playFromStart();
            Resources.TRANSANIMA_STRAT_SOUND.play();

            labelPane.getChildren().clear();
            labelPane.getChildren().addAll(
                    this.musicName,
                    music,
                    this.composer,
                    noteDesign,
                    this.noteDesigner);
            if (illustrator != null) {
                labelPane.getChildren().addAll(illustration, this.illustrator);
            }

            getChildren().clear();
            getChildren().addAll(
                    left,
                    right,
                    pane);
            this.noteDesigner.setText(noteDesigner);
        }

        private void play(@NotNull SetStage.TransAnimaType type, @NotNull Chart chart) {
            play(type,
                    chart.music,
                    chart.composer,
                    chart.illustrationPath,
                    chart.illustrator,
                    chart.noteDesigner,
                    chart.paradigms);
        }
    }

    //TODO: 素材替换
    public enum TransAnimaType {
        NORMAL(Resources.NORMAL_TRANSANIMA_L, Resources.NORMAL_TRANSANIMA_R),
        GRIEVOUS(Resources.NORMAL_TRANSANIMA_L, Resources.NORMAL_TRANSANIMA_R),
        FRACTURE(Resources.NORMAL_TRANSANIMA_L, Resources.NORMAL_TRANSANIMA_R),
        FINAL(Resources.NORMAL_TRANSANIMA_L, Resources.NORMAL_TRANSANIMA_R),
        ARGHENA(Resources.NORMAL_TRANSANIMA_L, Resources.NORMAL_TRANSANIMA_R),
        ALTER(Resources.NORMAL_TRANSANIMA_L, Resources.NORMAL_TRANSANIMA_R),
        DESIGNANT(Resources.NORMAL_TRANSANIMA_L, Resources.NORMAL_TRANSANIMA_R);

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
