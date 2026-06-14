package io.github.phiseecodyhsp.arcstory;

import io.github.phiseecodyhsp.arcstory.storage.Chart;
import io.github.phiseecodyhsp.arcstory.storage.Resources;
import io.github.phiseecodyhsp.arcstory.storyMode.ChapterPane;
import io.github.phiseecodyhsp.arcstory.storyMode.ChapterSelectionPane;
import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SetStage extends Stage {
    public static final double WIDTH = (int) Util.PRIMARY_SCREEN_WIDTH / 2.0;
    public static final int HEIGHT = (int) (WIDTH / 16 * 9);

    private Node lastNode;
    private Node currentNode;
    private MediaPlayer bgmPlayer;

    private final StackPane root = new StackPane();
    private final Scene scene = new Scene(root);
    private final Loading loading = new Loading();

    public SetStage(Node initialNode) {
        root.setStyle("-fx-background-color: black;");
        root.getChildren().add(initialNode);

        scene.setFill(Color.BLACK);
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.F11) {
                setFullScreen(!isFullScreen());
            }
        });
        scene.widthProperty().addListener(_ -> updateScale());
        scene.heightProperty().addListener(_ -> updateScale());

        lastNode = null;
        currentNode = initialNode;

        setFullScreenExitHint("");
        setTitle("Report");
        setWidth(WIDTH);
        setHeight(HEIGHT);
        setScene(scene);
        show();
    }

    private void updateScale() {
        double scale = Math.max(scene.getWidth() / Util.PRIMARY_SCREEN_WIDTH,
                scene.getHeight() / (Util.PRIMARY_SCREEN_HEIGHT));
        root.setScaleX(scale);
        root.setScaleY(scale);
    }

    public void transitionNode(Node newNode) {
        lastNode = currentNode;
        currentNode = newNode;

        FadeTransition ft1 = new FadeTransition(Duration.seconds(Loading.TRANS_TIME), lastNode);
        FadeTransition ft2 = new FadeTransition(Duration.seconds(Loading.TRANS_TIME), currentNode);

        ft1.setToValue(0);
        ft2.setToValue(1);
        ft2.setOnFinished(_ -> {
            root.getChildren().remove(lastNode);
            lastNode.setMouseTransparent(false);
            currentNode.setMouseTransparent(false);
        });

        lastNode.setMouseTransparent(true);
        currentNode.setMouseTransparent(true);
        currentNode.setOpacity(0);
        root.getChildren().add(currentNode);
        ft1.playFromStart();
        ft2.playFromStart();
        checkBgm();
    }

    public void transitionBack() {
        if (lastNode != null) {
            transitionNode(lastNode);
            checkBgm();
        }
    }

    public void switchNode(Loading.Type type, Node newNode) {
        loading.play(type, newNode);
        lastNode = currentNode;
        currentNode = newNode;
    }

    public void switchNode(Node newNode) {
        lastNode = currentNode;
        currentNode = newNode;

        FadeTransition ft1 = new FadeTransition(Duration.seconds(Loading.TRANS_TIME), lastNode);
        FadeTransition ft2 = new FadeTransition(Duration.seconds(Loading.TRANS_TIME), currentNode);
        ft1.setToValue(0);
        ft1.setOnFinished(_ -> {
            currentNode.setOpacity(0);
            currentNode.setMouseTransparent(true);
            root.getChildren().set(0, currentNode);
            ft2.playFromStart();
            lastNode.setMouseTransparent(false);
        });
        ft2.setToValue(1);
        ft2.setOnFinished(_ -> currentNode.setMouseTransparent(false));

        lastNode.setMouseTransparent(true);
        ft1.playFromStart();
        checkBgm();
    }

    public void switchBack(Loading.Type type) {
        if (lastNode != null) {
            switchNode(type, lastNode);
        }
    }

    public void switchBack() {
        if (lastNode != null) {
            switchNode(lastNode);
            checkBgm();
        }
    }

    public void playChart(@NotNull Loading.Type type,
                          String musicName,
                          String composer,
                          @NotNull String illustrationPath,
                          String illustrator,
                          String noteDesigner,
                          @NotNull Chart.Paradigms paradigms) {
        loading.play(type, musicName, composer,illustrationPath, illustrator, noteDesigner, paradigms);
    }

    public void playChart(@NotNull Loading.Type type, @NotNull Chart chart) {
        playChart(type,
                chart.music,
                chart.composer,
                chart.illustrationPath,
                chart.illustrator,
                chart.noteDesigner,
                chart.paradigms);
    }

    //TODO
    public void checkBgm() {
        if (currentNode instanceof ChapterSelectionPane || currentNode instanceof ChapterPane) {
            if (bgmPlayer == null || bgmPlayer.getStatus() == MediaPlayer.Status.STOPPED) {
                playBgm(Resources.STORY_MODE_BGM);
            } else {
                if (!Objects.equals(bgmPlayer.getMedia().getSource(), Resources.STORY_MODE_BGM)) {
                    bgmPlayer.stop();
                    playBgm(Resources.STORY_MODE_BGM);
                }
            }
        } else {
            bgmPlayer.stop();
        }
    }

    private void playBgm(String path) {
        bgmPlayer = new MediaPlayer(new Media(path));
        bgmPlayer.setCycleCount(AudioClip.INDEFINITE);
        bgmPlayer.setVolume(0.2);
        bgmPlayer.play();
    }

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


        private Loading() {
            illustrationView.setFitWidth(ILLUSTRATION_SIZE);
            illustrationView.setFitHeight(ILLUSTRATION_SIZE);
            paradigms.setOpacity(PARADIGMS_OPACITY);

            onLAdded.setInterpolator(Util.EASE_IN);
            onLRemoved.setInterpolator(Util.EASE_OUT);
            onRAdded.setInterpolator(Util.EASE_IN);
            onRRemoved.setInterpolator(Util.EASE_OUT);
            onLRemoved.setOnFinished(_ -> root.getChildren().remove(this));

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

        private void play(@NotNull SetStage.Loading.Type type, Node newNode) {
            type.setImage(this);

            root.getChildren().add(this);
            onLAdded.setOnFinished(_ -> {
                onLAdded.stop();
                onRAdded.stop();
                onLRemoved.playFromStart();
                onRRemoved.playFromStart();
                Resources.playSound(Resources.LOADING_END_SOUND);
                root.getChildren().set(0, newNode);
                try {
                    Util.getSetStage(pane).checkBgm();
                } catch (IllegalStateException _) {}
            });
            onLRemoved.stop();
            onRRemoved.stop();
            onLAdded.playFromStart();
            onRAdded.playFromStart();
            Resources.playSound(Resources.LOADING_START_SOUND);

            getChildren().clear();
            getChildren().addAll(left, right);
        }

        //TODO
        public void play(@NotNull SetStage.Loading.Type type,
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
                DELAY.stop();
                DELAY.playFromStart();
            });
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

        private void play(@NotNull SetStage.Loading.Type type, @NotNull Chart chart) {
            play(
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
}
