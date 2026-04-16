package io.github.phiseecodyhsp.demo.storyMode;

import io.github.phiseecodyhsp.demo.Resources;
import io.github.phiseecodyhsp.demo.Util;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;

public class StoryUnlockConditionView extends StackPane {
    private static final double T_TIME = 0.3;
    private static final double LOWEST_SCALE_RATIO = 0.5;

    private final Label condition = new Label();
    private final StackPane innerPane = new StackPane();
    private final StackPane outerPane = new StackPane();
    private final ImageView bg = new ImageView(Resources.SUCV_BG0);
    private final ScaleTransition onAddedST = new ScaleTransition(Duration.seconds(T_TIME), innerPane);
    private final ScaleTransition onRemovedST = new ScaleTransition(Duration.seconds(T_TIME), innerPane);
    private final FadeTransition onAddedContentFT = new FadeTransition(Duration.seconds(T_TIME), innerPane);
    private final FadeTransition onRemovedContentFT = new FadeTransition(Duration.seconds(T_TIME), innerPane);

    public StoryUnlockConditionView(String song, String path) {
        condition.setText("通关“" + song + "”以解锁此故事。");
        ImageView illustration = new ImageView(Resources.Tutorial_ILLUSTRTION);
        try {
            illustration.setImage(new Image(path));
        } catch (NullPointerException | IllegalArgumentException _) {}
        bg.setFitWidth(Util.getScreenWidth());
        bg.setPreserveRatio(true);
        illustration.setFitWidth(100);
        illustration.setPreserveRatio(true);

        innerPane.setMaxSize(bg.getFitWidth(), bg.getFitHeight());
        innerPane.getChildren().addAll(bg, condition, illustration);
        innerPane.setOpacity(0);
        innerPane.setScaleX(LOWEST_SCALE_RATIO);
        innerPane.setScaleY(LOWEST_SCALE_RATIO);
        outerPane.setMaxSize(bg.getFitWidth(), bg.getFitHeight());
        outerPane.getChildren().addAll(innerPane);

        onAddedST.setToX(1);
        onAddedST.setToY(1);
        onAddedST.setInterpolator(new Interpolator() {
            @Override
            protected double curve(double v) {
                return 1 - (1 - v) * (1 - v) * (1 - v);
            }
        });
        onRemovedST.setToX(LOWEST_SCALE_RATIO);
        onRemovedST.setToY(LOWEST_SCALE_RATIO);
        onRemovedST.setInterpolator(new Interpolator() {
            @Override
            protected double curve(double v) {
                return v * v * v;
            }
        });
        onRemovedST.setOnFinished(_ -> getParentStoryPane().getChildren().remove(this));
        onAddedContentFT.setToValue(1);
        onRemovedContentFT.setToValue(0);

        Rectangle mask = new Rectangle(Util.getScreenWidth(), Util.getScreenHeight());
        mask.setOpacity(0);
        mask.setOnMouseClicked(_ -> {
            onAddedContentFT.stop();
            onAddedST.stop();
            onRemovedContentFT.playFromStart();
            onRemovedST.playFromStart();
        });

        List<Scene> oldScenes = new ArrayList<>();
        sceneProperty().addListener((_, _, scene) -> {
            if (scene != null) {
                updateScale(scene);
                if (!oldScenes.contains(scene)) {
                    oldScenes.add(scene);
                    scene.widthProperty().addListener(
                            (_, _, _) -> updateScale(scene));
                    scene.heightProperty().addListener(
                            (_, _, _) -> updateScale(scene));
                }
            }
        });
        getChildren().addAll(mask, outerPane);
    }

    public StoryUnlockConditionView(String song, String partner, String sPath, String pPath) {
        this(song, sPath);
        condition.setText("使用搭档“" + partner + "”通关“" + song + "”以解锁此故事。");
        bg.setImage(new Image(Resources.SUCV_BG1));

        ImageView partnerView = new ImageView(Resources.DOROC_AVATAR);
        try {
            partnerView.setImage(new Image(pPath));
        } catch (NullPointerException | IllegalArgumentException _) {}
        innerPane.getChildren().add(partnerView);
    }

    private StoryPane getParentStoryPane() {
        return Util.getDesignatedParent(this, StoryPane.class);
    }

    private void updateScale(Scene scene) {
        double scale = Math.min(scene.getWidth() / Util.getScreenWidth(),
                scene.getHeight() / (Util.getScreenHeight()));
        outerPane.setScaleX(scale);
        outerPane.setScaleY(scale);
        System.out.println(scale);
        System.out.println(Util.getDpi());
    }

    public void show(StoryPane storyPane) {
        storyPane.getChildren().add(this);
        onRemovedContentFT.stop();
        onRemovedST.stop();
        onAddedContentFT.playFromStart();
        onAddedST.playFromStart();
    }
}
