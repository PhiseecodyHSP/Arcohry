package io.github.phiseecodyhsp.arcstory;

import io.github.phiseecodyhsp.arcstory.storage.*;
import io.github.phiseecodyhsp.arcstory.storyMode.*;
import io.github.phiseecodyhsp.arcstory.storyMode.ChapterPane.*;
import io.github.phiseecodyhsp.arcstory.storyMode.ChapterPane.StoryButtonPane.*;
import javafx.application.Application;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TestApplication extends Application {
    public void start(Stage stage) {
        PotentialCalculator calculator = new PotentialCalculator();

        ChapterPane chapterPane = new ChapterPane(Resources.CHAPTER5_SCENERY);

        StoryButtonPane buttonPane = chapterPane.new StoryButtonPane((String) null, null);

        StoryButton A1 = buttonPane.new StoryButton(
                "A1",
                Resources.Tutorial_ILLUSTRTION,
                Resources.STORYA1,
                null,
                null);
        StoryButton A2 = buttonPane.new StoryButton(
                "A2",
                Resources.Tutorial_ILLUSTRTION,
                Resources.STORYA2,
                null,
                null);
        StoryButton A3 = buttonPane.new StoryButton(
                "A3",
                Resources.Tutorial_ILLUSTRTION,
                Resources.STORYA3,
                null,
                null);
        StoryButton A4 = buttonPane.new StoryButton(
                "A4",
                Resources.Tutorial_ILLUSTRTION,
                Resources.STORYA4,
                null,
                null);
        StoryButton A5 = buttonPane.new StoryButton(
                "A5",
                Resources.Tutorial_ILLUSTRTION,
                Resources.STORYA5,
                null,
                null);
        StoryButton A6 = buttonPane.new StoryButton(
                "A6",
                Resources.Tutorial_ILLUSTRTION,
                Resources.STORYA6,
                null,
                null);
        StoryButton AE = buttonPane.new StoryButton(
                "AE",
                Resources.Tutorial_ILLUSTRTION,
                Resources.STORYAE,
                null,
                null);

        ImageView view = new ImageView(Resources.COVER);
        view.setPreserveRatio(true);
        view.setFitWidth(Util.PRIMARY_SCREEN_WIDTH);
        StackPane opening = new StackPane(view);
        SetStage setStage = new SetStage(opening);
        view.setOnMouseClicked(_ -> setStage.switchPane(chapterPane));
        setStage.show();
    }
}
