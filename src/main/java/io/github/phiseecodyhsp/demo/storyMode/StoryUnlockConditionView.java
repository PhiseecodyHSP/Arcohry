package io.github.phiseecodyhsp.demo.storyMode;

import io.github.phiseecodyhsp.demo.*;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import javafx.scene.text.Font;
import javafx.util.Duration;

public class StoryUnlockConditionView extends StackPane {
    private static final double T_TIME = 0.25;
    private static final double LOWEST_SCALE_RATIO = 0.75;
    private static final int ILLUSTRATION_WIDTH = StoryButton.SIDE_LENGTH * 2;
    private static final int BG_HEIGHT = Util.nextEven(ILLUSTRATION_WIDTH * 8 / 3.0);
    private static final Font FONT = new Font
            (Resources.Noto_Sans_FONT, Util.px2FontSize(Util.nextEven(ILLUSTRATION_WIDTH / 7.5)));

    private final Label condition = new Label();
    private final StackPane pane = new StackPane();
    private final ImageView bg = new ImageView(Resources.SUCV_BG0);
    private final ScaleTransition onAddedST = new ScaleTransition(Duration.seconds(T_TIME), pane);
    private final ScaleTransition onRemovedST = new ScaleTransition(Duration.seconds(T_TIME), pane);
    private final FadeTransition onAddedContentFT = new FadeTransition(Duration.seconds(T_TIME), pane);
    private final FadeTransition onRemovedContentFT = new FadeTransition(Duration.seconds(T_TIME), pane);

    public StoryUnlockConditionView(String song, String path) {
        condition.setText("通关“" + song + "”以解锁此故事。");
        condition.setTextFill(Color.WHITE);
        condition.setFont(FONT);
        condition.setTranslateY(BG_HEIGHT / 2.0 - ILLUSTRATION_WIDTH / 2.0);

        ImageView illustration = new ImageView(Resources.Tutorial_ILLUSTRTION);
        if (path != null) {
            try {
                illustration.setImage(new Image(path));
            } catch (IllegalArgumentException _) {}
        }
        illustration.setEffect(new DropShadow(StoryButton.OUTER_GLOW_INTENSITY, Color.WHITE));
        illustration.setTranslateY(BG_HEIGHT / 2.0 - ILLUSTRATION_WIDTH * 3 / 2.0);
        bg.setFitHeight(BG_HEIGHT);
        bg.setPreserveRatio(true);
        illustration.setFitWidth(ILLUSTRATION_WIDTH);
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

    //TODO: 排版
    public StoryUnlockConditionView(String song, String iPath, String partner, String pPath) {
        this(song, iPath);
        condition.setText("使用搭档“" + partner + "”通关“" + song + "”以解锁此故事。");
        bg.setImage(new Image(Resources.SUCV_BG1));

        Polygon arrow = new Polygon(
                0, -ILLUSTRATION_WIDTH / 15.0 / Math.sqrt(3),
                ILLUSTRATION_WIDTH / 30.0, ILLUSTRATION_WIDTH / 30.0 / Math.sqrt(3),
                -ILLUSTRATION_WIDTH / 30.0, ILLUSTRATION_WIDTH / 30.0 / Math.sqrt(3));
        arrow.setFill(Color.WHITE);
        arrow.setTranslateY(100);
        arrow.setEffect(new DropShadow(StoryButton.OUTER_GLOW_INTENSITY, Color.WHITE));

        Rectangle border = new Rectangle(Util.nextEven(ILLUSTRATION_WIDTH / 3.0),
                Util.nextEven(ILLUSTRATION_WIDTH / 3.0), Color.WHITE);
        border.setEffect(new DropShadow(StoryButton.OUTER_GLOW_INTENSITY, Color.WHITE));
        border.setRotate(45);

        ImageView partnerView = new ImageView(Resources.DOROC_AVATAR);
        if (pPath != null) {
            try {
                partnerView.setImage(new Image(pPath));
            } catch (IllegalArgumentException _) {}
        }
        partnerView.setFitWidth(
                Util.nextEven(ILLUSTRATION_WIDTH / 3.0 * Math.sqrt(2)) - StoryButton.BORDER_WIDTH * 2);
        partnerView.setPreserveRatio(true);

        pane.getChildren().addAll(arrow, border, partnerView);
    }

    public StoryUnlockConditionView(Chart chart, String partner, String pPath) {
        this(chart.music, chart.illustrationPath, partner, pPath);
    }

    public StoryUnlockConditionView(String song, String iPath, Partner partner) {
        this(song, iPath, partner.name(), partner.avatarPath());
    }

    public StoryUnlockConditionView(Chart chart, Partner partner) {
        this(chart.music, chart.illustrationPath, partner.name(), partner.avatarPath());
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
