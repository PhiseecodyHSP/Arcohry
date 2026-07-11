package io.github.phiseecodyhsp.arcstory.ui.screen.view;

import io.github.phiseecodyhsp.arcstory.model.story.Paragraph;
import io.github.phiseecodyhsp.arcstory.res.ResourceLoader;
import io.github.phiseecodyhsp.arcstory.ui.base.AppWindow;
import io.github.phiseecodyhsp.arcstory.ui.screen.viewmodel.StoryViewModel;
import io.github.phiseecodyhsp.arcstory.util.PropertyUtil;
import io.github.phiseecodyhsp.arcstory.view.StoryNodeUiConstants;
import io.github.phiseecodyhsp.arcstory.view.TextPlayer;
import io.github.phiseecodyhsp.arcstory.ui.util.Interpolators;
import io.github.phiseecodyhsp.arcstory.ui.util.ScreenMetrics;
import io.github.phiseecodyhsp.arcstory.view.Typewriter;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.List;

/**
 * 故事阅读的界面.
 *
 * @author RikkaKawaii0612
 */
public class StoryView extends StackPane {

    /**
     * CG 播放动画扫线宽度.
     */
    private static final double SWEEP_LINE_WIDTH = ScreenMetrics.SCREEN_HEIGHT / 40.0D;

    /**
     * CG 播放动画扫线从竖直旋转角度的正切值.
     */
    private static final double SWEEP_LINE_ROTATION = Math.tan(Math.toRadians(20.0D));

    /**
     * CG 播放动画时长.
     */
    public static final double TRANS_TIME = 0.25D;

    /**
     * 故事播放时背景阴影的暗度.
     */
    public static final double SHADOW_DARKNESS = 0.5D;

    /**
     * CG 播放动画中荧光的亮度.
     */
    public static final double GLOW_BRIGHTNESS = 0.75D;

    /**
     * 文本 X 轴偏移.
     */
    public static final double TEXT_TRANSLATE_X = StoryNodeUiConstants.TEXT_PLAYER_FONT_SIZE * 3.0D;

    private final StoryViewModel viewModel;

    private final Rectangle shadow;

    private final Timeline onCgAdded;

    private final Typewriter typewriter;

