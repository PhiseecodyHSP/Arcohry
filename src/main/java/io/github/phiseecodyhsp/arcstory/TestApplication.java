package io.github.phiseecodyhsp.arcstory;

import io.github.phiseecodyhsp.arcstory.storage.*;
import io.github.phiseecodyhsp.arcstory.storyMode.*;
import io.github.phiseecodyhsp.arcstory.storyMode.ChapterPane.*;
import io.github.phiseecodyhsp.arcstory.storyMode.ChapterPane.StoryButtonPane.*;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class TestApplication extends Application {
    public void start(Stage stage) {
        PotentialCalculator calculator = new PotentialCalculator();

        ChapterPane chapterPane = new ChapterPane(Resources.Beyond_BACKGROUND);

        StoryButtonPane buttonPane = chapterPane.new StoryButtonPane(Partners.Hikari, null);

        StoryButton storyButton = buttonPane.new StoryButton(
                "SP",
                Resources.Tutorial_ILLUSTRTION,
                Resources.STORYCG,
                null,
                null);

        stage = new SetStage(chapterPane);
        stage.show();
        System.out.println(PotentialCalculator.getScore(10.8, 12.45));
    }
}
