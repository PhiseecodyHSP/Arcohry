package io.github.phiseecodyhsp.arcstory.storyMode;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.phiseecodyhsp.arcstory.Util;
import io.github.phiseecodyhsp.arcstory.storage.Partner;
import io.github.phiseecodyhsp.arcstory.storage.Resources;
import javafx.animation.*;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
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
    private static final double FIT_WIDTH = Util.PRIMARY_SCREEN_WIDTH;
    private static final double SWEEP_LINE_WIDTH = Util.PRIMARY_SCREEN_WIDTH / 40;
    private static final ColorAdjust GLOW = new ColorAdjust();

    private List<Item> items;
    private final Polygon sweepLine = new Polygon(
            -SWEEP_LINE_WIDTH, 0,
            -SWEEP_LINE_WIDTH - Util.PRIMARY_SCREEN_HEIGHT / TAN, Util.PRIMARY_SCREEN_HEIGHT,
            -Util.PRIMARY_SCREEN_HEIGHT / TAN, Util.PRIMARY_SCREEN_HEIGHT,
            0, 0);
    private final Polygon clipper = new Polygon(
            -SWEEP_LINE_WIDTH, 0,
            -SWEEP_LINE_WIDTH - Util.PRIMARY_SCREEN_HEIGHT / TAN, Util.PRIMARY_SCREEN_HEIGHT,
            Util.PRIMARY_SCREEN_WIDTH, Util.PRIMARY_SCREEN_HEIGHT,
            Util.PRIMARY_SCREEN_WIDTH + Util.PRIMARY_SCREEN_HEIGHT / TAN, 0);
    private final ImageView lastCg = new ImageView();
    private final ImageView currentCg = new ImageView();
    private final TextPlayer textPlayer = new TextPlayer();
    private final Rectangle shadow = new Rectangle(Util.PRIMARY_SCREEN_WIDTH, Util.PRIMARY_SCREEN_HEIGHT, Color.BLACK);

    //TODO
    private final StackPane partnerAvatarPane = Partner.getAvatarPane(
            Resources.Tairitsu_AWAKEN_AVATAR,
            0,
            Color.WHITE, null);

    private final StackPane textPane = new StackPane(shadow, textPlayer, partnerAvatarPane);
    private final FadeTransition onRemoved = new FadeTransition(Duration.seconds(TRANS_TIME * 2), this);
    private final FadeTransition onShadowAdded = new FadeTransition(Duration.seconds(TRANS_TIME), shadow);

    //TODO: 这个动画很卡
    private final Timeline onCgAdded;

    private ChapterPane parent;
    private int lastPlayed;
    private int currentlyPlaying;

    //TODO: Partner and buttons
    public StoryPlayer() {
        lastCg.setPreserveRatio(true);
        lastCg.setFitWidth(FIT_WIDTH);

        currentCg.setPreserveRatio(true);
        currentCg.setFitWidth(FIT_WIDTH);
        currentCg.setClip(clipper);
        lastCg.setEffect(GLOW);
        currentCg.setEffect(GLOW);
        textPane.setEffect(GLOW);

        DropShadow sweepLineGlow = new DropShadow(0, Color.WHITE);
        DropShadow shadow1 = new DropShadow(0, Color.WHITE);
        DropShadow shadow2 = new DropShadow(0, Color.WHITE);
        sweepLineGlow.setInput(shadow1);
        shadow1.setInput(shadow2);
        sweepLine.setFill(Color.WHITE);
        sweepLine.setEffect(sweepLineGlow);

        double time = TRANS_TIME * 4;
        onCgAdded = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(
                        clipper.translateXProperty(),
                        -Util.PRIMARY_SCREEN_WIDTH - SWEEP_LINE_WIDTH - Util.PRIMARY_SCREEN_HEIGHT / TAN),
                        new KeyValue(
                                sweepLine.translateXProperty(),
                                -(Util.PRIMARY_SCREEN_WIDTH + SWEEP_LINE_WIDTH +
                                        Util.PRIMARY_SCREEN_HEIGHT / TAN) / 2),
                        new KeyValue(sweepLine.opacityProperty(), 0.5),
                        new KeyValue(sweepLineGlow.radiusProperty(), 127),
                        new KeyValue(shadow1.radiusProperty(), 127),
                        new KeyValue(shadow2.radiusProperty(), 127),
                        new KeyValue(GLOW.brightnessProperty(), 0)),
                new KeyFrame(Duration.seconds(time / 32),
                        new KeyValue(GLOW.brightnessProperty(), LOWEST_BRIGHTNESS)),
                new KeyFrame(Duration.seconds(time),
                        new KeyValue(clipper.translateXProperty(), 0, Util.EASE_IN),
                        new KeyValue(
                                sweepLine.translateXProperty(),
                                (Util.PRIMARY_SCREEN_WIDTH + SWEEP_LINE_WIDTH +
                                        Util.PRIMARY_SCREEN_HEIGHT / TAN) / 2,
                                Util.EASE_IN),
                        new KeyValue(sweepLine.opacityProperty(), 1),
                        new KeyValue(sweepLineGlow.radiusProperty(), 0),
                        new KeyValue(shadow1.radiusProperty(), 0),
                        new KeyValue(shadow2.radiusProperty(), 0),
                        new KeyValue(GLOW.brightnessProperty(), 0)));
        onCgAdded.setOnFinished(_ -> currentCg.setOnMouseClicked(_ -> playNext()));

        onShadowAdded.setFromValue(0);
        onShadowAdded.setToValue(LOWEST_BRIGHTNESS);
        onRemoved.setToValue(0);
    }

    public void play(ChapterPane parent, String partnerAvatarPath, List<Item> items) {
        this.parent = parent;
        this.items = items;
        this.parent.getChildren().add(this);
        onRemoved.setOnFinished(_ -> {
            this.parent.getChildren().remove(this);
            this.parent = null;
        });

        if (partnerAvatarPath != null) {
            Util.setPaneLastImage(textPane, partnerAvatarPath);
            allToTop(textPane, partnerAvatarPane);
        } else {
            textPane.getChildren().remove(partnerAvatarPane);
        }

        getChildren().clear();
        setOpacity(1);

        if (!items.isEmpty()) {
            this.parent.getBg().setEffect(GLOW);
            this.parent.getInnerPane().setEffect(GLOW);
            lastPlayed = -1;
            currentlyPlaying = 0;
            play(currentlyPlaying);
        } else {
            getChildren().add(textPane);
            onShadowAdded.setOnFinished(_ -> shadow.setOnMouseClicked(_ -> end()));
            onShadowAdded.playFromStart();
        }
    }

    public void play(ChapterPane parent, Partner partner, List<Item> items) {
        if (partner != null) {
            play(parent, partner.avatarPath(), items);
        } else {
            play(parent, (String) null, items);
        }
    }

    private void playNext() {
        lastPlayed = currentlyPlaying;
        currentlyPlaying++;
        if (currentlyPlaying < items.size()) {
            play(currentlyPlaying);
        } else {
            end();
        }
    }

    private void playLast() {
        lastPlayed = currentlyPlaying;
        currentlyPlaying--;
        play(currentlyPlaying);
    }

    private void play(int num) {
        Item item = items.get(num);
        if (num == 1) {
            this.parent.getBg().setEffect(null);
            this.parent.getInnerPane().setEffect(null);
        }
        if (!item.isText()) {
            if (lastPlayed >= 0 && !items.get(lastPlayed).isText()) {
                lastCg.setImage(currentCg.getImage());
                allToTop(this, lastCg);
            }
            Image image = new Image(Resources.ofString(item.path));
            currentCg.setImage(image);
            currentCg.setOnMouseClicked(null);

            double h = FIT_WIDTH * image.getHeight() / image.getWidth();
            clipper.setTranslateY((h - Util.PRIMARY_SCREEN_HEIGHT) / 2);

            shadow.setOnMouseClicked(null);
            allToTop(this, currentCg, sweepLine);
            onCgAdded.playFromStart();
        } else {
            if (lastPlayed < 0) {
                getChildren().add(textPane);
                shadow.setOnMouseClicked(null);
                onShadowAdded.playFromStart();
                onShadowAdded.setOnFinished(_ -> playText(item.path));
            } else {
                if (items.get(lastPlayed).isText()) {
                    playText(item.path);
                    //TODO: 在此处启用playLast()
                } else {
                    allToTop(this, lastCg, textPane);
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

    private void end() {
        currentCg.setOnMouseClicked(null);
        shadow.setOnMouseClicked(null);
        onRemoved.playFromStart();
    }

    private void allToTop(Pane pane, Node... nodes) {
        pane.getChildren().removeAll(nodes);
        pane.getChildren().addAll(nodes);
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
        private static final String SPACES = " ".repeat((int) (1.5 / INTERVAL));

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
                    shadow.setOnMouseClicked(_ -> {
                        clear();
                        StoryPlayer.this.playNext();
                    }));
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
                shadow.setOnMouseClicked(_ -> {
                    clear();
                    StoryPlayer.this.playNext();
                });
            });
        }

        private void clear() {
            getChildren().clear();
            texts.clear();
        }
    }
}
