package io.github.phiseecodyhsp.demo;

import javafx.scene.layout.StackPane;

public class Opening extends StackPane {
    public Opening(Root root) {
        setOnMouseClicked(_ -> getScene().setRoot(root));
    }
}
