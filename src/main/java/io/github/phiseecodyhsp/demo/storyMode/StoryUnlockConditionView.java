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
import org.jetbrains.annotations.NotNull;

import static io.github.phiseecodyhsp.demo.storyMode.StoryButton.*;

public class StoryUnlockConditionView extends StackPane {
    private static final double TRANS_TIME = 0.25;
    private static final double LOWEST_SCALE_RATIO = 0.75;
    private static final int ILLUSTRATION_WIDTH = SIDE_LENGTH * 2;
    private static final double BG_HEIGHT = ILLUSTRATION_WIDTH * 8 / 3.0;
    private static final Font FONT = new Font
            (Resources.Noto_Sans_FONT, Util.px2FontSize(ILLUSTRATION_WIDTH / 7.5));

    private final Label condition = new Label();
    private final StackPane pane = new StackPane();
    private final ImageView bg = new ImageView(Resources.SUCV_BG0);
    private final ImageView illustration;
    private final ScaleTransition onAddedST = new ScaleTransition(Duration.seconds(TRANS_TIME), pane);
    private final ScaleTransition onRemovedST = new ScaleTransition(Duration.seconds(TRANS_TIME), pane);
    private final FadeTransition onAddedContentFT = new FadeTransition(Duration.seconds(TRANS_TIME), pane);
    private final FadeTransition onRemovedContentFT = new FadeTransition(Duration.seconds(TRANS_TIME), pane);

    public StoryUnlockConditionView(String music, @NotNull String illustrationPath) {
        condition.setText("通关“" + music + "”以解锁此故事。");
        condition.setTextFill(Color.WHITE);
        condition.setFont(FONT);
        condition.setTranslateY((BG_HEIGHT - ILLUSTRATION_WIDTH) / 2.0);

        illustration = new ImageView(illustrationPath);
        illustration.setEffect(new DropShadow(OUTER_GLOW_INTENSITY, Color.WHITE));
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

        Rectangle shadow = new Rectangle(Util.getScreenWidth(), Util.getScreenHeight());
        shadow.setOpacity(0);
        shadow.setOnMouseClicked(_ -> {
            onAddedContentFT.stop();
            onAddedST.stop();
            onRemovedContentFT.playFromStart();
            onRemovedST.playFromStart();
        });

        getChildren().addAll(shadow, pane);
    }

    //TODO: 排版
    public StoryUnlockConditionView(String music,
                                    @NotNull String illustrationPath,
                                    String partner,
                                    @NotNull String partnerPath) {
        this(music, illustrationPath);
        condition.setText("使用搭档“" + partner + "”通关“" + music + "”以解锁此故事。");
        bg.setImage(new Image(Resources.SUCV_BG1));
        condition.setTranslateY(BG_HEIGHT / 2.0 - ILLUSTRATION_WIDTH / 4.0);
        illustration.setTranslateY(ILLUSTRATION_WIDTH - BG_HEIGHT / 2.0);

        Polygon arrow = new Polygon(
                0, -ILLUSTRATION_WIDTH / 15.0 / Math.sqrt(3),
                ILLUSTRATION_WIDTH / 30.0, ILLUSTRATION_WIDTH / 30.0 / Math.sqrt(3),
                -ILLUSTRATION_WIDTH / 30.0, ILLUSTRATION_WIDTH / 30.0 / Math.sqrt(3));
        arrow.setFill(Color.WHITE);
        arrow.setTranslateY(0);
        arrow.setEffect(new DropShadow(OUTER_GLOW_INTENSITY, Color.WHITE));

        Rectangle border = new Rectangle(ILLUSTRATION_WIDTH / 3.0, ILLUSTRATION_WIDTH / 3.0, Color.WHITE);
        border.setEffect(new DropShadow(OUTER_GLOW_INTENSITY, Color.WHITE));
        border.setRotate(45);
        border.setTranslateY(BG_HEIGHT / 2.0 - ILLUSTRATION_WIDTH * 5 / 6.0);

        ImageView partnerView = new ImageView(partnerPath);
        partnerView.setFitWidth(ILLUSTRATION_WIDTH / 3.0 * Math.sqrt(2) - BORDER_WIDTH * 2);
        partnerView.setPreserveRatio(true);
        partnerView.setTranslateY(BG_HEIGHT / 2.0 - ILLUSTRATION_WIDTH * 5 / 6.0);

        pane.getChildren().addAll(border, partnerView,arrow);
    }

    public StoryUnlockConditionView(Chart chart) {
        this(chart.music, chart.illustrationPath);
    }

    public StoryUnlockConditionView(Chart chart, Partner partner) {
        this(chart.music, chart.illustrationPath, partner.name(), partner.avatarPath());
    }

    public StoryUnlockConditionView(Chart chart, String partner, String partnerPath) {
        this(chart.music, chart.illustrationPath, partner, partnerPath);
    }

    public StoryUnlockConditionView(String music, String illustrationPath, Partner partner) {
        this(music, illustrationPath, partner.name(), partner.avatarPath());
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
