package io.github.phiseecodyhsp.arcstory.storyMode;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.phiseecodyhsp.arcstory.Util;
import io.github.phiseecodyhsp.arcstory.storage.Resources;
import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.github.phiseecodyhsp.arcstory.storyMode.ChapterPane.StoryButtonPane.StoryButton.*;
import static io.github.phiseecodyhsp.arcstory.storyMode.StoryUnlockConditionDisplayer.TRANS_TIME;

public class StoryPlayer extends StackPane {
    private static final int SWEEP_LINE_ROTATE = 20;
    private static final double TAN = Math.tan(Math.toRadians(90 - SWEEP_LINE_ROTATE));
    private static final double FIT_WIDTH = Util.getScreenWidth();

    private List<Item> items;
    private final Polygon sweepLine = new Polygon(
            -Util.getScreenWidth() / 40, 0,
            -Util.getScreenWidth() / 40 - Util.getScreenHeight() / TAN, Util.getScreenHeight(),
            -Util.getScreenHeight() / TAN, Util.getScreenHeight(),
            0, 0);
    private final Polygon clipper = new Polygon(
            -Util.getScreenWidth() / 40, 0,
            -Util.getScreenWidth() / 40 - Util.getScreenHeight() / TAN, Util.getScreenHeight(),
            -Util.getScreenHeight() / TAN, Util.getScreenHeight(),
            0, 0);
    private final ImageView lastCg = new ImageView();
    private final ImageView currentCg = new ImageView();
    private final TextPlayer textPlayer = new TextPlayer();
    private final Rectangle shadow = new Rectangle(Util.getScreenWidth(), Util.getScreenHeight(), Color.BLACK);
    private final StackPane textPane = new StackPane(shadow, textPlayer);
    private final FadeTransition onRemoved = new FadeTransition(Duration.seconds(TRANS_TIME * 2), this);
    private final FadeTransition onShadowAdded = new FadeTransition(Duration.seconds(TRANS_TIME), shadow);
    private final Timeline onCgAdded;

    private ChapterPane parent;
    private int currentlyPlaying;

