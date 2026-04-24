package io.github.phiseecodyhsp.demo;

import io.github.phiseecodyhsp.demo.storyMode.StoryButton;
import io.github.phiseecodyhsp.demo.storyMode.StoryButtonPane;
import io.github.phiseecodyhsp.demo.storyMode.StoryPane;
import io.github.phiseecodyhsp.demo.storyMode.StoryUnlockConditionView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class TestApplication extends Application {
    public void start(Stage stage) {
        StoryUnlockConditionView condition = new StoryUnlockConditionView("", null);
        TransitionAnimation anima = new TransitionAnimation(TransitionAnimation.Type.NORMAL);

        StoryButton button1 = new StoryButton("1.1", null, null, null);
        StoryButton button2 = new StoryButton("1.2", null, condition, null);
        StoryButton button3 = new StoryButton("1.3", null, null, null);
        StoryButton button4 = new StoryButton("2.1", null, null, null);
        StoryButton button5 = new StoryButton("2.2", null, condition, null);
        StoryButton button6 = new StoryButton("2.3", null, null, null);
        StoryButton button7 = new StoryButton("3.1", null, null, null);
        StoryButton button8 = new StoryButton("3.2", null, condition, null);
        StoryButton button9 = new StoryButton("3.3", null, null, null);

        StoryButtonPane bPane1 = new StoryButtonPane();
        bPane1.add(button1, button2, button3);
        StoryButtonPane bPane2 = new StoryButtonPane();
        bPane2.add(button4, button5, button6);
        StoryButtonPane bPane3 = new StoryButtonPane();
        bPane3.add(button7, button8, button9);

        StoryPane pane = new StoryPane(null);
        pane.add(bPane1, bPane2, bPane3);
        Root root = new Root();
        root.getChildren().add(pane);

        Scene scene = new Scene(root);
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.F11) {
                stage.setFullScreen(!stage.isFullScreen());
            }
        });

        stage.setScene(scene);
        stage.setWidth((double) 1920 / 3);
        stage.setHeight((double) 1080 / 3);
        stage.show();
    }
}
