package io.github.phiseecodyhsp.arcstory.storyMode;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import org.jetbrains.annotations.NotNull;

public class ActPane extends StackPane {
    public final Label numberLabel = new Label();
    public final String bgPath;

    private ActPane(String name, @NotNull String bgPath, ChapterButton... buttons) {
        Label nameLabel = new Label(name);
        this.bgPath = bgPath;

        getChildren().addAll(numberLabel, nameLabel);
        addAll(buttons);
    }

    private void addAll(ChapterButton... buttons) {
        getChildren().addAll(buttons);
    }
}
