package io.github.phiseecodyhsp.arcstory;

import io.github.phiseecodyhsp.arcstory.storage.*;
import io.github.phiseecodyhsp.arcstory.storyMode.*;
import io.github.phiseecodyhsp.arcstory.storyMode.ChapterPane.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class TestApplication extends Application {
    public void start(Stage stage) {
        PotentialCalculator calculator = new PotentialCalculator();

        ChapterPane chapterPane = new ChapterPane(Resources.CHAPTER5_SCENERY);

        StoryButtonPane buttonPane1 = chapterPane.new StoryButtonPane((String) null, null);
        StoryButtonPane buttonPane2 = chapterPane.new StoryButtonPane((String) null, null);

        SetStage setStage = new SetStage(chapterPane);
    }
}
