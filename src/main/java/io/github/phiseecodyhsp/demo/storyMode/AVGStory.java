package io.github.phiseecodyhsp.demo.storyMode;

import io.github.phiseecodyhsp.demo.SetStage;
import javafx.scene.layout.StackPane;

//TODO
public class AVGStory extends StackPane {
    public AVGStory() {
    }

    public void play(SetStage stage) {
        stage.switchPane(SetStage.TransAnimaType.NORMAL, this);
    }
}
