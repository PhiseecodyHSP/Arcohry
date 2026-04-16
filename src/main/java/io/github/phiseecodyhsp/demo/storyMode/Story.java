package io.github.phiseecodyhsp.demo.storyMode;

import io.github.phiseecodyhsp.demo.Util;
import javafx.scene.layout.StackPane;

public class Story extends StackPane {
    public Story(String story) {

    }

    private StoryPane getParentStoryPane() {
        return Util.getDesignatedParent(this, StoryPane.class);
    }

    public void play(StoryPane storyPane) {
        storyPane.getChildren().add(this);
    }
}
