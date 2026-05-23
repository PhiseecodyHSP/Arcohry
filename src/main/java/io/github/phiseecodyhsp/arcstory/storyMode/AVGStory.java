package io.github.phiseecodyhsp.arcstory.storyMode;

import io.github.phiseecodyhsp.arcstory.SetStage;
import javafx.scene.layout.StackPane;

//TODO
public class AVGStory extends StackPane {
    public AVGStory() {
    }

    public void play(SetStage stage) {
        stage.switchPane(SetStage.TransitionAnimation.Type.NORMAL, this);
    }
}
