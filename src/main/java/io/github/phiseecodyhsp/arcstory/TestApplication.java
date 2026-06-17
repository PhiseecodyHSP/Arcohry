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
                null,
                null);
        StoryButton button2 = buttonPane1.new StoryButton(
                "B",
                Resources.Tutorial_ILLUSTRTION,
                Resources.STORYA1,
                null,
                null);
        StoryButton button3 = buttonPane1.new StoryButton(
                "C",
                Resources.Tutorial_ILLUSTRTION,
                Resources.STORYA1,
                null,
                null);
        StoryButton button4 = buttonPane1.new StoryButton(
                "D",
                Resources.Tutorial_ILLUSTRTION,
                Resources.STORYA1,
                Charts.Tutorial_PST,
                Partners.Hikari);
        StoryButton button01 = buttonPane2.new StoryButton(
                "Α",
                Resources.Tutorial_ILLUSTRTION,
                Resources.STORYA1,
                null,
                null);
        StoryButton button02 = buttonPane2.new StoryButton(
                "Β",
                Resources.Tutorial_ILLUSTRTION,
                Resources.STORYA1,
                null,
                null);
        StoryButton button03 = buttonPane2.new StoryButton(
                "Ψ",
                Resources.Tutorial_ILLUSTRTION,
                Resources.STORYA1,
                null,
                null);
        StoryButton button04 = buttonPane2.new StoryButton(
                "Δ",
                Resources.Tutorial_ILLUSTRTION,
                Resources.STORYA1,
                Charts.Tutorial_PST,
                Partners.Hikari);
        StoryButton button05 = buttonPane2.new StoryButton(
                "Ε",
                Resources.Tutorial_ILLUSTRTION,
                Resources.STORYA1,
                Charts.Tutorial_PST,
                Partners.Hikari);

        SetStage setStage = new SetStage(chapterPane);
    }
}
