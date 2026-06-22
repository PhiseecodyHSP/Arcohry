package io.github.phiseecodyhsp.arcstory.ui.base;

import io.github.phiseecodyhsp.arcstory.util.ScreenMetrics;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class AppWindow {

    private final Stage stage;
    private final StackPane root;
    private final ScreenManager screenManager;

    private static final double ASPECT_RATIO = 16.0 / 9.0;

    public AppWindow(Stage stage) {
        this.stage = stage;
        this.root = new StackPane();
        this.screenManager = new ScreenManager(this.root);

        double width = 0.5D * ScreenMetrics.getPrimaryScreenWidth();
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
        Rectangle2D screenBounds = ScreenMetrics.getPrimaryScreenBounds();
        double scale = Math.max(this.stage.getScene().getWidth() / screenBounds.getWidth(),
                this.stage.getScene().getHeight() / screenBounds.getHeight());
        this.root.setScaleX(scale);
        this.root.setScaleY(scale);
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
