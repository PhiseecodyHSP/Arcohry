package io.github.phiseecodyhsp.arcstory.ui.screen.view;

import io.github.phiseecodyhsp.arcstory.model.story.Paragraph;
import io.github.phiseecodyhsp.arcstory.res.ResourceLoader;
import io.github.phiseecodyhsp.arcstory.ui.screen.viewmodel.StoryViewModel;
import io.github.phiseecodyhsp.arcstory.util.PropertyUtil;
import io.github.phiseecodyhsp.arcstory.view.TextPlayer;
import io.github.phiseecodyhsp.arcstory.ui.util.Interpolators;
import io.github.phiseecodyhsp.arcstory.ui.util.ScreenMetrics;
import io.github.phiseecodyhsp.arcstory.view.Typewriter;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
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
    private static final double SWEEP_LINE_WIDTH = ScreenMetrics.getPrimaryScreenHeight() / 40.0D;

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
    public static final double TEXT_TRANSLATE_X = 125.0D;

    private final StoryViewModel viewModel;

    private final Rectangle shadow;

    private final ColorAdjust glow = new ColorAdjust();

    private final Polygon sweepLine;

    private final Polygon clipper;

    private final Timeline onCgAdded;

    private final FadeTransition onShadowAdded;

    private final Typewriter typewriter;

    public StoryView(StoryViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.currentStatusProperty().addListener(
                (_, _, v) -> this.onStatusChanged(v));

        this.setPickOnBounds(true);
        this.setOnMouseClicked(_ -> this.viewModel.proceed());

        Rectangle2D screenBounds = ScreenMetrics.getPrimaryScreenBounds();
        double width = screenBounds.getWidth();
        double height = screenBounds.getHeight();

        this.shadow = new Rectangle(width, height, Color.BLACK);
        this.shadow.setOpacity(0.0D);

        this.sweepLine = new Polygon(
                -SWEEP_LINE_WIDTH, 0.0D,
                -SWEEP_LINE_WIDTH - SWEEP_LINE_ROTATION * height, height,
                -SWEEP_LINE_ROTATION * height, height,
                0.0D, 0.0D);
        DropShadow sweepLineGlow = new DropShadow(0, Color.WHITE);
        DropShadow shadow1 = new DropShadow(0, Color.WHITE);
        DropShadow shadow2 = new DropShadow(0, Color.WHITE);
        sweepLineGlow.setInput(shadow1);
        shadow1.setInput(shadow2);
        this.sweepLine.setTranslateX(-width);
        this.sweepLine.setFill(Color.WHITE);
        this.sweepLine.setEffect(sweepLineGlow);

        this.clipper = new Polygon(
                -SWEEP_LINE_WIDTH, 0.0D,
                -SWEEP_LINE_WIDTH - SWEEP_LINE_ROTATION * height, height,
                width, height,
                width + SWEEP_LINE_ROTATION * height, 0.0D);

        ImageView bottomCg = new ImageView();
        bottomCg.imageProperty().bind(PropertyUtil.createImage(this.viewModel.bottomCgProperty()));
        bottomCg.setPreserveRatio(true);
        bottomCg.setFitWidth(width);
        bottomCg.setEffect(this.glow);

        ImageView topCg = new ImageView();
        topCg.imageProperty().bind(PropertyUtil.createImage(this.viewModel.topCgProperty()));
        topCg.setPreserveRatio(true);
        topCg.setFitWidth(width);
        topCg.setClip(this.clipper);
        topCg.setEffect(this.glow);
        this.clipper.translateYProperty().bind(topCg.imageProperty().map(
                image -> 0.5D * (width * image.getHeight() / image.getWidth() - height)));

        this.typewriter = new Typewriter();
        this.typewriter.setOnFinished(this.viewModel::markWaiting);
        TextPlayer textPlayer = new TextPlayer(this.typewriter);
        textPlayer.setMaxWidth(0.5D * width - TEXT_TRANSLATE_X);
        textPlayer.setTranslateX(-0.5D * width + TEXT_TRANSLATE_X + 0.5D * textPlayer.getMaxWidth());

        double time = 4.0D * TRANS_TIME;
        this.onCgAdded = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(
                                this.clipper.translateXProperty(),
                                -width - SWEEP_LINE_WIDTH - SWEEP_LINE_ROTATION * height),
                        new KeyValue(
                                this.sweepLine.translateXProperty(),
                                -0.5D * (width + SWEEP_LINE_WIDTH + SWEEP_LINE_ROTATION * height)),
                        new KeyValue(this.sweepLine.opacityProperty(), 0.5D),
                        new KeyValue(sweepLineGlow.radiusProperty(), 127.0D),
                        new KeyValue(shadow1.radiusProperty(), 127.0D),
                        new KeyValue(shadow2.radiusProperty(), 127.0D),
                        new KeyValue(this.glow.brightnessProperty(), 0.0D)),
                new KeyFrame(Duration.seconds(time / 32.0D),
                        new KeyValue(this.glow.brightnessProperty(), GLOW_BRIGHTNESS)),
                new KeyFrame(Duration.seconds(time),
                        new KeyValue(this.clipper.translateXProperty(), 0.0D, Interpolators.CUBE_IN),
                        new KeyValue(
                                this.sweepLine.translateXProperty(),
                                0.5D * (width + SWEEP_LINE_WIDTH + SWEEP_LINE_ROTATION * height),
                                Interpolators.CUBE_IN),
                        new KeyValue(this.sweepLine.opacityProperty(), 1.0D),
                        new KeyValue(sweepLineGlow.radiusProperty(), 0.0D),
                        new KeyValue(shadow1.radiusProperty(), 0.0D),
                        new KeyValue(shadow2.radiusProperty(), 0.0D),
                        new KeyValue(this.glow.brightnessProperty(), 0.0D)));
        this.onCgAdded.setOnFinished(_ -> this.viewModel.markWaiting());

        this.onShadowAdded = new FadeTransition(Duration.seconds(TRANS_TIME), this.shadow);
        this.onShadowAdded.setToValue(SHADOW_DARKNESS);
        this.onShadowAdded.setOnFinished(_ -> this.viewModel.proceed());

        this.getChildren().addAll(bottomCg, this.shadow, topCg, this.sweepLine, textPlayer);
    }

    public void start() {
        List<Paragraph> list = this.viewModel.getStory().getParagraphs();
        if (list.isEmpty()) {
            return;
        }

//        if (list.getFirst().type() == ParagraphType.CG) {
//            this.viewModel.proceed();
//        } else {
//            FadeTransition onShadowAdded = new FadeTransition(Duration.seconds(TRANS_TIME), this.shadow);
//            onShadowAdded.setToValue(SHADOW_DARKNESS);
//            onShadowAdded.setOnFinished(_ -> this.viewModel.proceed());
//            onShadowAdded.playFromStart();
//        }
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
