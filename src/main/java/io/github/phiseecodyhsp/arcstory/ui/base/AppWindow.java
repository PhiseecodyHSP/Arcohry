package io.github.phiseecodyhsp.arcstory.ui.base;

import io.github.phiseecodyhsp.arcstory.ui.util.ScreenMetrics;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class AppWindow {

    private final Stage stage;
    private final StackPane root;
    private final ScreenManager screenManager;

    private static final double DEFAULT_SCALE = 0.5D;
    private static final double WIDTH = ScreenMetrics.SCREEN_WIDTH * DEFAULT_SCALE;
    private static final double HEIGHT = ScreenMetrics.SCREEN_HEIGHT * DEFAULT_SCALE;

    public AppWindow(Stage stage) {
        this.stage = stage;
        this.root = new StackPane();
        this.screenManager = new ScreenManager(this.root);

        Scene scene = new Scene(this.root, WIDTH, HEIGHT);
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
        double scale = Math.max(this.stage.getScene().getWidth() / ScreenMetrics.SCREEN_WIDTH,
                this.stage.getScene().getHeight() / ScreenMetrics.SCREEN_HEIGHT);
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
