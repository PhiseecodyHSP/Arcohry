package io.github.phiseecodyhsp.demo;

import io.github.phiseecodyhsp.demo.storyMode.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class TestApplication extends Application {
    public void start(Stage stage) {
        StoryUnlockConditionView condition1 = new StoryUnlockConditionView(Charts.Tutorial_PST);
        StoryUnlockConditionView condition2 = new StoryUnlockConditionView(Charts.Tutorial_PST, Partners.DORO_C);

        StoryPane pane = new StoryPane(Resources.Beyond_BACKGROUND);

        StoryButtonPane bPane1 = new StoryButtonPane(pane, Partners.DORO_C);
        StoryButtonPane bPane2 = new StoryButtonPane(pane, Partners.DORO_C);
        StoryButtonPane bPane3 = new StoryButtonPane(pane, Partners.DORO_C);

        StoryButton button1 = new StoryButton(bPane1, "1.1", Resources.Tutorial_ILLUSTRTION, null, (Story) null);
        StoryButton button2 = new StoryButton(bPane1, "1.2", Resources.Tutorial_ILLUSTRTION, condition1, (Story) null);
        StoryButton button3 = new StoryButton(bPane2, "2.1", Resources.Tutorial_ILLUSTRTION, null, (Story) null);
        StoryButton button4 = new StoryButton(bPane2, "2.2",  Resources.Tutorial_ILLUSTRTION, condition1, (Story) null);
        StoryButton button5 = new StoryButton(bPane2, "2.3", Resources.Tutorial_ILLUSTRTION, null, (Story) null);
        StoryButton button6 = new StoryButton(bPane2, "2.4", Resources.Tutorial_ILLUSTRTION, null, (Story) null);
        StoryButton button7 = new StoryButton(bPane3, "3.1", Resources.Tutorial_ILLUSTRTION, null, (Story) null);
        StoryButton button8 = new StoryButton(bPane3, "3.2", Resources.Tutorial_ILLUSTRTION, condition2, (Story) null);
        StoryButton button9 = new StoryButton(bPane3, "3.3", Resources.Tutorial_ILLUSTRTION, null, (Story) null);

        bPane1.add(button1, button2);
        bPane2.add(button3, button4, button5, button6);
        bPane3.add(button7, button8, button9);
        pane.add(bPane1, bPane2, bPane3);

        SetStage setStage = new SetStage(pane);
        setStage.show();
    }
}
