package io.github.phiseecodyhsp.arcstory.storyMode;

import io.github.phiseecodyhsp.arcstory.SetStage;
import io.github.phiseecodyhsp.arcstory.Util;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ChapterPane extends StackPane {
    private final List<StoryButtonPane> storyButtonPanes = new ArrayList<>();

    public ChapterPane(@NotNull String bgPath, StoryButtonPane... buttonPanes) {
        ImageView bg = new ImageView(bgPath);
        bg.setPreserveRatio(true);
        bg.setFitWidth(Util.getScreenWidth());

        getChildren().add(bg);
        addAll(buttonPanes);
    }

    public void addAll(StoryButtonPane... buttonPanes) {
        getChildren().addAll(buttonPanes);
        storyButtonPanes.addAll(List.of(buttonPanes));

        int s = storyButtonPanes.size();
        double d = StoryButton.DIAGONAL_LENGTH;
        for (int i = 0; i < s; i++) {
            storyButtonPanes.get(i).setTranslateY((2 * i - s + 1) * d);
        }
    }
}
