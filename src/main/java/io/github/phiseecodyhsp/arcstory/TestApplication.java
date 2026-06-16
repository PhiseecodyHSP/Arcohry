package io.github.phiseecodyhsp.arcstory;

import io.github.phiseecodyhsp.arcstory.storage.*;
import io.github.phiseecodyhsp.arcstory.storyMode.*;
import io.github.phiseecodyhsp.arcstory.storyMode.ChapterPane.StoryButtonPane;
import io.github.phiseecodyhsp.arcstory.storyMode.ChapterPane.StoryButtonPane.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class TestApplication extends Application {
    public void start(Stage stage) {
        PotentialCalculator calculator = new PotentialCalculator();

        ChapterPane chapterPane = new ChapterPane(Resources.CHAPTER5_SCENERY);

        StoryButtonPane buttonPane1 = chapterPane.new StoryButtonPane((String) null, null);
        StoryButtonPane buttonPane2 = chapterPane.new StoryButtonPane((String) null, null);

        StoryButton button1 = buttonPane1.new StoryButton(
                "A",
                Resources.Tutorial_ILLUSTRTION,
                Resources.STORYA1,
                Charts.Tutorial_PST,
                Partners.Hikari);

        SetStage setStage = new SetStage(chapterPane);
    }
}
