package io.github.phiseecodyhsp.arcstory;

import io.github.phiseecodyhsp.arcstory.storage.*;
import io.github.phiseecodyhsp.arcstory.storyMode.*;
import io.github.phiseecodyhsp.arcstory.storyMode.ChapterPane.*;
import io.github.phiseecodyhsp.arcstory.storyMode.ChapterPane.StoryButtonPane.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class TestApplication extends Application {
    public void start(Stage stage) {
        PotentialCalculator calculator = new PotentialCalculator();

        StoryUnlockConditionView condition1 = new StoryUnlockConditionView(Charts.Tutorial_PST);
        StoryUnlockConditionView condition2 = new StoryUnlockConditionView(Charts.Tutorial_PST, Partners.DORO_C);

        ChapterPane pane1 = new ChapterPane(Resources.Beyond_BACKGROUND);
        ChapterPane pane2 = new ChapterPane(Resources.Beyond_BACKGROUND);

        StoryButtonPane bPane1 = pane1.new StoryButtonPane(Partners.DORO_C, null);
        StoryButtonPane bPane2 = pane2.new StoryButtonPane(Partners.DORO_C, null);
        StoryButtonPane bPane3 = pane1.new StoryButtonPane(Partners.DORO_C, null);

        Story story = new Story(Resources.STORY1_1);

        StoryButton button1 = bPane1.new StoryButton("1-1", Resources.Tutorial_ILLUSTRTION, null, story);
        StoryButton button2 = bPane1.new StoryButton("1-2", Resources.Tutorial_ILLUSTRTION, condition1, story);
        StoryButton button3 = bPane2.new StoryButton("2-1", Resources.Tutorial_ILLUSTRTION, null, story);
        StoryButton button4 = bPane2.new StoryButton("2-2", Resources.Tutorial_ILLUSTRTION, condition1, story);
        StoryButton button5 = bPane2.new StoryButton("2-3", Resources.Tutorial_ILLUSTRTION, null, story);
        StoryButton button6 = bPane2.new StoryButton("2-4", Resources.Tutorial_ILLUSTRTION, null, story);
        StoryButton button7 = bPane3.new StoryButton("3-1", Resources.Tutorial_ILLUSTRTION, null, story);
        StoryButton button8 = bPane3.new StoryButton("3-2", Resources.Tutorial_ILLUSTRTION, condition2, story);
        StoryButton button9 = bPane3.new StoryButton("3-3", Resources.Tutorial_ILLUSTRTION, null, story);

        SetStage setStage = new SetStage(pane1);
        setStage.show();
    }
}
