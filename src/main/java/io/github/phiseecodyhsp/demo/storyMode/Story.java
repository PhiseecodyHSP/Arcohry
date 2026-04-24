package io.github.phiseecodyhsp.demo.storyMode;

import javafx.scene.layout.StackPane;

public class Story extends StackPane {
    public Story(String story) {

    }

    public void play(StoryPane parent) {
        parent.getChildren().add(this);
    }
}
