package io.github.phiseecodyhsp.arcstory.storyMode;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.phiseecodyhsp.arcstory.Util;
import io.github.phiseecodyhsp.arcstory.storage.Resources;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
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
    private List<Item> items;
    private final ImageView lastCg = new ImageView();
    private final ImageView currentCg = new ImageView();
    private final TextPlayer textPlayer = new TextPlayer();
    private final Rectangle shadow = new Rectangle(Util.getScreenWidth(), Util.getScreenHeight(), Color.BLACK);
    private final StackPane textPane = new StackPane(shadow, textPlayer);
    private final FadeTransition onRemoved = new FadeTransition(Duration.seconds(TRANS_TIME * 2), this);
    private final FadeTransition onShadowAdded = new FadeTransition(Duration.seconds(TRANS_TIME), shadow);

    private ChapterPane parent;
    private int currentlyPlaying;

    public StoryPlayer() {
        onShadowAdded.setFromValue(0);
        onShadowAdded.setToValue(LOWEST_OPACITY);
        onRemoved.setToValue(0);

        currentCg.setPreserveRatio(true);
        currentCg.setFitWidth(Util.getScreenWidth());
        currentCg.setOnMouseClicked(_ -> playNext());

        lastCg.setPreserveRatio(true);
        lastCg.setFitWidth(Util.getScreenWidth());
    }

    public void play(ChapterPane parent, List<Item> items) {
        this.parent = parent;
        this.items = items;
        this.parent.getChildren().add(this);
        currentlyPlaying = 0;
        play(currentlyPlaying);
    }

    private void playNext() {
        currentlyPlaying++;
        if (currentlyPlaying < items.size()) {
            play(currentlyPlaying);
        } else {
            onRemoved.playFromStart();
            onRemoved.setOnFinished(_ -> {
                this.parent.getChildren().remove(this);
                textPlayer.clear();
            });
        }
    }

    private void playLast() {
        currentlyPlaying--;
        if (currentlyPlaying >= 0) {
            play(currentlyPlaying - 1);
        }
    }

    //TODO
    private void play(int num) {
        Item item = items.get(num);
        if (Objects.equals(item.type, Item.CG_TYPE)) {

        } else {

        }
    }

    private void playText(String path) {
        try {
            textPlayer.play(new String(Resources.ofStream(path).readAllBytes(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read " + path, e);
        }
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
    }

    private class TextPlayer extends TextFlow {
        private static final double FONT_PX = DIAGONAL_LENGTH / 5.0;
        private static final double LINE_SPACING = FONT_PX / 2.333;
        private static final double DEFAULT_LINE_SPACING = 26.5;
        private static final Font FONT = Resources.getFont("fonts/NotoSansCJKsc-Regular.ttf", FONT_PX);
        private static final double INTERVAL = 0.05;
        private static final String SPACES = " ".repeat((int) (2 / INTERVAL));

        private final List<Text> texts = new ArrayList<>();
        private final Timeline timeline = new Timeline();

        private TextPlayer() {
            setMaxHeight(0);
            setLineSpacing(LINE_SPACING - DEFAULT_LINE_SPACING);
            setTranslateX(FONT_PX * 3);
        }

        private void play(String text) {
            clear();
            text = text.replaceAll("(?m)^$", SPACES);
            for (char c : text.toCharArray()) {
                Text t = new Text(String.valueOf(c));
                t.setFont(FONT);
                t.setFill(Color.TRANSPARENT);
                getChildren().add(t);
                texts.add(t);
            }

            int[] index = {0};
            timeline.setCycleCount(texts.size());
            timeline.getKeyFrames().clear();
            timeline.setOnFinished(_ -> textPane.setOnMouseClicked(_ -> StoryPlayer.this.playNext()));
            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(INTERVAL), _ -> {
                        if (index[0] < texts.size()) {
                            texts.get(index[0]).setFill(Color.WHITE);
                            requestLayout();
                            index[0]++;
                        }
                    })
            );
            timeline.play();

            textPane.setOnMouseClicked(_ -> skip());
        }

        private void skip() {
            timeline.stop();
            for (Text t : texts) {
                t.setFill(Color.WHITE);
            }
            requestLayout();
            textPane.setOnMouseClicked(_ -> StoryPlayer.this.playNext());
        }

        private void clear() {
            getChildren().clear();
            texts.clear();
        }
    }
}
