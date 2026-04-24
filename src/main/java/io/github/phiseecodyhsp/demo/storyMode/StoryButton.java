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
    public static final double ARC_SIZE = 10.0;

    private static final int IMAGE_SIZE = SIDE_LENGTH - 2 * BORDER_WIDTH;
    private static final double MASK_HIGHEST_OPACITY = 0.25;
    public static final double BUTTON_LOWEST_OPACITY = 1 - MASK_HIGHEST_OPACITY;
    private static final double FT_TIME = 0.25;
    private static final Font font = new Font(Resources.Futura_LT_Light_FONT,
            Util.px2FontSize(Util.nextEven(DIAGONAL_LENGTH / 4.0)));

    private boolean enabled = false;
    private boolean unlocked = false;
    private final Label label;
    private final ImageView lock = new ImageView(Resources.Init_ILLUSTRATION);
    private final StoryUnlockConditionView condition;
    private final Story story;

    public StoryButton(String title, String path, StoryUnlockConditionView condition, Story story)  {
        this.story = story;
        this.condition = condition;
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

        setOpacity(BUTTON_LOWEST_OPACITY);
        setMaxSize(SIDE_LENGTH, SIDE_LENGTH);
        setRotate(45);
        setMouseTransparent(false);
        setBackground(null);
        getChildren().addAll(border, view, mask, lockBG, lock);
    }

    private StoryButtonPane getParentStoryButtonPane() {
        return Util.getDesignatedParent(this, StoryButtonPane.class);
    }

    private StoryPane getParentStoryPane() {
        return Util.getDesignatedParent(getParentStoryButtonPane(), StoryPane.class);
    }

    public void enable() {
        if (!enabled) {
            setOpacity(1);
            setOnMouseClicked(_ -> condition.show(getParentStoryPane()));
            enabled = true;
        }
    }

    public void unlock() {
        if (!unlocked) {
            enable();
            setOnMouseClicked(_ -> story.play(getParentStoryPane()));
            getChildren().remove(lock);
            getChildren().add(label);
            getParentStoryButtonPane().buttonList.get(getParentStoryButtonPane().buttonList.indexOf(this) + 1).enable();
            unlocked = true;
        }
    }
}
