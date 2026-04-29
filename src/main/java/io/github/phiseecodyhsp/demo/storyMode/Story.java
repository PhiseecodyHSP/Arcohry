package io.github.phiseecodyhsp.demo.storyMode;

import javafx.scene.layout.StackPane;

public class Story extends StackPane {
    public void play(StoryPane parent) {
        parent.getChildren().add(this);
    }
}
