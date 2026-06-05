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

        ChapterPane pane1 = new ChapterPane(Resources.Beyond_BACKGROUND);
        ChapterPane pane2 = new ChapterPane(Resources.Beyond_BACKGROUND);

        StoryButtonPane bPane1 = pane1.new StoryButtonPane(Partners.DORO_C, null);
        StoryButtonPane bPane2 = pane2.new StoryButtonPane(Partners.DORO_C, null);
        StoryButtonPane bPane3 = pane1.new StoryButtonPane(Partners.DORO_C, null);

        StoryButton button1 = bPane1.new StoryButton("1-1", Resources.Tutorial_ILLUSTRTION, Resources.STORY1_1, null, null);
        StoryButton button2 = bPane1.new StoryButton("1-2", Resources.Tutorial_ILLUSTRTION, Resources.STORY1_1, Charts.Tutorial_PST, Partners.DORO_C);
        StoryButton button3 = bPane2.new StoryButton("2-1", Resources.Tutorial_ILLUSTRTION, Resources.STORY1_1, null, null);
        StoryButton button4 = bPane2.new StoryButton("2-2", Resources.Tutorial_ILLUSTRTION, Resources.STORY1_1, null, null);
        StoryButton button5 = bPane2.new StoryButton("2-3", Resources.Tutorial_ILLUSTRTION, Resources.STORY1_1, null, null);
        StoryButton button6 = bPane2.new StoryButton("2-4", Resources.Tutorial_ILLUSTRTION, Resources.STORY1_1, null, null);
        StoryButton button7 = bPane3.new StoryButton("3-1", Resources.Tutorial_ILLUSTRTION, Resources.STORY1_1, null, null);
        StoryButton button8 = bPane3.new StoryButton("3-2", Resources.Tutorial_ILLUSTRTION, Resources.STORY1_1, null, null);
        StoryButton button9 = bPane3.new StoryButton("3-3", Resources.Tutorial_ILLUSTRTION, Resources.STORY1_1, null, null);

        SetStage setStage = new SetStage(pane1);
        setStage.show();
    }
}
