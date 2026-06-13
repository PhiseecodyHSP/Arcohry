package io.github.phiseecodyhsp.arcstory;

import io.github.phiseecodyhsp.arcstory.storage.Chart;
import io.github.phiseecodyhsp.arcstory.storage.Resources;
import io.github.phiseecodyhsp.arcstory.storyMode.ChapterPane;
import io.github.phiseecodyhsp.arcstory.storyMode.ChapterSelectionPane;
import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
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
        loading.play(root, type, newNode);
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
        loading.play(root, type, musicName, composer,illustrationPath, illustrator, noteDesigner, paradigms);
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
        bgmPlayer.setVolume(0.2D);
        bgmPlayer.play();
    }
}
