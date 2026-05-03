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
    public final List<StoryButton> storyButtons = new ArrayList<>();

    private int darkLineCount;

    public StoryButtonPane(@NotNull StoryPane parent) {
        this.parent = parent;

        lightLine.setFill(Color.WHITE);
        lightLine.setHeight(StoryButton.BORDER_WIDTH);

        parentProperty().addListener((_, _, p) -> {
            if (p != null && p != parent) {
                throw new IllegalStateException(getClass().getSimpleName() + "的父容器必须与实例化其时传入的父容器相同");
            }
        });
        getChildren().add(lightLine);
    }

    public void add(StoryButton... storyButtons) {
        getChildren().addAll(storyButtons);
        this.storyButtons.addAll(List.of(storyButtons));
        this.storyButtons.getFirst().unlock(parent);

        int s = this.storyButtons.size();
        double l = StoryButton.SIDE_LENGTH + StoryButton.DIAGONAL_LENGTH;

        for (int i = 0; i < s; i++) {
            this.storyButtons.get(i).setTranslateX(l * (i + (1 - s) / 2.0));
        }

        setMaxSize(s * l - StoryButton.SIDE_LENGTH, StoryButton.DIAGONAL_LENGTH);
    }

    public void updateLine() {
        int e = (int) storyButtons.stream().filter(StoryButton::isEnabled).count();
        int s = storyButtons.size();
        double l = StoryButton.SIDE_LENGTH + StoryButton.DIAGONAL_LENGTH;
        int d = s - e;

        lightLine.setWidth(l * (e - 1));
        lightLine.setTranslateX(l * (e - s) / 2.0);

        while (darkLineCount < d) {
            darkLineCount++;
            Rectangle darkLine = new Rectangle(StoryButton.SIDE_LENGTH + 7, StoryButton.BORDER_WIDTH);
            darkLine.setOpacity(StoryButton.LOWEST_OPACITY);
            darkLine.setFill(Color.WHITE);
            getChildren().addFirst(darkLine);
        }
        while (darkLineCount > d) {
            darkLineCount--;
            getChildren().removeFirst();
        }
        for (int i = 0; i < darkLineCount; i++) {
            getChildren().get(i).setTranslateX(l * (e + i - s / 2.0));
        }
    }
}