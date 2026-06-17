package io.github.phiseecodyhsp.arcstory.storyMode;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.phiseecodyhsp.arcstory.Util;
import io.github.phiseecodyhsp.arcstory.storage.Chart;
import io.github.phiseecodyhsp.arcstory.storage.Partner;
import io.github.phiseecodyhsp.arcstory.storage.Resources;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    private final ImageView bg;

    public ChapterPane(@NotNull String bgPath) {
        bg = new ImageView(bgPath);
        bg.setPreserveRatio(true);
        bg.setFitWidth(Util.PRIMARY_SCREEN_WIDTH);
        innerPane.setAlignment(Pos.CENTER_LEFT);
        innerPane.setMaxSize(0, 0);

        getChildren().addAll(bg, innerPane);
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

    public StackPane getInnerPane() {
        return innerPane;
    }

    public ImageView getBg() {
        return bg;
    }

    public class StoryButtonPane extends StackPane {
        private final Rectangle lightLine = new Rectangle();
        private final StackPane partnerAvatarPane;
        private final ChapterPane parent = ChapterPane.this;
        private final List<StoryButton> storyButtons = new ArrayList<>();
        private final String partnerAvatarPath;

        private int darkLineCount;

        //TODO: Title
        public StoryButtonPane(String partnerAvatarPath, String title) {
            this.partnerAvatarPath = partnerAvatarPath;

            lightLine.setFill(Color.WHITE);
            lightLine.setHeight(StoryButton.BORDER_WIDTH);

            if (this.partnerAvatarPath != null) {
                partnerAvatarPane = Partner.getAvatarPane(
                        this.partnerAvatarPath,
                        StoryButton.SIDE_LENGTH,
                        Color.rgb(150, 140, 160),
                        new DropShadow(
                                OUTER_GLOW_INTENSITY,
                                OUTER_GLOW_OFFSET,
                                OUTER_GLOW_OFFSET,
                                StoryButton.TRANSPARENT_BLACK));
                getChildren().addAll(lightLine, partnerAvatarPane);
            } else {
                partnerAvatarPane = null;
                getChildren().add(lightLine);
            }
            setMaxSize(0, 0);

            parent.addAll(this);
        }

        public StoryButtonPane(Partner partner, String title) {
            this(partner.avatarPath(), title);
        }

        private void addAll(StoryButton... buttons) {
            getChildren().addAll(buttons);
            storyButtons.addAll(List.of(buttons));
            storyButtons.getFirst().enable();

            int s = storyButtons.size();
            double l = StoryButton.SIDE_LENGTH + StoryButton.DIAGONAL_LENGTH;
            int d = 2 - s;
            if (partnerAvatarPane != null) {
                partnerAvatarPane.setTranslateX(l * ((2 - s) / 2.0 - 1));
            } else {
                d = 1 - s;
            }
            for (int i = 0; i < s; i++) {
                StoryButton button = storyButtons.get(i);
                button.setTranslateX(l * (i + d / 2.0));
                if (button.unlocked && i + 1 < s) {
                    storyButtons.get(i + 1).enable();
                } else {
                    updateLine();
                }
            }
        }

        /**
         * 更新按钮之间的连线.
         *
         * <p>{@link #lightLine} 表示不透明的白色连线, 其只有一条并直接穿过多个按钮.
         * {@code darkLine} 表示透明的白色连线, 由于禁用的按钮也是透明的,
         * 其被设计为多条, 并只存在于两个按钮之间.
         */
        public void updateLine() {
            long enabledCount = storyButtons.stream().filter(b -> b.enabled).count();
            int totalCount = storyButtons.size();
            double w = StoryButton.SIDE_LENGTH + StoryButton.DIAGONAL_LENGTH;
            long disabledCount = totalCount - enabledCount;

            // 暗线数量小于禁用按钮数量时添加更多暗线
            while (darkLineCount < disabledCount) {
                darkLineCount++;
                Rectangle darkLine = new Rectangle(
                        StoryButton.SIDE_LENGTH + 7, StoryButton.BORDER_WIDTH, Color.WHITE);
                darkLine.setOpacity(StoryButton.LOWEST_BRIGHTNESS);
                getChildren().addFirst(darkLine);
            }

            // 大于的情况, 两个 while 实际上包括了不同的分支判断
            while (darkLineCount > disabledCount) {
                darkLineCount--;
                getChildren().removeFirst();
            }

            // Partner 算作一个按钮
            if (partnerAvatarPane != null) {
                enabledCount++;
                totalCount++;
            }
            lightLine.setWidth(w * (enabledCount - 1));
            lightLine.setTranslateX(w * (enabledCount - totalCount) / 2);
            for (int i = 0; i < darkLineCount; i++) {
                getChildren().get(i).setTranslateX(w * (enabledCount + i - totalCount / 2.0));
            }

            // 由于实际只有亮线会影响宽度, 这里要强制设置最小宽度以正确对齐
            setMinWidth(w * (totalCount - 1));
        }

        public List<StoryButton> getStoryButtons() {
            return Collections.unmodifiableList(storyButtons);
        }

        public class StoryButton extends StackPane {
            public static final double SIDE_LENGTH = Util.PRIMARY_SCREEN_HEIGHT * 0.1;
            public static final int BORDER_WIDTH = 2;
            public static final double DIAGONAL_LENGTH = SIDE_LENGTH * Util.SQRT_2;
            public static final int ARC_SIZE = 5;
            private static final double IMAGE_SIZE = SIDE_LENGTH - 2 * BORDER_WIDTH;
            public static final double HIGHEST_DARKNESS = 0.25;
            public static final double LOWEST_BRIGHTNESS = 1 - HIGHEST_DARKNESS;
            public static final int OUTER_GLOW_INTENSITY = 10;
            public static final int OUTER_GLOW_OFFSET = 10;
            public static final Color TRANSPARENT_BLACK = new Color(0, 0, 0, 0.5);
            public static final double NEW_ICON_SIZE = SIDE_LENGTH / 3 * Util.SQRT_2;
            private static final Font FONT =
                    Resources.getFont(Resources.GeosansLight_FONT, DIAGONAL_LENGTH / 4);
            public static final DropShadow SHADOW = new DropShadow(
                    OUTER_GLOW_INTENSITY, OUTER_GLOW_OFFSET, OUTER_GLOW_OFFSET, TRANSPARENT_BLACK);
            private static final StoryPlayer STORY_PLAYER = new StoryPlayer();
            private static final StoryUnlockConditionDisplayer CONDITION_DISPLAYER =
                    new StoryUnlockConditionDisplayer();

            private boolean enabled = false;
            private boolean unlocked = false;
            private final Label label;
            private final ImageView lock = new ImageView(Resources.Init_ILLUSTRATION);
            private final ImageView neo = new ImageView(Resources.NEW_ICON);
            private final StoryButtonPane parent = StoryButtonPane.this;
            private final Rectangle border = new Rectangle(SIDE_LENGTH, SIDE_LENGTH, Color.WHITE);
            private final EventHandler<? super MouseEvent> conditionHandler;
            private final EventHandler<? super MouseEvent> storyHandler;

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
                        -IMAGE_SIZE / 2, IMAGE_SIZE / 4,
                        -IMAGE_SIZE / 2, IMAGE_SIZE / 2,
                        -IMAGE_SIZE / 4, IMAGE_SIZE / 2,
                        IMAGE_SIZE / 2, -IMAGE_SIZE / 4,
                        IMAGE_SIZE / 2, -IMAGE_SIZE / 2,
                        IMAGE_SIZE / 4, -IMAGE_SIZE / 2);
                lockBg.setFill(Color.BLACK);
                lockBg.setOpacity(0.5);
                lockBg.setMouseTransparent(true);

                neo.setPreserveRatio(true);
                neo.setRotate(-45);
                neo.setFitWidth(NEW_ICON_SIZE);
                neo.setTranslateY(-SIDE_LENGTH * 7 / 12);
                neo.setMouseTransparent(true);

                ImageView view = new ImageView(illustrationPath);
                view.setFitWidth(IMAGE_SIZE);
                view.setPreserveRatio(true);
                view.setMouseTransparent(true);
                lock.setRotate(-45);
                lock.setFitWidth(DIAGONAL_LENGTH / 3);
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
                getChildren().addAll(border, view, lockBg, lock);

                if (chart != null) {
                    if (partner != null) {
                        conditionHandler = _ -> CONDITION_DISPLAYER.display(parent.parent, chart, partner);
                    } else {
                        conditionHandler = _ -> CONDITION_DISPLAYER.display(parent.parent, chart);
                    }
                } else {
                    conditionHandler = null;
                }

                boolean[] withCg = {false};
                List<StoryPlayer.Item> items;
                try {
                    items = new ObjectMapper().readValue(Resources.ofStream(storyPath), new TypeReference<>() {});
                } catch (IOException e) {
                    throw new UncheckedIOException("Failed to read '" + storyPath + "'", e);
                }

                if (items.isEmpty()) {
                    storyHandler = _ -> {
                        getChildren().remove(neo);
                        border.setOnMouseClicked(null);
                    };
                } else {
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

                    storyHandler = _ -> {
                        STORY_PLAYER.play(parent.parent, partnerAvatarPath, items);
                        getChildren().remove(neo);
                        border.setOnMouseClicked(_ ->
                                STORY_PLAYER.play(parent.parent, partnerAvatarPath, items));
                    };
                }

                if (withCg[0]) {
                    ImageView star = new ImageView(Resources.STAR);
                    star.setRotate(-45);
                    star.setPreserveRatio(true);
                    star.setFitWidth(SIDE_LENGTH / 4 * Util.SQRT_2);
                    star.setTranslateX(SIDE_LENGTH * 2 / 7);
                    star.setTranslateY(SIDE_LENGTH * 2 / 7);
                    star.setMouseTransparent(true);
                    getChildren().add(star);
                }

                parent.addAll(this);
            }

            @Override
            public String toString() {
                return getClass().getSimpleName() + " '" + label.getText() + "'";
            }

            public boolean isNew() {
                return getChildren().contains(neo);
            }

            private void enable() {
                if (!enabled) {
                    setOpacity(1);
                    if (conditionHandler != null) {
                        border.setOnMouseClicked(conditionHandler);
                    } else {
                        unlock();
                    }
                    enabled = true;
                    parent.updateLine();
                }
            }

            private void unlock() {
                if (!unlocked) {
                    border.setOnMouseClicked(storyHandler);
                    int i = parent.storyButtons.indexOf(this) + 1;
                    if (i < parent.storyButtons.size()) {
                        parent.storyButtons.get(i).enable();
                    }
                    getChildren().remove(lock);
                    getChildren().addAll(label, neo);
                    unlocked = true;
                }
            }

            public void setBorderOnMouseClicked(EventHandler<? super MouseEvent> handler) {
                border.setOnMouseClicked(handler);
            }
        }
    }
}
