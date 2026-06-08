package io.github.phiseecodyhsp.arcstory.storyMode;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.phiseecodyhsp.arcstory.Util;
import io.github.phiseecodyhsp.arcstory.storage.Chart;
import io.github.phiseecodyhsp.arcstory.storage.Partner;
import io.github.phiseecodyhsp.arcstory.storage.Resources;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static io.github.phiseecodyhsp.arcstory.storyMode.ChapterPane.StoryButtonPane.StoryButton.OUTER_GLOW_INTENSITY;
import static io.github.phiseecodyhsp.arcstory.storyMode.ChapterPane.StoryButtonPane.StoryButton.OUTER_GLOW_OFFSET;

//超绝双重内部类
public class ChapterPane extends StackPane {
    private final List<StoryButtonPane> storyButtonPanes = new ArrayList<>();
    private final StackPane innerPane = new StackPane();

    public ChapterPane(@NotNull String bgPath, StoryButtonPane... buttonPanes) {
        ImageView bg = new ImageView(bgPath);
        bg.setPreserveRatio(true);
        bg.setFitWidth(Util.PRIMARY_SCREEN_WIDTH);
        innerPane.setAlignment(Pos.CENTER_LEFT);
        innerPane.setMaxSize(0, 0);

        getChildren().addAll(bg, innerPane);
        addAll(buttonPanes);
    }

    private void addAll(StoryButtonPane... buttonPanes) {
        innerPane.getChildren().addAll(buttonPanes);
        storyButtonPanes.addAll(List.of(buttonPanes));

        int s = storyButtonPanes.size();
        double d = StoryButtonPane.StoryButton.DIAGONAL_LENGTH;
        for (int i = 0; i < s; i++) {
            storyButtonPanes.get(i).setTranslateY((2 * i - s + 1) * d);
        }
    }

    public List<StoryButtonPane> getStoryButtonPanes() {
        return Collections.unmodifiableList(storyButtonPanes);
    }

    public class StoryButtonPane extends StackPane {
        private final Rectangle lightLine = new Rectangle();
        private final StackPane partner;
        private final ChapterPane parent = ChapterPane.this;
        private final List<StoryButton> storyButtons = new ArrayList<>();

        private int darkLineCount;

        //TODO: Title
        public StoryButtonPane(String partnerPath, String title) {
            lightLine.setFill(Color.WHITE);
            lightLine.setHeight(StoryButton.BORDER_WIDTH);

            if (partnerPath != null) {
                partner = Partner.getAvatarPane(
                        partnerPath,
                        StoryButton.SIDE_LENGTH,
                        Color.rgb(150, 140, 160),
                        new DropShadow(
                                OUTER_GLOW_INTENSITY,
                                OUTER_GLOW_OFFSET,
                                OUTER_GLOW_OFFSET,
                                StoryButton.TRANSPARENT_BLACK));
                getChildren().addAll(lightLine, partner);
            } else {
                partner = null;
                getChildren().add(lightLine);
            }
            setMaxSize(0, 0);

            parent.addAll(this);
        }

        public StoryButtonPane(Partner partner, String title) {
            this(partner.avatarPath(), title);
        }

        //TODO: 展示UnlockCondition时会发生微小位移
        private void addAll(StoryButton... buttons) {
            if (buttons.length != 0) {
                getChildren().addAll(buttons);
                storyButtons.addAll(List.of(buttons));

                int s = storyButtons.size();
                int l = StoryButton.SIDE_LENGTH + StoryButton.DIAGONAL_LENGTH;
                int d = 2 - s;
                if (partner != null) {
                    partner.setTranslateX(Util.doubleToEven(l * ((2 - s) / 2.0 - 1)));
                } else {
                    d = 1 - s;
                }
                for (int i = 0; i < s; i++) {
                    StoryButton button = storyButtons.get(i);
                    button.setTranslateX(Util.doubleToEven(l * (i + (d) / 2.0)));
                    if (button.unlocked && i + 1 < s) {
                        storyButtons.get(i + 1).enable();
                    }
                }
                updateLine();
            }
        }

