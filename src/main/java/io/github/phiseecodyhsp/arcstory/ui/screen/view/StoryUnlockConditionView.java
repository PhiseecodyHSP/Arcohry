package io.github.phiseecodyhsp.arcstory.ui.screen.view;

import io.github.phiseecodyhsp.arcstory.res.ResourceLoader;
import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;
import io.github.phiseecodyhsp.arcstory.ui.screen.viewmodel.StoryUnlockConditionViewModel;
import io.github.phiseecodyhsp.arcstory.ui.util.Interpolators;
import io.github.phiseecodyhsp.arcstory.ui.util.ScreenMetrics;
import io.github.phiseecodyhsp.arcstory.util.MathUtil;
import io.github.phiseecodyhsp.arcstory.view.Avatar;
import io.github.phiseecodyhsp.arcstory.view.Effects;
import io.github.phiseecodyhsp.arcstory.view.StoryNodeUiConstants;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class StoryUnlockConditionView extends StackPane {

    public static final double TRANS_TIME = 0.25;

    private static final double LOWEST_SCALE = 0.75;

    private static final double ILLUSTRATION_WIDTH = StoryNodeUiConstants.SIDE_LENGTH * 2;

    private static final double BG_HEIGHT = ILLUSTRATION_WIDTH * 8 / 3;

    private static final Font FONT = ResourceLoader.loadFont(ResourceLocation.font("noto_sans_regular"), ILLUSTRATION_WIDTH / 7.5);

    private final StoryUnlockConditionViewModel viewModel;

    private final Rectangle shadow = new Rectangle(ScreenMetrics.SCREEN_WIDTH, ScreenMetrics.SCREEN_HEIGHT);

    private final ScaleTransition onAddedST;

    private final ScaleTransition onRemovedST;

    private final FadeTransition onContentAddedFT;

    private final FadeTransition onContentRemovedFT;

    public StoryUnlockConditionView(StoryUnlockConditionViewModel viewModel) {
        this.viewModel = viewModel;

        ImageView illustration = new ImageView(ResourceLoader.loadImage(this.viewModel.getChart().illustrationLocation()));
        illustration.setEffect(Effects.OUTER_GLOW);
        illustration.setPreserveRatio(true);
        illustration.setFitWidth(ILLUSTRATION_WIDTH);

        this.shadow.setOpacity(0);

        Label label;
        ImageView bg;
        StackPane contentPane;
        if (viewModel.needsPartner()) {
            illustration.setTranslateY(ILLUSTRATION_WIDTH - BG_HEIGHT / 2.0D);

            bg = new ImageView(ResourceLoader.loadImage(ResourceLocation.image("suc_bg1")));

            label = new Label("使用搭档“" + this.viewModel.getPartner().name() + "”通关“" + this.viewModel.getChart().music() + "”以解锁此故事。");
            label.setTranslateY(BG_HEIGHT / 2.0D - ILLUSTRATION_WIDTH / 4.0D);

            Polygon arrow = new Polygon(
                    0, -ILLUSTRATION_WIDTH / 15.0D / MathUtil.SQRT_3,
                    ILLUSTRATION_WIDTH / 30.0D, ILLUSTRATION_WIDTH / 30.0D / MathUtil.SQRT_3,
                    -ILLUSTRATION_WIDTH / 30.0D, ILLUSTRATION_WIDTH / 30.0D / MathUtil.SQRT_3);
            arrow.setFill(Color.WHITE);
            arrow.setTranslateY(ILLUSTRATION_WIDTH * 7.0D / 30.0D + StoryNodeUiConstants.BORDER_WIDTH / 4.0D);
            arrow.setEffect(Effects.OUTER_GLOW);

            StackPane partnerAvatarPane = new Avatar(
                    viewModel.getPartner().avatarLocation(),
                    ILLUSTRATION_WIDTH / 2.5D / MathUtil.SQRT_2,
                    Color.WHITE,
                    Effects.OUTER_GLOW);
            partnerAvatarPane.setTranslateY(BG_HEIGHT / 2 - ILLUSTRATION_WIDTH * 5 / 6);

            contentPane = new StackPane(bg, label, partnerAvatarPane, arrow, illustration);
        } else {
            illustration.setTranslateY(BG_HEIGHT / 2 - ILLUSTRATION_WIDTH * 3 / 2);

            bg = new ImageView(ResourceLoader.loadImage(ResourceLocation.image("suc_bg0")));

            label = new Label("通关“" + this.viewModel.getChart().music() + "”以解锁此故事。");
            label.setTranslateY((BG_HEIGHT - ILLUSTRATION_WIDTH) / 2);

            contentPane = new StackPane(bg, label, illustration);
        }

        label.setTextFill(Color.WHITE);
        label.setFont(FONT);

        bg.setPreserveRatio(true);
        bg.setFitHeight(BG_HEIGHT);

        contentPane.setMaxSize(0, 0);
        contentPane.setOpacity(0);
        contentPane.setScaleX(LOWEST_SCALE);
        contentPane.setScaleY(LOWEST_SCALE);

        this.onContentAddedFT = new FadeTransition(Duration.seconds(TRANS_TIME), contentPane);
        this.onContentAddedFT.setToValue(1);

        this.onContentRemovedFT = new FadeTransition(Duration.seconds(TRANS_TIME), contentPane);
        this.onContentRemovedFT.setToValue(0);

        this.onRemovedST = new ScaleTransition(Duration.seconds(TRANS_TIME), contentPane);
        this.onRemovedST.setToX(LOWEST_SCALE);
        this.onRemovedST.setToY(LOWEST_SCALE);
        this.onRemovedST.setInterpolator(Interpolators.CUBE_OUT);

        this.onAddedST = new ScaleTransition(Duration.seconds(TRANS_TIME), contentPane);
        this.onAddedST.setToX(1);
        this.onAddedST.setToY(1);
        this.onAddedST.setInterpolator(Interpolators.CUBE_IN);
        this.onAddedST.setOnFinished(_ ->
                this.shadow.setOnMouseClicked(_ -> {
                    this.onContentRemovedFT.playFromStart();
                    this.onRemovedST.playFromStart();
                    this.shadow.setOnMouseClicked(null);}));

        this.getChildren().addAll(this.shadow, contentPane);
    }

    public void show() {
        this.onRemovedST.setOnFinished(_ -> viewModel.requestRemoving());
        this.onContentAddedFT.playFromStart();
        this.onAddedST.playFromStart();
    }
}
