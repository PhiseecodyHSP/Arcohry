package io.github.phiseecodyhsp.demo.storyMode;

import io.github.phiseecodyhsp.demo.Resources;
import io.github.phiseecodyhsp.demo.Util;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StoryPane extends StackPane {
    private final List<StoryButtonPane> storyButtonPanes = new ArrayList<>();

    public StoryPane(@NotNull String bgPath) {
        ImageView bg = new ImageView(Resources.Beyond_BACKGROUND);
        try {
            bg.setImage(new Image(bgPath));
        } catch (IllegalArgumentException _) {}
        bg.setPreserveRatio(true);
        bg.setFitWidth(Util.getScreenWidth());

        getChildren().add(bg);
    }

    public void add(StoryButtonPane... buttonPanes) {
        getChildren().addAll(buttonPanes);
        storyButtonPanes.addAll(List.of(buttonPanes));

        int s = storyButtonPanes.size();
        int d = StoryButton.DIAGONAL_LENGTH;
        for (int i = 0; i < storyButtonPanes.size(); i++) {
            storyButtonPanes.get(i).setTranslateY((2 * i - s + 1) * d);
        }
    }
}
