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
        this.root.getTransforms().add(this.scaleTransform);
        this.screenManager = new ScreenManager(this.root);

        double width = ScreenMetrics.getPrimaryScreenWidth() * 0.5;
        double height = width / ASPECT_RATIO;

        Scene scene = new Scene(this.root, width, height);
        stage.setScene(scene);
        stage.setTitle("ArcStory");
        stage.show();

        scene.widthProperty().addListener((_, _, _) -> updateScale());
        scene.heightProperty().addListener((_, _, _) -> updateScale());

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.F11) {
                this.stage.setFullScreen(!this.stage.isFullScreen());
                updateScale();
            }
        });

        updateScale();
    }

    private void updateScale() {
        double sceneW = this.stage.getScene().getWidth();
        double sceneH = this.stage.getScene().getHeight();

        double scaleX = sceneW / this.root.prefWidth(-1);
        double scaleY = sceneH / this.root.prefHeight(-1);
        double scale = Math.min(scaleX, scaleY);

        if (this.root.prefWidth(-1.0D) <= 0) {
            scale = Math.min(sceneW / 1280.0D, sceneH / 720.0D);
        }

        this.scaleTransform.setX(scale);
        this.scaleTransform.setY(scale);
        this.scaleTransform.setPivotX(0.0D);
        this.scaleTransform.setPivotY(0.0D);

        this.root.setTranslateX((sceneW - this.root.prefWidth(-1.0D) * scale) / 2.0D);
        this.root.setTranslateY((sceneH - this.root.prefHeight(-1.0D) * scale) / 2.0D);
    }

    public Stage getStage() {
        return this.stage;
    }

    public ScreenManager getScreenManager() {
        return this.screenManager;
    }

    public StackPane getRoot() {
        return this.root;
    }
}
