package io.github.phiseecodyhsp.arcstory.ui.base;

import io.github.phiseecodyhsp.arcstory.util.ScreenMetrics;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

public class AppWindow {

    private final Stage stage;
    private final StackPane root;
    private final Scale scaleTransform;
    private final ScreenManager screenManager;

    private static final double ASPECT_RATIO = 16.0 / 9.0;

    public AppWindow(Stage stage) {
        this.stage = stage;
        this.root = new StackPane();
        this.scaleTransform = new Scale();
        this.root.getTransforms().add(scaleTransform);
        this.screenManager = new ScreenManager(root);

        double width = ScreenMetrics.getPrimaryScreenWidth() * 0.5;
        double height = width / ASPECT_RATIO;

        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.setTitle("ArcStory");
        stage.show();

        scene.widthProperty().addListener((obs, oldVal, newVal) -> updateScale());
        scene.heightProperty().addListener((obs, oldVal, newVal) -> updateScale());

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.F11) {
                stage.setFullScreen(!stage.isFullScreen());
                updateScale();
            }
        });

        updateScale();
    }

    private void updateScale() {
        double sceneW = stage.getScene().getWidth();
        double sceneH = stage.getScene().getHeight();

        double scaleX = sceneW / root.prefWidth(-1);
        double scaleY = sceneH / root.prefHeight(-1);
        double scale = Math.min(scaleX, scaleY);

        if (root.prefWidth(-1) <= 0) {
            scale = Math.min(sceneW / 1280, sceneH / 720);
        }

        scaleTransform.setX(scale);
        scaleTransform.setY(scale);
        scaleTransform.setPivotX(0);
        scaleTransform.setPivotY(0);

        root.setTranslateX((sceneW - root.prefWidth(-1) * scale) / 2);
        root.setTranslateY((sceneH - root.prefHeight(-1) * scale) / 2);
    }

    public Stage getStage() {
        return stage;
    }

    public ScreenManager getScreenManager() {
        return screenManager;
    }

    public StackPane getRoot() {
        return root;
    }
}
