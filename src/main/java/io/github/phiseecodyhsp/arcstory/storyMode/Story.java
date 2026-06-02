package io.github.phiseecodyhsp.arcstory.storyMode;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.phiseecodyhsp.arcstory.Util;
import io.github.phiseecodyhsp.arcstory.storage.Resources;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import jdk.swing.interop.SwingInterOpUtils;

import java.awt.*;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Story extends StackPane {
    public final boolean withCG;
    private final List<Item> items;
    private final ImageView lastCg = new ImageView();
    private final ImageView currentCg = new ImageView();
    private final Text text = new Text();
    private final TextPlayer textPlayer = new TextPlayer();
    private final Rectangle shadow = new Rectangle(Util.getScreenWidth(), Util.getScreenHeight(), Color.BLACK);
    private final StackPane textPane = new StackPane(shadow, textPlayer);

    private ChapterPane parent;
    private int currentlyPlaying;

    public Story(String jsonPath) {
        shadow.setOpacity(ChapterPane.StoryButtonPane.StoryButton.LOWEST_OPACITY);

        try {
            items = new ObjectMapper().readValue(Resources.ofStream(jsonPath), new TypeReference<>() {});
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read '" + jsonPath + "'", e);
        }
        withCG = Objects.equals(items.getFirst().type, Item.CG_TYPE);
        items.forEach(i -> {
            if (!Objects.equals(i.type, Item.CG_TYPE) && !Objects.equals(i.type, Item.TEXT_TYPE)) {
                throw new IllegalStateException(
                        "A " + Item.class.getSimpleName() + "'s type must be \"cg\" or \"text\", " +
                                "but found \"" + i.type + "\" in '" + i.path +"'");
            }
        });

        getChildren().add(textPane);
    }

    public void play(ChapterPane parent) {
        this.parent = parent;
        this.parent.getChildren().add(this);
        playScene(0);
        currentlyPlaying = 0;
    }

    private void playNext() {
        currentlyPlaying++;
        if (currentlyPlaying < items.size()) {
            playScene(currentlyPlaying);
        } else {
            this.parent.getChildren().remove(this);
        }
    }

    private void playLast() {
        playScene(currentlyPlaying - 1);
        currentlyPlaying--;
    }

    //TODO: playCg
    private void playScene(int num) {
        Item item = items.get(num);
        if (Objects.equals(item.type, Item.CG_TYPE)) {
            currentCg.setImage(new Image(item.path));
        } else {
            try {
                textPlayer.play(new String(Resources.ofStream(item.path).readAllBytes(), StandardCharsets.UTF_8));
            } catch (IOException e) {
                throw new UncheckedIOException("Failed to read " + item.path, e);
            }
        }
    }

    private static class Item {
        private static final String CG_TYPE = "cg";
        private static final String TEXT_TYPE = "text";

        @JsonProperty
        private String type;
        @JsonProperty
        private String path;

        private Item() {}

        private Item(String type, String path) {
            this.type = type;
            this.path = path;
        }
    }

    private class TextPlayer extends TextFlow {
        private static final double FONT_PX = ChapterPane.StoryButtonPane.StoryButton.DIAGONAL_LENGTH / 5.0;
        private static final double LINE_SPACING = FONT_PX / 2.333;
        private static final double DEFAULT_LINE_SPACING = 26.5;
        private static final Font FONT = Resources.getFont("fonts/NotoSansCJKsc-Regular.ttf", FONT_PX);
        private static final double INTERVAL = 0.05;
        private static final String SPACES = " ".repeat(40);

        private final List<Text> texts = new ArrayList<>();
        private final Timeline timeline = new Timeline();

        private TextPlayer() {
            setMaxHeight(0);
            setLineSpacing(LINE_SPACING - DEFAULT_LINE_SPACING);
            setTranslateX(FONT_PX * 3);
        }

        private void play(String text) {
            text = text.replaceAll("(?m)^$", SPACES);
            getChildren().clear();
            texts.clear();
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
            timeline.setOnFinished(_ -> textPane.setOnMouseClicked(_ -> Story.this.playNext()));
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
            textPane.setOnMouseClicked(_ -> Story.this.playNext());
        }
    }
}
