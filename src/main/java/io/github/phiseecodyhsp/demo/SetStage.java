package io.github.phiseecodyhsp.demo;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class SetStage extends Stage {
    private static final int WIDTH = 640;
    private static final int HEIGHT = WIDTH / 16 * 9;

    public final Root root = new Root();

    public SetStage() {
        Scene scene = new Scene(root);
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.F11) {
                setFullScreen(!isFullScreen());
            }
        });

        setWidth(WIDTH);
        setHeight(HEIGHT);
        setScene(scene);
        show();
    }
}
