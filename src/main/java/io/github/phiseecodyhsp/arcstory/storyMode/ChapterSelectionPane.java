package io.github.phiseecodyhsp.arcstory.storyMode;

import io.github.phiseecodyhsp.arcstory.Util;
import io.github.phiseecodyhsp.arcstory.storage.Resources;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Polygon;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

//TODO
public class ChapterSelectionPane extends StackPane {
    private final ImageView neo = new ImageView(Resources.NEW_ICON);
    private final List<ActPane> actPanes = new ArrayList<>();
    private final Polygon leftArrow = new Polygon(
    );
    private final Polygon rightArrow = new Polygon(
    );

    public ChapterSelectionPane(@NotNull String bgPath) {
        ImageView bg = new ImageView(bgPath);
        bg.setPreserveRatio(true);
        bg.setFitWidth(Util.getScreenWidth());

        //TODO
        neo.setPreserveRatio(true);
        neo.setFitWidth(StoryButton.NEW_ICON_SIZE);
        neo.setTranslateY(0);

        leftArrow.setEffect(StoryButton.SHADOW);
        rightArrow.setEffect(StoryButton.SHADOW);

        getChildren().addAll(bg, leftArrow, rightArrow);
    }

    public void addAll(ActPane... panes) {
        getChildren().addAll(panes);
        actPanes.addAll(List.of(panes));

        for (int i = 0; i < actPanes.size(); i++) {
            actPanes.get(i).numberLabel.setText(Util.intToRoman(i + 1));
        }
    }

    private static class ChapterButton extends StackPane {
        private ChapterButton(ChapterPane pane, @NotNull String imagePath) {
            ImageView view = new ImageView(imagePath);

            setOnMouseClicked(_ -> Util.getSetStage(this).switchPane(pane));
        }
    }

    private static class ActPane extends StackPane {
        private final Label numberLabel = new Label();

        private ActPane(String name, @NotNull String bgPath, ChapterButton... buttons) {
            Label nameLabel = new Label(name);
        }

        private void addAll(ChapterButton... buttons) {
            getChildren().addAll(buttons);
        }
    }
}
