package io.github.phiseecodyhsp.demo;

import io.github.phiseecodyhsp.demo.storage.Charts;
import io.github.phiseecodyhsp.demo.storage.Partners;
import io.github.phiseecodyhsp.demo.storage.Resources;
import io.github.phiseecodyhsp.demo.storyMode.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class TestApplication extends Application {
    public void start(Stage stage) {
        StoryUnlockConditionView condition1 = new StoryUnlockConditionView(Charts.Tutorial_PST);
        StoryUnlockConditionView condition2 = new StoryUnlockConditionView(Charts.Tutorial_PST, Partners.DORO_C);
        Story story = new Story();

        StoryPane pane1 = new StoryPane(Resources.Beyond_BACKGROUND);
        StoryPane pane2 = new StoryPane(Resources.Beyond_BACKGROUND);

        StoryButtonPane bPane1 = new StoryButtonPane(pane1, Partners.DORO_C);
        StoryButtonPane bPane2 = new StoryButtonPane(pane2, Partners.DORO_C);
        StoryButtonPane bPane3 = new StoryButtonPane(pane1, Partners.DORO_C);

        StoryButton button1 = new StoryButton(bPane1, "1.1", Resources.Tutorial_ILLUSTRTION, null, story);
        StoryButton button2 = new StoryButton(bPane1, "1.2", Resources.Tutorial_ILLUSTRTION, condition1, story);
        StoryButton button3 = new StoryButton(bPane2, "2.1", Resources.Tutorial_ILLUSTRTION, null, story);
        StoryButton button4 = new StoryButton(bPane2, "2.2",  Resources.Tutorial_ILLUSTRTION, condition1, story);
        StoryButton button5 = new StoryButton(bPane2, "2.3", Resources.Tutorial_ILLUSTRTION, null, story);
        StoryButton button6 = new StoryButton(bPane2, "2.4", Resources.Tutorial_ILLUSTRTION, null, story);
        StoryButton button7 = new StoryButton(bPane3, "3.1", Resources.Tutorial_ILLUSTRTION, null, story);
        StoryButton button8 = new StoryButton(bPane3, "3.2", Resources.Tutorial_ILLUSTRTION, condition2, story);
        StoryButton button9 = new StoryButton(bPane3, "3.3", Resources.Tutorial_ILLUSTRTION, null, story);

        bPane1.add(button1, button2);
        bPane2.add(button3, button4, button5, button6);
        bPane3.add(button7, button8, button9);
        pane1.add(bPane1, bPane3);
        pane2.add(bPane2);

        SetStage setStage = new SetStage(pane1);
        setStage.show();
    }
}
