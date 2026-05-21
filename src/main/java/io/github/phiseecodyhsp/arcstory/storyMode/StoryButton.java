package io.github.phiseecodyhsp.arcstory.storyMode;

import io.github.phiseecodyhsp.arcstory.Util;
import io.github.phiseecodyhsp.arcstory.storage.Resources;
import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

public class StoryButton extends StackPane {
    public static final int SIDE_LENGTH = Util.doubleToEven(Util.getScreenHeight() * 0.1);
    public static final int BORDER_WIDTH = 2;
    public static final int DIAGONAL_LENGTH = Util.doubleToEven(SIDE_LENGTH * Util.SQRT_2);
    public static final int ARC_SIZE = 5;
    public static final int IMAGE_SIZE = SIDE_LENGTH - 2 * BORDER_WIDTH;
    public static final double MASK_HIGHEST_OPACITY = 0.25;
    public static final double LOWEST_OPACITY = 1 - MASK_HIGHEST_OPACITY;
    public static final int OUTER_GLOW_INTENSITY = 10;
    public static final int OUTER_GLOW_OFFSET = 10;
    public static final double FADETRANS_TIME = 0.25;
    public static final Color TRANSPARENT_BLACK = new Color(0, 0, 0, 0.5);
    public static final int NEW_ICON_SIZE = 10;
    private static final Font FONT =
            Resources.getFont(Resources.GeosansLight_FONT, DIAGONAL_LENGTH / 4.0);

    private boolean enabled = false;
    private boolean unlocked = false;
    private final Label label;
    private final ImageView lock = new ImageView(Resources.Init_ILLUSTRATION);
    private final ImageView neo = new ImageView(Resources.NEW_ICON);
    private final StoryUnlockConditionView condition;
    private final StoryButtonPane parent;
    private final ImageView star = new ImageView(Resources.Init_ILLUSTRATION);
    private final Rectangle border = new Rectangle(SIDE_LENGTH, SIDE_LENGTH);
    private final ImageView view;
    private final Rectangle mask = new Rectangle(IMAGE_SIZE, IMAGE_SIZE);
    private final Polygon lockBG = new Polygon(
            -IMAGE_SIZE / 2.0, IMAGE_SIZE / 4.0,
            -IMAGE_SIZE / 2.0, IMAGE_SIZE / 2.0,
            -IMAGE_SIZE / 4.0, IMAGE_SIZE / 2.0,
            IMAGE_SIZE / 2.0, -IMAGE_SIZE / 4.0,
            IMAGE_SIZE / 2.0, -IMAGE_SIZE / 2.0,
            IMAGE_SIZE / 4.0, -IMAGE_SIZE / 2.0);

    private Story story = null;
    private AVGStory avgStory = null;

    private StoryButton(@NotNull StoryButtonPane parent,
                        String title,
                        @NotNull String illustrationPath,
                        StoryUnlockConditionView condition) {
        this.condition = condition;
        this.parent = parent;
        label = new Label(title);
        label.setFont(FONT);
        label.setTextFill(Color.WHITE);
        label.setRotate(-45);
        label.setEffect(new DropShadow(
                OUTER_GLOW_INTENSITY, OUTER_GLOW_OFFSET, OUTER_GLOW_OFFSET, TRANSPARENT_BLACK));

        border.setFill(Color.WHITE);
        border.setArcWidth(ARC_SIZE);
        border.setArcHeight(ARC_SIZE);
        border.setEffect(new DropShadow(
                OUTER_GLOW_INTENSITY, OUTER_GLOW_OFFSET, OUTER_GLOW_OFFSET, TRANSPARENT_BLACK));
        mask.setFill(Color.BLACK);
        mask.setOpacity(0);
        lockBG.setFill(Color.BLACK);
        lockBG.setOpacity(0.5);

        //TODO
        star.setPreserveRatio(true);
        star.setFitWidth(0);
        star.setTranslateY(0);
        neo.setPreserveRatio(true);
        neo.setFitWidth(NEW_ICON_SIZE);
        neo.setTranslateY(0);

        view = new ImageView(illustrationPath);
        view.setFitWidth(IMAGE_SIZE);
        view.setPreserveRatio(true);
        lock.setRotate(-45);
        lock.setFitWidth(DIAGONAL_LENGTH / 3.0);
        lock.setPreserveRatio(true);


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

        parentProperty().addListener((_, _, p) -> {
            if (p != parent) {
                throw new IllegalStateException(getClass().getSimpleName() + "的父容器必须与实例化其时传入的父容器相同");
            }
        });
        setOpacity(LOWEST_OPACITY);
        setMaxSize(0, 0);
        setRotate(45);
        setMouseTransparent(false);
    }

    public StoryButton(@NotNull StoryButtonPane parent,
                       String title,
                       @NotNull String illustrationPath,
                       StoryUnlockConditionView condition,
                       @NotNull Story story)  {
        this(parent, title, illustrationPath, condition);
        if (story.hasCG()) {
            getChildren().add(star);
        }
        getChildren().addAll(border, view, mask, lockBG, lock);
        this.story = story;
    }

    public StoryButton(@NotNull StoryButtonPane parent,
                       String title,
                       @NotNull String illustrationPath,
                       StoryUnlockConditionView condition,
                       @NotNull AVGStory story) {
        this(parent, title, illustrationPath, condition);
        getChildren().addAll(star, border, view, mask, lockBG, lock);
        avgStory = story;
    }

    public String toString() {
        return label.getText();
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void enable(ChapterPane parent) {
        if (!enabled) {
            setOpacity(1);
            setOnMouseClicked(_ -> condition.show(parent));
            enabled = true;
            this.parent.updateLine();
        }
    }

    public void unlock(ChapterPane parent) {
        if (!unlocked) {
            enable(parent);
            setOnMouseClicked(_ -> {
                if (story == null) {
                    avgStory.play(Util.getSetStage(this));
                } else {
                    story.play(parent);
                }
                getChildren().remove(neo);
                setOnMouseClicked(_ -> {
                    if (story == null) {
                        avgStory.play(Util.getSetStage(this));
                    } else {
                        story.play(parent);
                    }
                });
            });
            getChildren().remove(lock);
            getChildren().addAll(label, neo);
            try {
                this.parent.storyButtons.get(this.parent.storyButtons.indexOf(this) + 1).enable(parent);
            } catch (IndexOutOfBoundsException _) {}
            unlocked = true;
        }
    }
}
