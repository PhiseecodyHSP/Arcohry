package io.github.phiseecodyhsp.arcstory.ui.screen.view;

import io.github.phiseecodyhsp.arcstory.ui.screen.viewmodel.StoryViewModel;
import io.github.phiseecodyhsp.arcstory.ui.util.Interpolators;
import io.github.phiseecodyhsp.arcstory.ui.util.ScreenMetrics;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

/**
 * 故事阅读的界面.
 *
 * @author RikkaKawaii0612
 */
public class StoryView {

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
     * CG 播放动画中荧光的亮度.
     */
    public static final double GLOW_BRIGHTNESS = 0.75D;

    private final StoryViewModel viewModel;

    private final ColorAdjust glow = new ColorAdjust();

    private final Polygon sweepLine;

    private final Polygon clipper;

    private final Timeline onCgAdded;

    public StoryView(StoryViewModel viewModel) {
        this.viewModel = viewModel;

        Rectangle2D screenBounds = ScreenMetrics.getPrimaryScreenBounds();
        double width = screenBounds.getWidth();
        double height = screenBounds.getHeight();

        this.sweepLine = new Polygon(
                -width, 0.0D,
                -SWEEP_LINE_WIDTH - SWEEP_LINE_ROTATION * height, height,
                -SWEEP_LINE_ROTATION * height, height,
                0.0D, 0.0D);

        this.clipper = new Polygon(
                -SWEEP_LINE_WIDTH, 0.0D,
                -SWEEP_LINE_WIDTH - SWEEP_LINE_ROTATION * height, height,
                width, height,
                width + SWEEP_LINE_ROTATION * height, 0.0D);

        ImageView lastCg = new ImageView();
        lastCg.setPreserveRatio(true);
        lastCg.setFitWidth(ScreenMetrics.getPrimaryScreenWidth());

        ImageView currentCg = new ImageView();
        currentCg.setPreserveRatio(true);
        currentCg.setFitWidth(ScreenMetrics.getPrimaryScreenWidth());
        currentCg.setClip(this.clipper);
        lastCg.setEffect(this.glow);
        currentCg.setEffect(this.glow);
        // textPane.setEffect(this.glow);

        DropShadow sweepLineGlow = new DropShadow(0, Color.WHITE);
        DropShadow shadow1 = new DropShadow(0, Color.WHITE);
        DropShadow shadow2 = new DropShadow(0, Color.WHITE);
        sweepLineGlow.setInput(shadow1);
        shadow1.setInput(shadow2);
        this.sweepLine.setFill(Color.WHITE);
        this.sweepLine.setEffect(sweepLineGlow);

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
        this.onCgAdded.setOnFinished(_ -> currentCg.setOnMouseClicked(_ -> this.playNext()));

//        onShadowAdded.setFromValue(0);
//        onShadowAdded.setToValue(LOWEST_BRIGHTNESS);
//        onRemoved.setToValue(0);
    }

    private void playNext() {
        StoryViewModel.Status status = this.viewModel.playNext();
        switch (status) {
            case TEXT -> {
                // TODO: Text
            }
            case CG -> {
                this.onCgAdded.play();
                // TODO
            }
            case FINISHED -> {
                // TODO: Finished
            }
        }
    }
}
