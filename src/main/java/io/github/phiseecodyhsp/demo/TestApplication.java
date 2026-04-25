package io.github.phiseecodyhsp.demo;

import io.github.phiseecodyhsp.demo.storyMode.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class TestApplication extends Application {
    public void start(Stage stage) {
        StoryUnlockConditionView condition = new StoryUnlockConditionView("", null);

        StoryPane pane = new StoryPane(null);

        StoryButtonPane bPane1 = new StoryButtonPane(pane);
        StoryButtonPane bPane2 = new StoryButtonPane(pane);
        StoryButtonPane bPane3 = new StoryButtonPane(pane);

        StoryButton button1 = new StoryButton(bPane1, "1.1", null, null, (Story) null);
        StoryButton button2 = new StoryButton(bPane1, "1.2", null, condition, (Story) null);
        StoryButton button3 = new StoryButton(bPane2, "2.1", null, null, (Story) null);
        StoryButton button4 = new StoryButton(bPane2, "2.2",  null, condition, (Story) null);
        StoryButton button5 = new StoryButton(bPane2, "2.3", null, null, (Story) null);
        StoryButton button6 = new StoryButton(bPane2, "2.4", null, null, (Story) null);
        StoryButton button7 = new StoryButton(bPane3, "3.1", null, null, (Story) null);
        StoryButton button8 = new StoryButton(bPane3, "3.2", null, condition, (Story) null);
        StoryButton button9 = new StoryButton(bPane3, "3.3", null, null, (Story) null);

        bPane1.add(button1, button2);
        bPane2.add(button3, button4, button5, button6);
        bPane3.add(button7, button8, button9);

        SetStage setStage = new SetStage(pane);
        setStage.switchPane(SetStage.TransAnimaType.NORMAL, pane);
    }
}
