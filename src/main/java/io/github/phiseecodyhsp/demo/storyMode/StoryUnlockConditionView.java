package io.github.phiseecodyhsp.demo.storyMode;

import io.github.phiseecodyhsp.demo.Resources;
import io.github.phiseecodyhsp.demo.Util;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

import javafx.util.Duration;

public class StoryUnlockConditionView extends StackPane {
    private static final double T_TIME = 0.25;
    private static final double LOWEST_SCALE_RATIO = 0.75;

    private final Label condition = new Label();
    private final StackPane pane = new StackPane();
    private final ImageView bg = new ImageView(Resources.SUCV_BG0);
    private final ScaleTransition onAddedST = new ScaleTransition(Duration.seconds(T_TIME), pane);
    private final ScaleTransition onRemovedST = new ScaleTransition(Duration.seconds(T_TIME), pane);
    private final FadeTransition onAddedContentFT = new FadeTransition(Duration.seconds(T_TIME), pane);
    private final FadeTransition onRemovedContentFT = new FadeTransition(Duration.seconds(T_TIME), pane);

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

        pane.setMaxSize(bg.getFitWidth(), bg.getFitHeight());
        pane.getChildren().addAll(bg, condition, illustration);
        pane.setOpacity(0);
        pane.setScaleX(LOWEST_SCALE_RATIO);
        pane.setScaleY(LOWEST_SCALE_RATIO);
        pane.setMaxSize(bg.getFitWidth(), bg.getFitHeight());

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

        getChildren().addAll(mask, pane);
    }

    public StoryUnlockConditionView(String song, String partner, String sPath, String pPath) {
        this(song, sPath);
        condition.setText("使用搭档“" + partner + "”通关“" + song + "”以解锁此故事。");
        bg.setImage(new Image(Resources.SUCV_BG1));

        ImageView partnerView = new ImageView(Resources.DOROC_AVATAR);
        try {
            partnerView.setImage(new Image(pPath));
        } catch (NullPointerException | IllegalArgumentException _) {}
        pane.getChildren().add(partnerView);
    }

    public void show(StoryPane parent) {
        parent.getChildren().add(this);
        onRemovedST.setOnFinished(_ -> parent.getChildren().remove(this));
        onRemovedContentFT.stop();
        onRemovedST.stop();
        onAddedContentFT.playFromStart();
        onAddedST.playFromStart();
    }
}
