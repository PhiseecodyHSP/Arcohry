package io.github.phiseecodyhsp.demo.storyMode;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class StoryButtonPane extends StackPane {
    private final Rectangle line = new Rectangle();
    public final List<StoryButton> buttonList = new ArrayList<>();

    public StoryButtonPane() {
        line.setFill(Color.WHITE);
        line.setHeight(StoryButton.BORDER_WIDTH);

        getChildren().add(line);
    }

    public void add(StoryButton... storyButtons) {
        buttonList.addAll(List.of(storyButtons));
        getChildren().addAll(storyButtons);

        int s = buttonList.size();
        int d = StoryButton.SIDE_LENGTH + StoryButton.DIAGONAL_LENGTH;
        line.setWidth((s - 1) * d);
        setMaxSize(s * d - StoryButton.SIDE_LENGTH, StoryButton.DIAGONAL_LENGTH);

        for (int i = 0; i < s; i++) {
            buttonList.get(i).setTranslateX(d * (i + 1) -((s + 1.0) * d) / 2);
        }

        buttonList.getFirst().unlock();
    }
}