    public StoryPlayer() {
        lastCg.setPreserveRatio(true);
        lastCg.setFitWidth(FIT_WIDTH);

        ColorAdjust colorAdjust = new ColorAdjust();
        currentCg.setPreserveRatio(true);
        currentCg.setFitWidth(FIT_WIDTH);
        currentCg.setClip(clipper);
        currentCg.setEffect(colorAdjust);

        DropShadow dropShadow = new DropShadow(0, Color.WHITE);
        sweepLine.setFill(Color.WHITE);
        sweepLine.setEffect(dropShadow);

        DoubleProperty x3 = new SimpleDoubleProperty();
        DoubleProperty x4 = new SimpleDoubleProperty();
        x3.addListener((_, _, x) ->
                clipper.getPoints().set(4, x.doubleValue()));
        x4.addListener((_, _, x) ->
                clipper.getPoints().set(6, x.doubleValue()));

        onCgAdded = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(x3, -Util.getScreenWidth() / 40 - Util.getScreenHeight() / TAN),
                        new KeyValue(x4, -Util.getScreenWidth() / 40),
                        new KeyValue(
                                sweepLine.translateXProperty(),
                                -Util.getScreenWidth() * 41 / 80 - Util.getScreenHeight() / 2 / TAN),
                        new KeyValue(dropShadow.radiusProperty(), 127),
                        new KeyValue(colorAdjust.brightnessProperty(), 1)),
                new KeyFrame(Duration.seconds(TRANS_TIME * 4),
                        new KeyValue(x3, Util.getScreenWidth(), Util.EASE_IN),
                        new KeyValue(x4, Util.getScreenWidth() + Util.getScreenHeight() / TAN, Util.EASE_IN),
                        new KeyValue(
                                sweepLine.translateXProperty(),
                                Util.getScreenWidth() * 41 / 80 + Util.getScreenHeight() / 2 / TAN,
                                Util.EASE_IN),
                        new KeyValue(colorAdjust.brightnessProperty(), 0),
                        new KeyValue(dropShadow.radiusProperty(), 0)));
        onCgAdded.setOnFinished(_ -> currentCg.setOnMouseClicked(_ -> playNext()));

        onShadowAdded.setFromValue(0);
        onShadowAdded.setToValue(LOWEST_BRIGHTNESS);
        onRemoved.setToValue(0);
    }

    public void play(ChapterPane parent, List<Item> items) {
        this.parent = parent;
        this.items = items;
        this.parent.getChildren().add(this);
        onRemoved.setOnFinished(_ -> this.parent.getChildren().remove(this));
        getChildren().clear();
        setOpacity(1);
        currentlyPlaying = 0;
        play(currentlyPlaying);
    }

    private void playNext() {
        currentlyPlaying++;
        if (currentlyPlaying < items.size()) {
            play(currentlyPlaying);
        } else {
            currentCg.setOnMouseClicked(null);
            shadow.setOnMouseClicked(null);
            onRemoved.playFromStart();
        }
    }

    private void playLast() {
        currentlyPlaying--;
        play(currentlyPlaying);
    }

    private void play(int num) {
        Item item = items.get(num);
        if (!item.isText()) {
            if (num != 0 && !items.get(num - 1).isText()) {
                lastCg.setImage(currentCg.getImage());
                entopAll(lastCg);
            }
            Image image = new Image(Resources.ofString(item.path));
            currentCg.setImage(image);
            currentCg.setOnMouseClicked(null);

            double h = FIT_WIDTH * image.getHeight() / image.getWidth();
            clipper.setTranslateY((h - Util.getScreenHeight()) / 2);

            shadow.setOnMouseClicked(null);
            entopAll(currentCg, sweepLine);
            onCgAdded.playFromStart();
        } else {
            textPlayer.clear();
            if (num == 0) {
                getChildren().add(textPane);
                shadow.setOnMouseClicked(null);
                onShadowAdded.playFromStart();
                onShadowAdded.setOnFinished(_ -> playText(item.path));
            } else {
                if (items.get(num - 1).isText()) {
                    playText(item.path);
                } else {
                    entopAll(lastCg, textPane);
                    lastCg.setImage(currentCg.getImage());
                    shadow.setOnMouseClicked(null);
                    onShadowAdded.playFromStart();
                    onShadowAdded.setOnFinished(_ -> playText(item.path));
                }
            }
        }
    }

    private void playText(String path) {
        try {
            textPlayer.play(new String(Resources.ofStream(path).readAllBytes(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read '" + path + "'", e);
        }
    }

    private void entopAll(Node... nodes) {
        getChildren().removeAll(nodes);
        getChildren().addAll(nodes);
    }

    public static class Item {
        public static final String CG_TYPE = "cg";
        public static final String TEXT_TYPE = "text";

        @JsonProperty
        private String type;
        @JsonProperty
        private String path;

        private Item() {}

        private Item(String type, String path) {
            this.type = type;
            this.path = path;
        }

        public String getType() {
            return type;
        }

        public String getPath() {
            return path;
        }

        private boolean isText() {
            return Objects.equals(type, TEXT_TYPE);
        }
    }

    private class TextPlayer extends TextFlow {
        private static final double FONT_PX = DIAGONAL_LENGTH / 5.0;
        private static final double LINE_SPACING = FONT_PX / 2.333;
        private static final double DEFAULT_LINE_SPACING = 26.5;
        private static final Font FONT = Resources.getFont("fonts/NotoSansCJKsc-Regular.ttf", FONT_PX);
        private static final double INTERVAL = 0.05;
        private static final String SPACES = " ".repeat((int) (2 / INTERVAL));

        private final List<Text> texts = new ArrayList<>();

        private Timeline timeline;

        private TextPlayer() {
            setMaxHeight(0);
            setLineSpacing(LINE_SPACING - DEFAULT_LINE_SPACING);
            setTranslateX(FONT_PX * 3);
            setMouseTransparent(true);
        }

        private void play(String text) {
            if (timeline != null) {
                timeline.stop();
                timeline.getKeyFrames().clear();
                timeline.setOnFinished(null);
                timeline = null;
            }

            text = text.replaceAll("(?m)^$", SPACES);
            for (char c : text.toCharArray()) {
                Text t = new Text(String.valueOf(c));
                t.setFont(FONT);
                t.setFill(Color.TRANSPARENT);
                getChildren().add(t);
                texts.add(t);
            }

            int s = texts.size();
            int[] index = {0};
            timeline = new Timeline(new KeyFrame(Duration.seconds(INTERVAL), _ -> {
                if (timeline != null && index[0] < s) {
                    texts.get(index[0]).setFill(Color.WHITE);
                    requestLayout();
                    index[0]++;
                }
            }));
            timeline.setCycleCount(s);
            timeline.setOnFinished(_ ->
                    shadow.setOnMouseClicked(_ -> StoryPlayer.this.playNext()));
            timeline.play();

            shadow.setOnMouseClicked(_ -> {
                if (timeline != null) {
                    timeline.stop();
                    timeline = null;
                }
                for (Text t : texts) {
                    t.setFill(Color.WHITE);
                }
                requestLayout();
                shadow.setOnMouseClicked(_ -> StoryPlayer.this.playNext());
            });
        }

        private void clear() {
            getChildren().clear();
            texts.clear();
        }
    }
}
