package io.github.phiseecodyhsp.arcstory;

import io.github.phiseecodyhsp.arcstory.storage.Resources;
import javafx.animation.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static io.github.phiseecodyhsp.arcstory.storyMode.ChapterPane.StoryButtonPane.StoryButton.*;

//TODO
public class PotentialCalculator extends StackPane {
    private static final int PM_SCORE = 10000000;
    private static final int EX_SCORE = 9800000;
    private static final int AA_SCORE = 9500000;

    public PotentialCalculator() {
        FancyTextField rating = new FancyTextField();
        rating.setTranslateY(-200);
        rating.setMaxSize(400, 40);

        FancyTextField acc = new FancyTextField();
        acc.setTranslateY(-100);
        acc.setMaxSize(400, 40);

        RectangleButton button = new RectangleButton(100, 50, Color.WHITE, Color.GRAY, "BACK");
        button.setOnMouseClicked(_ -> Util.getSetStage(this).switchBack());
        getChildren().addAll(button, rating, acc);
    }

    private static class FancyTextField extends StackPane {
        private final TextField content = new TextField();

        private FancyTextField() {
            Rectangle rectangle = new Rectangle();
            rectangle.widthProperty().bind(this.widthProperty());
            rectangle.setHeight(4);
            rectangle.setFill(Color.color(0, 0.4375, 0.75));
            rectangle.translateYProperty().bind(this.heightProperty().subtract(rectangle.heightProperty()).divide(2D));
            rectangle.setScaleX(0D);

            // 三次缓动动画, 支持正向与逆向播放的衔接
            AnimationTimer timer = new AnimationTimer() {
                private static final double DURATION = 0.5D;
                private double progress = 0D;
                private long preview = Long.MIN_VALUE;

                @Override
                public void handle(long now) {
                    if (preview == Long.MIN_VALUE) {
                        preview = now;
                        return;
                    }
                    double deltaSec = (now - preview) / 1000000000D;
                    if (content.isFocused()) {
                        // P = 1 - (1 - t / duration)^3, 0 <= t <= duration
                        double t0 = (1D - Math.cbrt(1D - this.progress)) * DURATION;
                        double t = Math.clamp(t0 + deltaSec, 0D, DURATION);
                        double d = 1D - t / DURATION;
                        this.progress = 1D - d * d * d;
                        rectangle.setScaleX(this.progress);
                        if (this.progress == 1D) {
                            this.stop();
                        }
                    } else {
                        // P = (t / duration)^3, 0 <= t <= duration
                        double t0 = Math.cbrt(this.progress) * DURATION;
                        double t = Math.clamp(t0 - deltaSec, 0D, DURATION);
                        double d = t / DURATION;
                        this.progress = d * d * d;
                        rectangle.setScaleX(this.progress);
                        if (this.progress == 0D) {
                            this.stop();
                        }
                    }
                    this.preview = now;
                }

                @Override
                public void start() {
                    super.start();
                    this.preview = Long.MIN_VALUE;
                }
            };

            this.content.prefWidthProperty().bind(this.widthProperty());
            this.content.setFont(Resources.getFont(Resources.GeosansLight_FONT, 32));
            this.content.setBackground(Background.EMPTY);
            this.content.setBorder(Border.EMPTY);
            this.content.setStyle("-fx-text-fill: white;");
            this.content.focusedProperty().addListener((_, _, _) ->
                    timer.start());

            this.getChildren().addAll(this.content, rectangle);
        }
    }

    private static class RectangleButton extends StackPane {
        private RectangleButton(double width, double height, Color rectangleFill, Color borderFill, String text) {
            Label label = new Label(text);
            label.setFont(new Font(null, height / 3 / 0.75));

            Rectangle rectangle = new Rectangle(width - BORDER_WIDTH, height - BORDER_WIDTH, rectangleFill);

            Rectangle border = new Rectangle(width, height, borderFill);
            border.setArcHeight(ARC_SIZE);

            Rectangle mask = new Rectangle(width - BORDER_WIDTH, height - BORDER_WIDTH, Color.BLACK);
            mask.setOpacity(0);

            setOnMouseEntered(_ -> mask.setOpacity(HIGHEST_DARKNESS));
            setOnMouseExited(_ -> mask.setOpacity(0));

            setMaxSize(0, 0);
            getChildren().addAll(border, rectangle, label, mask);
        }
    }

    public static double getPtt(double rating, int score) {
        if (score >= PM_SCORE) {
            return rating + 2;
        }
        if (score >= EX_SCORE) {
            return rating + 1 + (score - EX_SCORE) / 200000.0;
        }
        return rating + (score - AA_SCORE) / 300000.0;
    }

    public static int getScore(double rating, double ptt) {
        if (ptt == rating + 2) {
            return PM_SCORE;
        }
        if (ptt >= rating + 1) {
            return (int) Math.ceil((ptt - rating - 1) * 200000 + EX_SCORE + 1);
        }
        return (int) Math.ceil((ptt - rating) * 300000 + AA_SCORE + 1);
    }

    public static double getRating(double ptt, int score) {
        if (score >= PM_SCORE) {
            return ceilToOneDecimalPlace(ptt - 2);
        }
        if (score >= EX_SCORE) {
            return ceilToOneDecimalPlace(ptt - 1 - (score - EX_SCORE) / 200000.0);
        }
        return ceilToOneDecimalPlace(ptt - (score - AA_SCORE) / 300000.0);
    }

    private static double ceilToOneDecimalPlace(double x) {
        return BigDecimal.valueOf(x).setScale(1, RoundingMode.CEILING).doubleValue();
    }
}
