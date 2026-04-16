package io.github.phiseecodyhsp.demo;

import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class Opening extends StackPane {
    public Opening(Parent newRoot) {
        setOnMouseClicked(_ -> getScene().setRoot(newRoot));
    }
}
