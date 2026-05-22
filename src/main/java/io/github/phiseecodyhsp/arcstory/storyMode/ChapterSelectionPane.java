package io.github.phiseecodyhsp.arcstory.storyMode;

import io.github.phiseecodyhsp.arcstory.SetStage;
import io.github.phiseecodyhsp.arcstory.Util;
import io.github.phiseecodyhsp.arcstory.storage.Resources;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

//TODO
public class ChapterSelectionPane extends StackPane {
    private static final double TRANS_TIME = 1;

    private final ImageView neo = new ImageView(Resources.NEW_ICON);
    private final StackPane actPanePane = new StackPane();

    public ChapterSelectionPane(@NotNull String bgPath) {
        ImageView bg = new ImageView(bgPath);
        bg.setPreserveRatio(true);
        bg.setFitWidth(Util.getScreenWidth());

        //TODO
        neo.setPreserveRatio(true);
        neo.setFitWidth(StoryButton.NEW_ICON_SIZE);
        neo.setTranslateY(0);

        TranslateTransition toLeft = new TranslateTransition(Duration.seconds(TRANS_TIME), actPanePane);
        toLeft.setToX(-SetStage.WIDTH);
        toLeft.setInterpolator(Util.EASE_OUT);
        TranslateTransition toRight = new TranslateTransition(Duration.seconds(TRANS_TIME), actPanePane);
        toRight.setToX(SetStage.WIDTH);
        toLeft.setInterpolator(Util.EASE_IN);

        Polygon leftArrow = new Polygon(
        );
        leftArrow.setEffect(StoryButton.SHADOW);
        leftArrow.setOnMouseClicked(_ -> {

        });

        Polygon rightArrow = new Polygon(
        );
        rightArrow.setEffect(StoryButton.SHADOW);

        getChildren().addAll(bg, leftArrow, rightArrow);
    }

    public void addAll(ActPane... panes) {
        actPanePane.getChildren().addAll(panes);

        for (int i = 0; i < actPanePane.getChildren().size(); i++) {
            if (actPanePane.getChildren().get(i) instanceof ActPane pane) {
                pane.numberLabel.setText(Util.intToRoman(i + 1));
            }

            actPanePane.getChildren().get(i).setTranslateX(i * SetStage.WIDTH);
        }
    }
}
