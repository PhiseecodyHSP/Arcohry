package io.github.phiseecodyhsp.demo.storyMode;

import io.github.phiseecodyhsp.demo.Util;
import io.github.phiseecodyhsp.demo.Resources;
import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class StoryButton extends StackPane {
    public static final int SIDE_LENGTH = 100;
    public static final int BORDER_WIDTH = 2;
    public static final int DIAGONAL_LENGTH = Util.nextEven(SIDE_LENGTH * Math.sqrt(2));
    public static final double ARC_SIZE = 5;
    private static final int IMAGE_SIZE = SIDE_LENGTH - 2 * BORDER_WIDTH;
    private static final double MASK_HIGHEST_OPACITY = 0.25;
    public static final double LOWEST_OPACITY = 1 - MASK_HIGHEST_OPACITY;
    private static final double FT_TIME = 0.25;
    private static final Font font = new Font(Resources.Futura_LT_Light_FONT,
            Util.px2FontSize(Util.nextEven(DIAGONAL_LENGTH / 4.0)));

    private boolean enabled = false;
    private boolean unlocked = false;
    private final Label label;
    private final ImageView lock = new ImageView(Resources.Init_ILLUSTRATION);
    private final StoryUnlockConditionView condition;
    private final StoryButtonPane parent;

    private Story story = null;
    private AVGStory avgStory = null;

    private StoryButton(StoryButtonPane parent, String title, String path, StoryUnlockConditionView condition) {
        this.condition = condition;
        this.parent = parent;
        label = new Label(title);
        label.setFont(font);
        label.setTextFill(Color.WHITE);
        label.setRotate(-45);

        Rectangle border = new Rectangle(SIDE_LENGTH, SIDE_LENGTH);
        border.setFill(Color.WHITE);
        border.setArcWidth(ARC_SIZE);
        border.setArcHeight(ARC_SIZE);
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


        ImageView view = new ImageView(Resources.Tutorial_ILLUSTRTION);
        try {
            view.setImage(new Image(path));
        } catch (NullPointerException | IllegalArgumentException _) {}

        view.setFitWidth(IMAGE_SIZE);
        view.setPreserveRatio(true);
        lock.setRotate(-45);
        lock.setFitWidth(DIAGONAL_LENGTH / 3.0);
        lock.setPreserveRatio(true);


        FadeTransition onEntered = new FadeTransition(Duration.seconds(FT_TIME), mask);
        FadeTransition onExited = new FadeTransition(Duration.seconds(FT_TIME), mask);
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
            if (p != null && p != parent) {
                throw new IllegalStateException(getClass().getSimpleName() + "的父容器必须与实例化其时传入的父容器相同");
            }
        });
        setOpacity(LOWEST_OPACITY);
        setMaxSize(SIDE_LENGTH, SIDE_LENGTH);
        setRotate(45);
        setMouseTransparent(false);
        setBackground(null);
        getChildren().addAll(border, view, mask, lockBG, lock);
    }

    public StoryButton(StoryButtonPane parent, String title, String path, StoryUnlockConditionView condition, Story story)  {
        this(parent, title, path, condition);
        this.story = story;
    }

    public StoryButton(StoryButtonPane parent, String title, String path, StoryUnlockConditionView condition, AVGStory story) {
        this(parent, title, path, condition);
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
                    avgStory.play();
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
