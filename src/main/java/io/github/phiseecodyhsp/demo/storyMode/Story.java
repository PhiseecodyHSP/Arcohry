package io.github.phiseecodyhsp.demo.storyMode;

import javafx.scene.layout.StackPane;

//TODO
public class Story extends StackPane {
    private boolean withCG;

    public Story() {

    }

    public void play(StoryPane parent) {
        parent.getChildren().add(this);
    }

    public boolean hasCG() {
        return withCG;
    }
}
