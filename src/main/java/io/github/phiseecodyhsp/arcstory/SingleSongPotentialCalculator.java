package io.github.phiseecodyhsp.arcstory;

import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import static io.github.phiseecodyhsp.arcstory.storyMode.StoryButton.*;

public class SingleSongPotentialCalculator extends StackPane {
    public SingleSongPotentialCalculator() {
        RectangleButton button = new RectangleButton(100, 50, Color.WHITE, Color.GRAY, "START");
        getChildren().addAll(button);
    }

    private static class RectangleButton extends StackPane{
        private RectangleButton(double width, double height, Color rectangleFill, Color borderFill, String text) {
            Label label = new Label(text);
            label.setFont(new Font(null, height / 3 / 0.75));

            Rectangle rectangle = new Rectangle(width - BORDER_WIDTH, height - BORDER_WIDTH, rectangleFill);

            Rectangle border = new Rectangle(width, height, borderFill);
            border.setArcHeight(ARC_SIZE);

            Rectangle mask = new Rectangle(width - BORDER_WIDTH, height - BORDER_WIDTH, Color.BLACK);
            mask.setOpacity(0);

            FadeTransition onEntered = new FadeTransition(Duration.seconds(FADETRANS_TIME), mask);
            FadeTransition onExited = new FadeTransition(Duration.seconds(FADETRANS_TIME), mask);
            onEntered.setToValue(MASK_HIGHEST_OPACITY);
            onExited.setToValue(0);
            setOnMouseEntered(_ -> {
                onExited.stop();
                onEntered.playFromStart();
            });
            setOnMouseExited(_ -> {
                onEntered.stop();
                onExited.playFromStart();
            });

            setMaxSize(0, 0);
            getChildren().addAll(border, rectangle, label, mask);
        }
    }

    public static double getSingleSongPtt(double rating, int score) {
        if (score >= 10000000) {
            return rating + 2;
        }
        if (score >= 9800000) {
            return rating + 1 + (score - 9800000) / 200000.0;
        }
        return rating + (score - 9500000) / 300000.0;
    }
}