        //TODO
        public void updateLine() {
            long e = storyButtons.stream().filter(b -> b.enabled).count();
            int s = storyButtons.size();
            double l = StoryButton.SIDE_LENGTH + StoryButton.DIAGONAL_LENGTH;
            long d = s - e;

            while (darkLineCount < d) {
                darkLineCount++;
                Rectangle darkLine = new Rectangle(
                        StoryButton.SIDE_LENGTH + 7, StoryButton.BORDER_WIDTH, Color.WHITE);
                darkLine.setOpacity(StoryButton.LOWEST_BRIGHTNESS);
                getChildren().addFirst(darkLine);
            }
            while (darkLineCount > d) {
                darkLineCount--;
                getChildren().removeFirst();
            }

            if (partner != null) {
                e++;
                s++;
            }
            lightLine.setWidth(l * (e - 1));
            lightLine.setTranslateX(Util.doubleToEven(l * (e - s) / 2.0));
            for (int i = 0; i < darkLineCount; i++) {
                getChildren().get(i).setTranslateX(Util.doubleToEven(l * (e + i - s / 2.0)));
            }
        }

        public List<StoryButton> getStoryButtons() {
            return Collections.unmodifiableList(storyButtons);
        }

        public class StoryButton extends StackPane {
            public static final int SIDE_LENGTH = Util.doubleToEven(Util.PRIMARY_SCREEN_HEIGHT * 0.1);
            public static final int BORDER_WIDTH = 2;
            public static final int DIAGONAL_LENGTH = Util.doubleToEven(SIDE_LENGTH * Util.SQRT_2);
            public static final int ARC_SIZE = 5;
            private static final int IMAGE_SIZE = SIDE_LENGTH - 2 * BORDER_WIDTH;
            public static final double HIGHEST_DARKNESS = 0.25;
            public static final double LOWEST_BRIGHTNESS = 1 - HIGHEST_DARKNESS;
            public static final int OUTER_GLOW_INTENSITY = 10;
            public static final int OUTER_GLOW_OFFSET = 10;
            public static final Color TRANSPARENT_BLACK = new Color(0, 0, 0, 0.5);
            public static final int NEW_ICON_SIZE = Util.doubleToEven(SIDE_LENGTH / 3.0);
            private static final Font FONT =
                    Resources.getFont(Resources.GeosansLight_FONT, DIAGONAL_LENGTH / 4.0);
            public static final DropShadow SHADOW = new DropShadow(
                    OUTER_GLOW_INTENSITY, OUTER_GLOW_OFFSET, OUTER_GLOW_OFFSET, TRANSPARENT_BLACK);

            private boolean enabled = false;
            private boolean unlocked = false;
            private final List<StoryPlayer.Item> items;
            private final Label label;
            private final ImageView lock = new ImageView(Resources.Init_ILLUSTRATION);
            private final ImageView neo = new ImageView(Resources.NEW_ICON);
            private final StoryButtonPane parent = StoryButtonPane.this;
            private final Rectangle border = new Rectangle(SIDE_LENGTH, SIDE_LENGTH, Color.WHITE);
            private final Chart chart;
            private final Partner partner;