    public StoryView(StoryViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.currentStatusProperty().addListener(
                (_, _, v) -> this.onStatusChanged(v));

        this.setPickOnBounds(true);
        this.setOnMouseClicked(_ -> this.viewModel.proceed());

        this.shadow = new Rectangle(ScreenMetrics.SCREEN_WIDTH, ScreenMetrics.SCREEN_HEIGHT, Color.BLACK);
        this.shadow.setOpacity(0.0D);

        Polygon sweepLine = new Polygon(
                -SWEEP_LINE_WIDTH, 0.0D,
                -SWEEP_LINE_WIDTH - SWEEP_LINE_ROTATION * ScreenMetrics.SCREEN_HEIGHT, ScreenMetrics.SCREEN_HEIGHT,
                -SWEEP_LINE_ROTATION * ScreenMetrics.SCREEN_HEIGHT, ScreenMetrics.SCREEN_HEIGHT,
                0.0D, 0.0D);
        DropShadow sweepLineGlow = new DropShadow(0, Color.WHITE);
        DropShadow shadow1 = new DropShadow(0, Color.WHITE);
        DropShadow shadow2 = new DropShadow(0, Color.WHITE);
        sweepLineGlow.setInput(shadow1);
        shadow1.setInput(shadow2);
        sweepLine.setTranslateX(-ScreenMetrics.SCREEN_WIDTH);
        sweepLine.setFill(Color.WHITE);
        sweepLine.setEffect(sweepLineGlow);

        ColorAdjust glow = new ColorAdjust();
        Polygon clipper = new Polygon(
                -SWEEP_LINE_WIDTH, 0.0D,
                -SWEEP_LINE_WIDTH - SWEEP_LINE_ROTATION * ScreenMetrics.SCREEN_HEIGHT, ScreenMetrics.SCREEN_HEIGHT,
                ScreenMetrics.SCREEN_WIDTH, ScreenMetrics.SCREEN_HEIGHT,
                ScreenMetrics.SCREEN_WIDTH + SWEEP_LINE_ROTATION * ScreenMetrics.SCREEN_HEIGHT, 0.0D);

        ImageView bottomCg = new ImageView();
        bottomCg.imageProperty().bind(PropertyUtil.createImage(this.viewModel.bottomCgProperty()));
        bottomCg.setPreserveRatio(true);
        bottomCg.setFitWidth(ScreenMetrics.SCREEN_WIDTH);
        bottomCg.setEffect(glow);

        ImageView topCg = new ImageView();
        topCg.imageProperty().bind(PropertyUtil.createImage(this.viewModel.topCgProperty()));
        topCg.setPreserveRatio(true);
        topCg.setFitWidth(ScreenMetrics.SCREEN_WIDTH);
        topCg.setClip(clipper);
        topCg.setEffect(glow);
        clipper.translateYProperty().bind(topCg.imageProperty().map(
                image -> 0.5D * (ScreenMetrics.SCREEN_WIDTH * image.getHeight() / image.getWidth() - ScreenMetrics.SCREEN_HEIGHT)));

        this.typewriter = new Typewriter();
        this.typewriter.setOnFinished(this.viewModel::markWaiting);
        TextPlayer textPlayer = new TextPlayer(this.typewriter);
        textPlayer.setMaxWidth(0.5D * ScreenMetrics.SCREEN_WIDTH - TEXT_TRANSLATE_X);
        textPlayer.setTranslateX(-0.5D * ScreenMetrics.SCREEN_WIDTH + TEXT_TRANSLATE_X + 0.5D * textPlayer.getMaxWidth());

        double time = 4.0D * TRANS_TIME;
        this.onCgAdded = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(
                                clipper.translateXProperty(),
                                -ScreenMetrics.SCREEN_WIDTH - SWEEP_LINE_WIDTH - SWEEP_LINE_ROTATION * ScreenMetrics.SCREEN_HEIGHT),
                        new KeyValue(
                                sweepLine.translateXProperty(),
                                -0.5D * (ScreenMetrics.SCREEN_WIDTH + SWEEP_LINE_WIDTH + SWEEP_LINE_ROTATION * ScreenMetrics.SCREEN_HEIGHT)),
                        new KeyValue(sweepLine.opacityProperty(), 0.5D),
                        new KeyValue(sweepLineGlow.radiusProperty(), 127.0D),
                        new KeyValue(shadow1.radiusProperty(), 127.0D),
                        new KeyValue(shadow2.radiusProperty(), 127.0D),
                        new KeyValue(glow.brightnessProperty(), 0.0D)),
                new KeyFrame(Duration.seconds(time / 32.0D),
                        new KeyValue(glow.brightnessProperty(), GLOW_BRIGHTNESS)),
                new KeyFrame(Duration.seconds(time),
                        new KeyValue(clipper.translateXProperty(), 0.0D, Interpolators.CUBE_IN),
                        new KeyValue(
                                sweepLine.translateXProperty(),
                                0.5D * (ScreenMetrics.SCREEN_WIDTH + SWEEP_LINE_WIDTH + SWEEP_LINE_ROTATION * ScreenMetrics.SCREEN_HEIGHT),
                                Interpolators.CUBE_IN),
                        new KeyValue(sweepLine.opacityProperty(), 1.0D),
                        new KeyValue(sweepLineGlow.radiusProperty(), 0.0D),
                        new KeyValue(shadow1.radiusProperty(), 0.0D),
                        new KeyValue(shadow2.radiusProperty(), 0.0D),
                        new KeyValue(glow.brightnessProperty(), 0.0D)));
        this.onCgAdded.setOnFinished(_ -> this.viewModel.markWaiting());

        this.getChildren().addAll(bottomCg, this.shadow, topCg, sweepLine, textPlayer);
    }

    public void start() {
        List<Paragraph> list = this.viewModel.getStory().getParagraphs();
        if (list.isEmpty()) {
            return;
        }

        this.viewModel.proceed();
    }

    private void onStatusChanged(StoryViewModel.Status status) {
        switch (status) {
            case TEXT -> {
                String text = ResourceLoader.loadText(this.viewModel.getCurrentText());
                if (this.viewModel.isShadowHidden()) {
                    FadeTransition onShadowAdded = new FadeTransition(Duration.seconds(TRANS_TIME), this.shadow);
                    onShadowAdded.setFromValue(0.0D);
                    onShadowAdded.setToValue(SHADOW_DARKNESS);
                    onShadowAdded.setOnFinished(_ -> this.typewriter.play(text));
                    onShadowAdded.playFromStart();
                } else {
                    this.typewriter.play(text);
                }
            }
            case CG -> {
                if (this.viewModel.isShadowHidden()) {
                    this.shadow.setOpacity(0.0D);
                }
                this.typewriter.play("");
                this.onCgAdded.playFromStart();
            }
            case WAITING -> {
                if (this.typewriter.isPlaying()) {
                    this.typewriter.stop();
                }
            }
            case FINISHED -> {
                FadeTransition onFinished = new FadeTransition(Duration.seconds(TRANS_TIME), this);
                onFinished.setToValue(0.0D);
                onFinished.setOnFinished(_ -> this.viewModel.requestRemoving());
                onFinished.playFromStart();
            }
        }
    }
}
