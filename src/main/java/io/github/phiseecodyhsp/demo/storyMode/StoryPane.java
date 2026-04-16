package io.github.phiseecodyhsp.demo.storyMode;

import io.github.phiseecodyhsp.demo.Resources;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

public class StoryPane extends StackPane {
    private final List<StoryButtonPane> buttonPaneList = new ArrayList<>();

    public StoryPane(String path) {
        Image image = new Image(Resources.Beyond_BACKGROUND);
        try {
            image = new Image(path);
        } catch (NullPointerException | IllegalArgumentException _) {}

        setBackground(new Background(new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(
                        BackgroundSize.AUTO,
                        BackgroundSize.AUTO,
                        false, false, true, true
                ))));
    }

    public void add(StoryButtonPane... buttonPanes) {
        getChildren().addAll(buttonPanes);
        buttonPaneList.addAll(List.of(buttonPanes));

        int s = buttonPaneList.size();
        int d = StoryButton.DIAGONAL_LENGTH;
        for (int i = 0; i < buttonPaneList.size(); i++) {
            buttonPaneList.get(i).setTranslateY((2 * i - s + 1) * d);
        }
    }
}