            public StoryButton(String title,
                               @NotNull String illustrationPath,
                               @NotNull String storyPath,
                               Chart chart,
                               Partner partner) {
                label = new Label(title);
                label.setFont(FONT);
                label.setTextFill(Color.WHITE);
                label.setRotate(-45);
                label.setEffect(SHADOW);
                label.setMouseTransparent(true);

                Polygon lockBg = new Polygon(
                        -IMAGE_SIZE / 2.0, IMAGE_SIZE / 4.0,
                        -IMAGE_SIZE / 2.0, IMAGE_SIZE / 2.0,
                        -IMAGE_SIZE / 4.0, IMAGE_SIZE / 2.0,
                        IMAGE_SIZE / 2.0, -IMAGE_SIZE / 4.0,
                        IMAGE_SIZE / 2.0, -IMAGE_SIZE / 2.0,
                        IMAGE_SIZE / 4.0, -IMAGE_SIZE / 2.0);
                lockBg.setFill(Color.BLACK);
                lockBg.setOpacity(0.5);
                lockBg.setMouseTransparent(true);

                neo.setPreserveRatio(true);
                neo.setFitWidth(NEW_ICON_SIZE);
                neo.setTranslateY(Util.doubleToEven(-SIDE_LENGTH * 7 / 12.0));
                neo.setMouseTransparent(true);

                ImageView view = new ImageView(illustrationPath);
                view.setFitWidth(IMAGE_SIZE);
                view.setPreserveRatio(true);
                view.setMouseTransparent(true);
                lock.setRotate(-45);
                lock.setFitWidth(DIAGONAL_LENGTH / 3.0);
                lock.setPreserveRatio(true);
                lock.setMouseTransparent(true);

                ColorAdjust darken = new ColorAdjust();
                darken.setBrightness(-HIGHEST_DARKNESS);
                border.setArcWidth(ARC_SIZE);
                border.setArcHeight(ARC_SIZE);
                border.setEffect(SHADOW);
                border.setOnMouseEntered(_ -> view.setEffect(darken));
                border.setOnMouseExited(_ -> view.setEffect(null));

                setOpacity(LOWEST_BRIGHTNESS);
                setMaxSize(0, 0);
                setRotate(45);
                setMouseTransparent(false);
                getChildren().addAll(border, view, lockBg, lock);
                this.chart = chart;
                if (this.chart == null) {
                    unlock();
                    this.partner = null;
                } else {
                    this.partner = partner;
                }

                boolean[] withCg = {false};
                try {
                    items = new ObjectMapper().readValue(Resources.ofStream(storyPath), new TypeReference<>() {});
                } catch (IOException e) {
                    throw new UncheckedIOException("Failed to read '" + storyPath + "'", e);
                }
                items.forEach(i -> {
                    String type = i.getType();
                    if (!Objects.equals(type, StoryPlayer.Item.CG_TYPE) &&
                            !Objects.equals(type, StoryPlayer.Item.TEXT_TYPE)) {
                        throw new IllegalStateException(
                                "A " + StoryPlayer.Item.class.getSimpleName() + "'s type must be \"cg\" or \"text\", " +
                                        "but found \"" + type + "\" in '" + i.getPath() +"'");
                    }
                    if (type.equals(StoryPlayer.Item.CG_TYPE)) {
                        withCg[0] = true;
                    }
                });

                if (withCg[0]) {
                    ImageView star = new ImageView(Resources.Init_ILLUSTRATION);
                    star.setPreserveRatio(true);
                    star.setFitWidth(Util.doubleToEven(SIDE_LENGTH / 4.0));
                    star.setTranslateX(Util.doubleToEven(SIDE_LENGTH * 2 / 7.0));
                    star.setTranslateY(Util.doubleToEven(SIDE_LENGTH * 2 / 7.0));
                    star.setMouseTransparent(true);
                    getChildren().add(star);
                }

                parent.addAll(this);
            }

            public boolean isNew() {
                return getChildren().contains(neo);
            }

            private void enable() {
                if (!enabled) {
                    setOpacity(1);
                    if (chart != null) {
                        if (partner != null) {
                            border.setOnMouseClicked(_ ->
                                    Util.CONDITION_DISPLAYER.display(parent.parent, chart, partner));
                        } else {
                            border.setOnMouseClicked(_ ->
                                    Util.CONDITION_DISPLAYER.display(parent.parent, chart));
                        }
                    }
                    parent.updateLine();
                    enabled = true;
                }
            }

            private void unlock() {
                if (!unlocked) {
                    enable();
                    border.setOnMouseClicked(_ -> {
                        Util.STORY_PLAYER.play(parent.parent, items);
                        getChildren().remove(neo);
                        border.setOnMouseClicked(_ -> Util.STORY_PLAYER.play(parent.parent, items));
                    });
                    getChildren().remove(lock);
                    getChildren().addAll(label, neo);
                    unlocked = true;
                }
            }
        }
    }
}
