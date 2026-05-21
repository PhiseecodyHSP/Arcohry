package io.github.phiseecodyhsp.arcstory.storyMode;

import javafx.scene.layout.StackPane;

//TODO
public class Story extends StackPane {
    private boolean withCG;

    public Story() {

    }

    public void play(ChapterPane parent) {
        parent.getChildren().add(this);
    }

    public boolean hasCG() {
        return withCG;
    }
}
