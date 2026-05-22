package io.github.phiseecodyhsp.arcstory.storyMode;

import io.github.phiseecodyhsp.arcstory.SetStage;
import io.github.phiseecodyhsp.arcstory.Util;
import io.github.phiseecodyhsp.arcstory.storage.Resources;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

//TODO
public class ChapterSelectionPane extends StackPane {
    private static final double TRANS_TIME = 1;

    private int currentActPaneNumber = 0;
    private final ImageView neo = new ImageView(Resources.NEW_ICON);
    private final StackPane actPanePane = new StackPane();
    private final List<ActPane> actPanes = new ArrayList<>();

    public ChapterSelectionPane(ActPane... actPanes) {
        ImageView lastBg = new ImageView();
        lastBg.setOpacity(0);
        lastBg.setPreserveRatio(true);
        lastBg.setFitWidth(Util.getScreenWidth());
        ImageView currentBg = new ImageView();
        currentBg.setPreserveRatio(true);
        currentBg.setFitWidth(Util.getScreenWidth());

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

        FadeTransition out = new FadeTransition(Duration.seconds(TRANS_TIME), lastBg);
        out.setFromValue(1);
        out.setToValue(0);
        FadeTransition in = new FadeTransition(Duration.seconds(TRANS_TIME), currentBg);
        in.setFromValue(0);
        in.setToValue(1);

        Polygon leftArrow = new Polygon(
        );

        Polygon rightArrow = new Polygon(
        );

        leftArrow.setOpacity(StoryButton.LOWEST_OPACITY);
        leftArrow.setFill(Color.BLACK);
        leftArrow.setTranslateX(0);
        leftArrow.setEffect(StoryButton.SHADOW);
        leftArrow.setOnMouseEntered(_ -> {
            leftArrow.setFill(Color.BLACK);
            leftArrow.setOpacity(StoryButton.LOWEST_OPACITY);
        });
        leftArrow.setOnMouseExited(_ -> {
            leftArrow.setFill(Color.WHITE);
            leftArrow.setOpacity(1);
        });
        leftArrow.setOnMouseClicked(_ -> {
            toLeft.play();
            currentActPaneNumber--;
            lastBg.setImage(currentBg.getImage());
            currentBg.setImage(new Image(this.actPanes.get(currentActPaneNumber).bgPath));
            out.play();
            in.play();
            if (currentActPaneNumber == 0) {
                leftArrow.setFill(Color.BLACK);
                leftArrow.setOpacity(StoryButton.LOWEST_OPACITY);
                leftArrow.setMouseTransparent(true);
            }
            if (currentActPaneNumber == this.actPanes.size() - 2) {
                rightArrow.setFill(Color.WHITE);
                rightArrow.setOpacity(1);
                rightArrow.setMouseTransparent(false);
            }
        });

        rightArrow.setTranslateX(0);
        rightArrow.setFill(Color.WHITE);
        rightArrow.setEffect(StoryButton.SHADOW);
        rightArrow.setOnMouseClicked(_ -> {
            toRight.play();
            currentActPaneNumber++;
            lastBg.setImage(currentBg.getImage());
            currentBg.setImage(new Image(this.actPanes.get(currentActPaneNumber).bgPath));
            out.play();
            in.play();
            if (currentActPaneNumber == this.actPanes.size() - 1) {
                rightArrow.setFill(Color.BLACK);
                rightArrow.setOpacity(StoryButton.LOWEST_OPACITY);
                rightArrow.setMouseTransparent(true);
            }
            if (currentActPaneNumber == 1) {
                leftArrow.setFill(Color.WHITE);
                leftArrow.setOpacity(1);
                leftArrow.setMouseTransparent(false);
            }
        });
        rightArrow.setOnMouseEntered(_ -> {
            rightArrow.setFill(Color.BLACK);
            rightArrow.setOpacity(StoryButton.LOWEST_OPACITY);
        });
        rightArrow.setOnMouseExited(_ -> {
            rightArrow.setFill(Color.WHITE);
            rightArrow.setOpacity(1);
        });

        getChildren().addAll(lastBg, currentBg, actPanePane, leftArrow, rightArrow);
        addAll(actPanes);
    }

    public void addAll(ActPane... actPanes) {
        actPanePane.getChildren().addAll(actPanes);
        this.actPanes.addAll(List.of(actPanes));

        for (int i = 0; i < actPanePane.getChildren().size(); i++) {
            this.actPanes.get(i).numberLabel.setText(Util.intToRoman(i + 1));
            this.actPanes.get(i).setTranslateX(i * SetStage.WIDTH);
        }
    }
}
