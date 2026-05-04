package io.github.phiseecodyhsp.demo.storyMode;

import io.github.phiseecodyhsp.demo.Util;
import io.github.phiseecodyhsp.demo.storage.Resources;
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
    public static final int DIAGONAL_LENGTH = Util.doubleToEven(SIDE_LENGTH * Math.sqrt(2));
    public static final int ARC_SIZE = 5;
    public static final int IMAGE_SIZE = SIDE_LENGTH - 2 * BORDER_WIDTH;
    private static final double MASK_HIGHEST_OPACITY = 0.25;
    public static final double LOWEST_OPACITY = 1 - MASK_HIGHEST_OPACITY;
    public static final int OUTER_GLOW_INTENSITY = 10;
    public static final int OUTER_GLOW_OFFSET = 10;
    private static final double FADETRANS_TIME = 0.25;
    private static final Font FONT = new Font(
            Resources.Futura_LT_Light_FONT, Util.pxToFontSize(DIAGONAL_LENGTH / 4.0));

    private boolean enabled = false;
    private boolean unlocked = false;
    private final Label label;
    private final ImageView lock = new ImageView(Resources.Init_ILLUSTRATION);
    private final StoryUnlockConditionView condition;
    private final StoryButtonPane parent;

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

        Rectangle border = new Rectangle(SIDE_LENGTH, SIDE_LENGTH);
        border.setFill(Color.WHITE);
        border.setArcWidth(ARC_SIZE);
        border.setArcHeight(ARC_SIZE);
        border.setEffect(new DropShadow(
                OUTER_GLOW_INTENSITY, OUTER_GLOW_OFFSET, OUTER_GLOW_OFFSET, new Color(0, 0, 0, 0.5)));
        Rectangle mask = new Rectangle(IMAGE_SIZE, IMAGE_SIZE);
        mask.setFill(Color.BLACK);
        mask.setOpacity(0);
        Polygon lockBG = new Polygon(
                -IMAGE_SIZE / 2.0, IMAGE_SIZE / 4.0,
                -IMAGE_SIZE / 2.0, IMAGE_SIZE / 2.0,
                -IMAGE_SIZE / 4.0, IMAGE_SIZE / 2.0,
                IMAGE_SIZE / 2.0, -IMAGE_SIZE / 4.0,
                IMAGE_SIZE / 2.0, -IMAGE_SIZE / 2.0,
                IMAGE_SIZE / 4.0, -IMAGE_SIZE / 2.0);
        lockBG.setFill(Color.BLACK);
        lockBG.setOpacity(0.5);


        ImageView view;
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
        getChildren().addAll(border, view, mask, lockBG, lock);
    }

    public StoryButton(StoryButtonPane parent,
                       String title,
                       String illustrationPath,
                       StoryUnlockConditionView condition,
                       Story story)  {
        this(parent, title, illustrationPath, condition);
        this.story = story;
    }

    public StoryButton(StoryButtonPane parent,
                       String title,
                       String illustrationPath,
                       StoryUnlockConditionView condition,
                       AVGStory story) {
        this(parent, title, illustrationPath, condition);
        avgStory = story;
    }

    public String toString() {
        return label.getText();
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void enable(StoryPane parent) {
        if (!enabled) {
            setOpacity(1);
            setOnMouseClicked(_ -> condition.show(parent));
            enabled = true;
            this.parent.updateLine();
        }
    }

    public void unlock(StoryPane parent) {
        if (!unlocked) {
            enable(parent);
            setOnMouseClicked(_ -> {
                if (story == null) {
                    avgStory.play(Util.getSetStage(this));
                } else {
                    story.play(parent);
                }
            });
            getChildren().remove(lock);
            getChildren().add(label);
            try {
                this.parent.storyButtons.get(this.parent.storyButtons.indexOf(this) + 1).enable(parent);
            } catch (IndexOutOfBoundsException _) {}
            unlocked = true;
        }
    }
}
