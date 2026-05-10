package io.github.phiseecodyhsp.demo.storyMode;

import io.github.phiseecodyhsp.demo.*;
import io.github.phiseecodyhsp.demo.storage.Chart;
import io.github.phiseecodyhsp.demo.storage.Partner;
import io.github.phiseecodyhsp.demo.storage.Resources;
import io.github.phiseecodyhsp.demo.Util;
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

//TODO: 背景素材等
public class StoryUnlockConditionView extends StackPane {
    private static final double TRANS_TIME = 0.25;
    private static final double LOWEST_SCALE_RATIO = 0.75;
    private static final int ILLUSTRATION_WIDTH = SIDE_LENGTH * 2;
    private static final int BG_HEIGHT = Util.doubleToEven(ILLUSTRATION_WIDTH * 8 / 3.0);
    private static final Font FONT = new Font
            (null, Util.pxToFontSize(ILLUSTRATION_WIDTH / 7.5));

    private final String music;
    private final String composer;
    private final String illustrationPath;
    private final String illustrator;
    private final String noteDesigner;
    private final Chart.Paradigms paradigms;
    private final Label condition = new Label();
    private final ImageView bg = new ImageView(Resources.SUCV_BG0);
    private final ImageView illustration;
    private final StackPane pane = new StackPane();
    private final ScaleTransition onAddedST = new ScaleTransition(Duration.seconds(TRANS_TIME), pane);
    private final ScaleTransition onRemovedST = new ScaleTransition(Duration.seconds(TRANS_TIME), pane);
    private final FadeTransition onAddedContentFT = new FadeTransition(Duration.seconds(TRANS_TIME), pane);
    private final FadeTransition onRemovedContentFT = new FadeTransition(Duration.seconds(TRANS_TIME), pane);

