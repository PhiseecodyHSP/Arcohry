package io.github.phiseecodyhsp.arcstory.storyMode;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.phiseecodyhsp.arcstory.storage.Resources;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
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

//TODO
public class Story extends StackPane {
    public final boolean withCG;

    private final List<Item> items;
    private final ImageView lastCg = new ImageView();
    private final ImageView currentCg = new ImageView();
    private final Text text = new Text();
    private final TextPlayer player = new TextPlayer();

    public Story(String jsonPath) {
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
    }

    public void play(ChapterPane parent) {
        parent.getChildren().add(this);

        Item item = items.getFirst();
        if (Objects.equals(item.type, Item.CG_TYPE)) {

        } else {
            try {
                player.play(new String(Resources.ofStream(item.path).readAllBytes(), StandardCharsets.UTF_8));
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

    private static class TextPlayer extends TextFlow {
        private static final Font FONT = Resources.getFont(null, 10);
        private static final double INTERVAL = 0.2;
        private static final String SPACES = " ".repeat(5);

        private final List<Text> texts = new ArrayList<>();
        private Timeline timeline;

        private void play(String text) {
            text = text.replaceAll("(?m)^$", SPACES);
            System.out.println(text);

            getChildren().clear();
            texts.clear();

            for (char c : text.toCharArray()) {
                Text t = new Text(String.valueOf(c));
                t.setFont(FONT);
                t.setFill(Color.TRANSPARENT);
                getChildren().add(t);
                texts.add(t);
            }

            timeline = new Timeline();
            timeline.setCycleCount(texts.size());
            int[] index = {0};
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(INTERVAL), _ -> {
                if (index[0] < texts.size()) {
                    texts.get(index[0]).setFill(Color.WHITE);
                    index[0]++;
                }
            });

            timeline.getKeyFrames().add(keyFrame);
            timeline.play();
        }

        private void skip() {
            if (timeline != null) {
                timeline.stop();
                timeline = null;
                texts.forEach(t -> t.setFill(Color.WHITE));
            }
        }
    }
}
