package io.github.phiseecodyhsp.arcstory;

import io.github.phiseecodyhsp.arcstory.storage.*;
import io.github.phiseecodyhsp.arcstory.storyMode.*;
import io.github.phiseecodyhsp.arcstory.storyMode.ChapterPane.*;
import io.github.phiseecodyhsp.arcstory.storyMode.ChapterPane.StoryButtonPane.*;
import javafx.application.Application;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

public class TestApplication extends Application {
    public void start(Stage stage) {
        PotentialCalculator calculator = new PotentialCalculator();

        ChapterPane chapterPane = new ChapterPane(Resources.CHAPTER5_SCENERY);

        StoryButtonPane buttonPane1 = chapterPane.new StoryButtonPane((String) null, null);
        StoryButtonPane buttonPane2 = chapterPane.new StoryButtonPane((String) null, null);

        StoryButton A1 = buttonPane1.new StoryButton(
                "A1",
                Resources.Tutorial_ILLUSTRTION,
                Resources.STORYA1,
                null,
                null);
        StoryButton A2 = buttonPane1.new StoryButton(
                "A2",
                Resources.Tutorial_ILLUSTRTION,
                Resources.STORYA2,
                null,
                null);
        StoryButton A3 = buttonPane1.new StoryButton(
                "A3",
                Resources.Tutorial_ILLUSTRTION,
                Resources.STORYA3,
                null,
                null);
        StoryButton A4 = buttonPane1.new StoryButton(
                "A4",
                Resources.Tutorial_ILLUSTRTION,
                Resources.STORYA4,
                null,
                null);
        StoryButton A5 = buttonPane1.new StoryButton(
                "A5",
                Resources.Tutorial_ILLUSTRTION,
                Resources.STORYA5,
                null,
                null);
        StoryButton A6 = buttonPane1.new StoryButton(
                "A6",
                Resources.Tutorial_ILLUSTRTION,
                Resources.STORYA6,
                null,
                null);
        StoryButton AE = buttonPane1.new StoryButton(
                "AE",
                Resources.Tutorial_ILLUSTRTION,
                Resources.STORYAE,
                null,
                null);
        StoryButton BACK = buttonPane2.new StoryButton(
                "BACK",
                Resources.Tutorial_ILLUSTRTION,
                Resources.STORYAE,
                null,
                null);
        StoryButton VIDEO = buttonPane2.new StoryButton(
                "VIDEO",
                Resources.Tutorial_ILLUSTRTION,
                Resources.STORYAE,
                null,
                null);

        ImageView view = new ImageView(Resources.COVER);
        view.setPreserveRatio(true);
        view.setFitWidth(Util.PRIMARY_SCREEN_WIDTH);
        SetStage setStage = new SetStage(view);
        view.setOnMouseClicked(_ -> {
            setStage.transitionNode(chapterPane);
            new AudioClip(Resources.ofString("audios/START.mp3")).play();
        });
        BACK.setBorderOnMouseClicked(_ -> setStage.transitionBack());
        VIDEO.setBorderOnMouseClicked(_ -> getHostServices().showDocument(
                "https://www.bilibili.com/video/BV1iCpozfE8w"));
        setStage.show();
    }
}
