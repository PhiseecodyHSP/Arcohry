package io.github.phiseecodyhsp.arcstory.storyMode;

import io.github.phiseecodyhsp.arcstory.storage.Partner;
import io.github.phiseecodyhsp.arcstory.Util;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static io.github.phiseecodyhsp.arcstory.storyMode.StoryButton.*;

public class StoryButtonPane extends StackPane {
    private final Rectangle lightLine = new Rectangle();
    private final StackPane partner;
    private final ChapterPane parent;
    public final List<StoryButton> storyButtons = new ArrayList<>();

    private int darkLineCount;

    //TODO: Title
    public StoryButtonPane(@NotNull ChapterPane parent, String partnerPath, String title, StoryButton... buttons) {
        this.parent = parent;

        lightLine.setFill(Color.WHITE);
        lightLine.setHeight(StoryButton.BORDER_WIDTH);


        parentProperty().addListener((_, _, p) -> {
            if (p != null && p != parent) {
                throw new IllegalStateException(getClass().getSimpleName() + "的父容器必须与实例化其时传入的父容器相同");
            }
        });

        if (partnerPath != null) {
            partner = new Partner(null, partnerPath, null).
                    getAvatarPane(SIDE_LENGTH, Color.rgb(150, 140, 160));
            getChildren().addAll(lightLine, partner);
        } else {
            partner = null;
            getChildren().add(lightLine);
        }
        addAll(buttons);
    }

    public StoryButtonPane(@NotNull ChapterPane parent, Partner partner, String title, StoryButton... buttons) {
        this(parent, partner.avatarPath(), title, buttons);
    }

    public void addAll(StoryButton... buttons) {
        if (buttons.length != 0) {
            getChildren().addAll(buttons);
            this.storyButtons.addAll(List.of(buttons));
            this.storyButtons.getFirst().unlock(parent);

            int s = this.storyButtons.size();
            int l = StoryButton.SIDE_LENGTH + StoryButton.DIAGONAL_LENGTH;

            if (partner != null) {
                partner.setTranslateX(Util.doubleToEven(l * ((2 - s) / 2.0 - 1)));
                for (int i = 0; i < s; i++) {
                    this.storyButtons.get(i).setTranslateX(Util.doubleToEven(l * (i + (2 - s) / 2.0)));
                }
            } else {
                for (int i = 0; i < s; i++) {
                    this.storyButtons.get(i).setTranslateX(Util.doubleToEven(l * (i + (1 - s) / 2.0)));
                }
            }
            setMaxSize(0, 0);
        }
    }

    public void updateLine() {
        long e = storyButtons.stream().filter(StoryButton::isEnabled).count();
        int s = storyButtons.size();
        double l = StoryButton.SIDE_LENGTH + StoryButton.DIAGONAL_LENGTH;
        long d = s - e;

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

        if (partner != null) {
            e++;
            s++;
        }
        lightLine.setWidth(l * (e - 1));
        lightLine.setTranslateX(Util.doubleToEven(l * (e - s) / 2.0));
        for (int i = 0; i < darkLineCount; i++) {
            getChildren().get(i).setTranslateX(Util.doubleToEven(l * (e + i - s / 2.0)));
        }
    }
}