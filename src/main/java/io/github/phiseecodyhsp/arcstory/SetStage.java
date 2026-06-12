package io.github.phiseecodyhsp.arcstory;

import io.github.phiseecodyhsp.arcstory.storage.Chart;
import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

public class SetStage extends Stage {
    public static final double WIDTH = (int) Util.PRIMARY_SCREEN_WIDTH / 2.0;
    public static final int HEIGHT = (int) (WIDTH / 16 * 9);

    private Node lastNode;
    private Node currentNode;
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

    public void switchPane(Loading.Type type, Node newNode) {
        loading.play(root, type, newNode);
        lastNode = currentNode;
        currentNode = newNode;
    }

    public void switchPane(Node newNode) {
        FadeTransition ft1 = new FadeTransition(Duration.seconds(Loading.TRANS_TIME), currentNode);
        FadeTransition ft2 = new FadeTransition(Duration.seconds(Loading.TRANS_TIME), newNode);
        ft1.setToValue(0);
        ft1.setOnFinished(_ -> {
            root.getChildren().set(0, newNode);
            ft2.stop();
            ft2.playFromStart();
            currentNode.setMouseTransparent(false);
        });
        ft2.setFromValue(0);
        ft2.setToValue(1);
        ft1.stop();
        ft1.playFromStart();

        currentNode.setMouseTransparent(true);
        lastNode = currentNode;
        currentNode = newNode;
    }

    public void back(Loading.Type type) {
        if (lastNode != null) {
            switchPane(type, lastNode);
        }
    }

    public void back() {
        if (lastNode != null) {
            switchPane(lastNode);
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
}
