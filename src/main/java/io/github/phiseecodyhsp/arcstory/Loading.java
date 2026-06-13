package io.github.phiseecodyhsp.arcstory;

import io.github.phiseecodyhsp.arcstory.storage.Chart;
import io.github.phiseecodyhsp.arcstory.storage.Resources;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

//TODO
public class Loading extends StackPane {
    public static final double TRANS_TIME = 1;
    private static final int LABEL_DISPLACEMENT = 0;
    private static final double HIGHEST_ILLUSTRATION_SCALE = 2;
    private static final double PARADIGMS_OPACITY = 0.5;
    private static final int ILLUSTRATION_SIZE = Util.doubleToEven(Util.PRIMARY_SCREEN_HEIGHT / 2);
    private static final PauseTransition DELAY = new PauseTransition(Duration.seconds(3));
    private static final Font FONT = Resources.getFont(Resources.GeosansLight_FONT, 20);

    private final ImageView left = new ImageView();
    private final ImageView right = new ImageView();
    private final ImageView illustrationView = new ImageView();
    private final ImageView musicNameShadow = new ImageView();
    private final ImageView shadow = new ImageView(Resources.TRANSANIMA_SHADOW);
    private final Rectangle paradigms = new Rectangle(ILLUSTRATION_SIZE, ILLUSTRATION_SIZE);
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


    public Loading() {
        illustrationView.setFitWidth(ILLUSTRATION_SIZE);
        illustrationView.setFitHeight(ILLUSTRATION_SIZE);
        paradigms.setOpacity(PARADIGMS_OPACITY);

        onLAdded.setInterpolator(Util.EASE_IN);
        onLRemoved.setInterpolator(Util.EASE_OUT);
        onRAdded.setInterpolator(Util.EASE_IN);
        onRRemoved.setInterpolator(Util.EASE_OUT);

        onLabelPaneAdded.setInterpolator(Util.EASE_IN);

        onIllustrationAdded.setFromX(HIGHEST_ILLUSTRATION_SCALE);
        onIllustrationAdded.setFromY(HIGHEST_ILLUSTRATION_SCALE);
        onIllustrationAdded.setToX(1);
        onIllustrationAdded.setToY(1);
        onIllustrationAdded.setInterpolator(Util.EASE_IN);
        onIllustrationRemoved.setToX(HIGHEST_ILLUSTRATION_SCALE);
        onIllustrationRemoved.setToY(HIGHEST_ILLUSTRATION_SCALE);
        onIllustrationRemoved.setInterpolator(Util.EASE_OUT);
        onPaneAdded.setFromValue(0);
        onPaneAdded.setToValue(1);
        onPaneRemoved.setToValue(0);

        DELAY.setOnFinished(_ -> {
            onLAdded.stop();
            onRAdded.stop();
            onLabelPaneAdded.stop();
            onIllustrationAdded.stop();
            onPaneAdded.stop();
            onLRemoved.playFromStart();
            onRRemoved.playFromStart();
            onIllustrationRemoved.playFromStart();
            onPaneRemoved.playFromStart();
            Resources.playSound(Resources.LOADING_END_SOUND);
        });

        musicName.setTextFill(Color.WHITE);
        musicName.setFont(FONT);
        composer.setTextFill(Color.WHITE);
        composer.setFont(FONT);
        illustrator.setTextFill(Color.WHITE);
        illustration.setFont(FONT);
        illustration.setTextFill(Color.WHITE);
        illustration.setFont(FONT);
        noteDesign.setTextFill(Color.WHITE);
        noteDesign.setFont(FONT);
        noteDesigner.setTextFill(Color.WHITE);
        noteDesigner.setFont(FONT);
        musicName.setTextFill(Color.WHITE);
        musicName.setFont(FONT);
    }

    public void play(StackPane pane, @NotNull Type type, Node newNode) {
        type.setImage(this);

        pane.getChildren().add(this);
        onLAdded.setOnFinished(_ -> {
            onLAdded.stop();
            onRAdded.stop();
            onLRemoved.playFromStart();
            onRRemoved.playFromStart();
            Resources.playSound(Resources.LOADING_END_SOUND);
            pane.getChildren().set(0, newNode);
            try {
                Util.getSetStage(pane).checkBgm();
            } catch (IllegalStateException _) {}
        });
        onLRemoved.setOnFinished(_ -> pane.getChildren().remove(this));
        onLRemoved.stop();
        onRRemoved.stop();
        onLAdded.playFromStart();
        onRAdded.playFromStart();
        Resources.playSound(Resources.LOADING_START_SOUND);

        getChildren().clear();
        getChildren().addAll(left, right);
    }

    //TODO
    public void play(StackPane pane,
                     @NotNull Type type,
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

        pane.getChildren().add(this);
        onLAdded.setOnFinished(_ -> {
            DELAY.stop();
            DELAY.playFromStart();
        });
        onLRemoved.setOnFinished(_ -> pane.getChildren().remove(this));
        onLRemoved.stop();
        onRRemoved.stop();
        onIllustrationRemoved.stop();
        onPaneRemoved.stop();
        onLAdded.playFromStart();
        onRAdded.playFromStart();
        onLabelPaneAdded.playFromStart();
        onIllustrationAdded.playFromStart();
        onPaneAdded.playFromStart();
        Resources.playSound(Resources.START_SOUND);

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
        getChildren().addAll(left, right, pane);
        this.noteDesigner.setText(noteDesigner);
    }

    public void play(StackPane pane, @NotNull Type type, @NotNull Chart chart) {
        play(
                pane,
                type,
                chart.music,
                chart.composer,
                chart.illustrationPath,
                chart.illustrator,
                chart.noteDesigner,
                chart.paradigms);
    }

    //TODO: 素材替换
    public enum Type {
        NORMAL(Resources.NORMAL_TRANSANIMA_L, Resources.NORMAL_TRANSANIMA_R),
        COURSE(Resources.NORMAL_TRANSANIMA_L, Resources.NORMAL_TRANSANIMA_R),
        GRIEVOUS(Resources.NORMAL_TRANSANIMA_L, Resources.NORMAL_TRANSANIMA_R),
        FRACTURE(Resources.NORMAL_TRANSANIMA_L, Resources.NORMAL_TRANSANIMA_R),
        TEMPESTISSIMO(Resources.NORMAL_TRANSANIMA_L, Resources.NORMAL_TRANSANIMA_R),
        FINAL(Resources.NORMAL_TRANSANIMA_L, Resources.NORMAL_TRANSANIMA_R),
        ARGHENA(Resources.NORMAL_TRANSANIMA_L, Resources.NORMAL_TRANSANIMA_R),
        ALTER(Resources.NORMAL_TRANSANIMA_L, Resources.NORMAL_TRANSANIMA_R),
        DESIGNANT(Resources.NORMAL_TRANSANIMA_L, Resources.NORMAL_TRANSANIMA_R),
        UNDYING(Resources.NORMAL_TRANSANIMA_L, Resources.NORMAL_TRANSANIMA_R);

        private final String leftImagePath;
        private final String rightImagePath;

        Type(String l, String r) {
            leftImagePath = l;
            rightImagePath = r;
        }

        private void setImage(Loading anima) {
            anima.left.setImage(new Image(leftImagePath));
            anima.right.setImage(new Image(rightImagePath));
        }
    }
}
