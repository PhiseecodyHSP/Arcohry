package io.github.phiseecodyhsp.demo.storyMode;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class StoryButtonPane extends StackPane {
    private final Rectangle lightLine = new Rectangle();
    private final Rectangle darkLine = new Rectangle();
    public final List<StoryButton> buttonList = new ArrayList<>();

    public StoryButtonPane() {
        lightLine.setFill(Color.WHITE);
        lightLine.setHeight(StoryButton.BORDER_WIDTH);
        darkLine.setFill(Color.WHITE);
        darkLine.setOpacity(StoryButton.BUTTON_LOWEST_OPACITY);
        darkLine.setHeight(StoryButton.BORDER_WIDTH);

        getChildren().addAll(lightLine, darkLine);
    }

    public void add(StoryButton... storyButtons) {
        buttonList.addAll(List.of(storyButtons));
        getChildren().addAll(storyButtons);

        int s = buttonList.size();
        int d = StoryButton.SIDE_LENGTH + StoryButton.DIAGONAL_LENGTH;
        lightLine.setWidth((s - 1) * d);
        setMaxSize(s * d - StoryButton.SIDE_LENGTH, StoryButton.DIAGONAL_LENGTH);

        for (int i = 0; i < s; i++) {
            buttonList.get(i).setTranslateX(d * (i + 1) -((s + 1.0) * d) / 2);
        }

        buttonList.getFirst().unlock();
    }
}
