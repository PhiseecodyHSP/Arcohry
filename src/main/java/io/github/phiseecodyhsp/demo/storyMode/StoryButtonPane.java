package io.github.phiseecodyhsp.demo.storyMode;

import io.github.phiseecodyhsp.demo.Util;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StoryButtonPane extends StackPane {
    private final Rectangle lightLine = new Rectangle();
    private final StoryPane parent;
    public final List<StoryButton> buttonList = new ArrayList<>();

    public StoryButtonPane(@NotNull StoryPane parent) {
        this.parent = parent;

        lightLine.setFill(Color.WHITE);
        lightLine.setHeight(StoryButton.BORDER_WIDTH);

        getChildren().add(lightLine);
        parent.add(this);
    }

    public void add(StoryButton... storyButtons) {
        getChildren().addAll(storyButtons);
        buttonList.addAll(List.of(storyButtons));
        buttonList.getFirst().unlock(parent);

        int e = (int) buttonList.stream().filter(StoryButton::isEnabled).count();
        int s = buttonList.size();
        int l = StoryButton.SIDE_LENGTH + StoryButton.DIAGONAL_LENGTH;
        lightLine.setWidth(l * (e - 1));
        lightLine.setTranslateX(Util.nextEven(l * (e - s) / 2.0));
        //TODO: darkLine

        for (int i = 0; i < s; i++) {
            buttonList.get(i).setTranslateX(Util.nextEven(l * (i + (1 - s) / 2.0)));
        }

        setMaxSize(s * l - StoryButton.SIDE_LENGTH, StoryButton.DIAGONAL_LENGTH);
    }
}