    public StoryUnlockConditionView(String music,
                                    String composer,
                                    @NotNull String illustrationPath,
                                    String illustrator,
                                    String noteDesigner,
                                    @NotNull Chart.Paradigms paradigms) {
        this.music = music;
        this.composer = composer;
        this.illustrationPath = illustrationPath;
        this.illustrator = illustrator;
        this.noteDesigner = noteDesigner;
        this.paradigms = paradigms;

        condition.setText("通关“" + music + "”以解锁此故事。");
        condition.setTextFill(Color.WHITE);
        condition.setFont(FONT);
        condition.setTranslateY(Util.doubleToEven((Util.doubleToEven(BG_HEIGHT - ILLUSTRATION_WIDTH) / 2.0)));

        illustration = new ImageView(illustrationPath);
        illustration.setEffect(new DropShadow(OUTER_GLOW_INTENSITY, Color.WHITE));
        illustration.setTranslateY(
                Util.doubleToEven(Util.doubleToEven(BG_HEIGHT / 2.0 - ILLUSTRATION_WIDTH * 3 / 2.0)));
        bg.setFitHeight(BG_HEIGHT);
        bg.setPreserveRatio(true);
        illustration.setFitWidth(ILLUSTRATION_WIDTH);
        illustration.setPreserveRatio(true);

        pane.setMaxSize(0, 0);
        pane.getChildren().addAll(bg, condition, illustration);
        pane.setOpacity(0);
        pane.setScaleX(LOWEST_SCALE_RATIO);
        pane.setScaleY(LOWEST_SCALE_RATIO);

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

    public StoryUnlockConditionView(String music,
                                    String composer,
                                    @NotNull String illustrationPath,
                                    String illustrator,
                                    String noteDesigner,
                                    @NotNull Chart.Paradigms paradigms,
                                    String partner,
                                    @NotNull String partnerPath) {
        this(music, composer, illustrationPath, illustrator, noteDesigner, paradigms);
        condition.setText("使用搭档“" + partner + "”通关“" + music + "”以解锁此故事。");
        bg.setImage(new Image(Resources.SUCV_BG1));
        condition.setTranslateY(Util.doubleToEven(BG_HEIGHT / 2.0 - ILLUSTRATION_WIDTH / 4.0));
        illustration.setTranslateY(Util.doubleToEven(ILLUSTRATION_WIDTH - BG_HEIGHT / 2.0));

        Polygon arrow = new Polygon(
                0, -ILLUSTRATION_WIDTH / 15.0 / Util.SQRT_3,
                ILLUSTRATION_WIDTH / 30.0, ILLUSTRATION_WIDTH / 30.0 / Util.SQRT_3,
                -ILLUSTRATION_WIDTH / 30.0, ILLUSTRATION_WIDTH / 30.0 / Util.SQRT_3);
        arrow.setFill(Color.WHITE);
        arrow.setTranslateY(Util.doubleToEven(ILLUSTRATION_WIDTH * 7 / 30.0 + BORDER_WIDTH / 4.0));
        arrow.setEffect(new DropShadow(OUTER_GLOW_INTENSITY, Color.WHITE));

        Rectangle border = new Rectangle(
                Util.doubleToEven(ILLUSTRATION_WIDTH / 2.5) / Util.SQRT_2 + BORDER_WIDTH,
                Util.doubleToEven(ILLUSTRATION_WIDTH / 2.5) / Util.SQRT_2 + BORDER_WIDTH,
                Color.WHITE);
        border.setEffect(new DropShadow(OUTER_GLOW_INTENSITY, Color.WHITE));
        border.setRotate(45);
        border.setTranslateY(Util.doubleToEven(BG_HEIGHT / 2.0 - ILLUSTRATION_WIDTH * 5 / 6.0));

        ImageView partnerView = new ImageView(partnerPath);
        partnerView.setFitWidth(Util.doubleToEven(ILLUSTRATION_WIDTH / 2.5));
        partnerView.setPreserveRatio(true);
        partnerView.setTranslateY(Util.doubleToEven(BG_HEIGHT / 2.0 - ILLUSTRATION_WIDTH * 5 / 6.0));

        pane.getChildren().addAll(border, partnerView,arrow);
    }

    public StoryUnlockConditionView(@NotNull Chart chart) {
        this(chart.music,
                chart.composer,
                chart.illustrationPath,
                chart.illustrator,
                chart.noteDesigner,
                chart.paradigms);
    }

    public StoryUnlockConditionView(@NotNull Chart chart,@NotNull Partner partner) {
        this(chart.music,
                chart.composer,
                chart.illustrationPath,
                chart.illustrator,
                chart.noteDesigner,
                chart.paradigms,
                partner.name(),
                partner.avatarPath());
    }

    public StoryUnlockConditionView(@NotNull Chart chart, String partner,@NotNull String partnerPath) {
        this(chart.music,
                chart.composer,
                chart.illustrationPath,
                chart.illustrator,
                chart.noteDesigner,
                chart.paradigms,
                partner,
                partnerPath);
    }

    public StoryUnlockConditionView(String music,
                                    String composer,
                                    @NotNull String illustrationPath,
                                    String illustrator,
                                    String noteDesigner,
                                    @NotNull Chart.Paradigms paradigms,
                                    @NotNull Partner partner) {
        this(music,
                composer,
                illustrationPath,
                illustrator,
                noteDesigner,
                paradigms,
                partner.name(),
                partner.avatarPath());
    }

    public void show(StoryPane parent) {
        parent.getChildren().add(this);
        illustration.setOnMouseClicked(_ -> {
            Util.getSetStage(this).playChart(SetStage.TransAnimaType.NORMAL,
                    music,
                    composer,
                    illustrationPath,
                    illustrator,
                    noteDesigner,
                    paradigms);
            onAddedContentFT.stop();
            onAddedST.stop();
            onRemovedContentFT.playFromStart();
            onRemovedST.playFromStart();
            });
        onRemovedST.setOnFinished(_ -> parent.getChildren().remove(this));
        onRemovedContentFT.stop();
        onRemovedST.stop();
        onAddedContentFT.playFromStart();
        onAddedST.playFromStart();
    }
}
