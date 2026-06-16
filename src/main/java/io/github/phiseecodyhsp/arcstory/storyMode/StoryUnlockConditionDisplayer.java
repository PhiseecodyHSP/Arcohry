package io.github.phiseecodyhsp.arcstory.storyMode;

import io.github.phiseecodyhsp.arcstory.SetStage;
import io.github.phiseecodyhsp.arcstory.Util;
import io.github.phiseecodyhsp.arcstory.storage.Chart;
import io.github.phiseecodyhsp.arcstory.storage.Partner;
import io.github.phiseecodyhsp.arcstory.storage.Resources;
import javafx.animation.FadeTransition;
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

import static io.github.phiseecodyhsp.arcstory.storyMode.ChapterPane.StoryButtonPane.StoryButton.*;

public class StoryUnlockConditionDisplayer extends StackPane {
    public static final double TRANS_TIME = 0.25;
    private static final double LOWEST_SCALE_RATIO = 0.75;
    private static final double ILLUSTRATION_WIDTH = SIDE_LENGTH * 2;
    private static final double BG_HEIGHT = ILLUSTRATION_WIDTH * 8 / 3.0;
    private static final Font FONT = Resources.getFont("fonts/NotoSansCJKsc-Regular.ttf", ILLUSTRATION_WIDTH / 7.5);
    public static final DropShadow GLOW = new DropShadow(OUTER_GLOW_INTENSITY, Color.WHITE);
    private static final double SQRT_3 = Math.sqrt(3);

    private final Label condition = new Label();
    private final ImageView bg = new ImageView();
    private final ImageView illustration = new ImageView();
    private final Rectangle shadow = new Rectangle(Util.PRIMARY_SCREEN_WIDTH, Util.PRIMARY_SCREEN_HEIGHT);
    private final Polygon arrow = new Polygon(
            0, -ILLUSTRATION_WIDTH / 15.0 / SQRT_3,
            ILLUSTRATION_WIDTH / 30.0, ILLUSTRATION_WIDTH / 30.0 / SQRT_3,
            -ILLUSTRATION_WIDTH / 30.0, ILLUSTRATION_WIDTH / 30.0 / SQRT_3);
    private final StackPane partnerAvatarPane = Partner.getAvatarPane(
            Resources.Tairitsu_AWAKEN_AVATAR,
            ILLUSTRATION_WIDTH / 2.5 / Util.SQRT_2,
            Color.WHITE, GLOW);
    private final StackPane pane = new StackPane(bg, condition, partnerAvatarPane, arrow, illustration);
    private final ScaleTransition onAddedST = new ScaleTransition(Duration.seconds(TRANS_TIME), pane);
    private final ScaleTransition onRemovedST = new ScaleTransition(Duration.seconds(TRANS_TIME), pane);
    private final FadeTransition onContentAddedFT = new FadeTransition(Duration.seconds(TRANS_TIME), pane);
    private final FadeTransition onContentRemovedFT = new FadeTransition(Duration.seconds(TRANS_TIME), pane);

    public StoryUnlockConditionDisplayer() {
        condition.setTextFill(Color.WHITE);
        condition.setFont(FONT);

        illustration.setEffect(GLOW);
        illustration.setPreserveRatio(true);
        illustration.setFitWidth(ILLUSTRATION_WIDTH);

        bg.setPreserveRatio(true);
        bg.setFitHeight(BG_HEIGHT);

        pane.setMaxSize(0, 0);
        pane.setOpacity(0);
        pane.setScaleX(LOWEST_SCALE_RATIO);
        pane.setScaleY(LOWEST_SCALE_RATIO);

        this.partnerAvatarPane.setTranslateY(BG_HEIGHT / 2.0 - ILLUSTRATION_WIDTH * 5 / 6.0);
        arrow.setFill(Color.WHITE);
        arrow.setTranslateY(ILLUSTRATION_WIDTH * 7 / 30.0 + BORDER_WIDTH / 4.0);
        arrow.setEffect(GLOW);

        shadow.setOpacity(0);

        onAddedST.setToX(1);
        onAddedST.setToY(1);
        onAddedST.setInterpolator(Util.EASE_IN);
        onAddedST.setOnFinished(_ -> shadow.setOnMouseClicked(_ -> {
            onContentRemovedFT.playFromStart();
            onRemovedST.playFromStart();
            shadow.setOnMouseClicked(null);
        }));
        onRemovedST.setToX(LOWEST_SCALE_RATIO);
        onRemovedST.setToY(LOWEST_SCALE_RATIO);
        onRemovedST.setInterpolator(Util.EASE_OUT);
        onContentAddedFT.setToValue(1);
        onContentRemovedFT.setToValue(0);

        getChildren().addAll(shadow, pane);
    }

    public void display(StackPane parent,
                        String music,
                        String composer,
                        @NotNull String illustrationPath,
                        String illustrator,
                        String noteDesigner,
                        @NotNull Chart.Paradigms paradigms) {
        parent.getChildren().add(this);

        bg.setImage(new Image(Resources.SUC_BG0));

        condition.setText("通关“" + music + "”以解锁此故事。");
        condition.setTranslateY((BG_HEIGHT - ILLUSTRATION_WIDTH) / 2);

        illustration.setImage(new Image(illustrationPath));
        illustration.setTranslateY(BG_HEIGHT / 2 - ILLUSTRATION_WIDTH * 3 / 2);
        illustration.setOnMouseClicked(_ -> {
            Util.getSetStage(this).playChart(SetStage.Loading.Type.NORMAL,
                    music,
                    composer,
                    illustrationPath,
                    illustrator,
                    noteDesigner,
                    paradigms);
            onContentAddedFT.stop();
            onAddedST.stop();
            onContentRemovedFT.playFromStart();
            onRemovedST.playFromStart();
        });

        onRemovedST.setOnFinished(_ -> parent.getChildren().remove(this));
        onContentAddedFT.playFromStart();
        onAddedST.playFromStart();

        this.partnerAvatarPane.setOpacity(0);
        this.arrow.setOpacity(0);
    }

    public void display(StackPane parent, @NotNull Chart chart) {
        display(parent,
                chart.music,
                chart.composer,
                chart.illustrationPath,
                chart.illustrator,
                chart.noteDesigner,
                chart.paradigms);
    }

    public void display(StackPane parent,
                        String music,
                        String composer,
                        @NotNull String illustrationPath,
                        String illustrator,
                        String noteDesigner,
                        @NotNull Chart.Paradigms paradigms,
                        String partner,
                        @NotNull String partnerPath) {
        parent.getChildren().add(this);

        Util.setPaneImage(this.partnerAvatarPane, 1, partnerPath);

        bg.setImage(new Image(Resources.SUC_BG1));

        condition.setText("使用搭档“" + partner + "”通关“" + music + "”以解锁此故事。");
        condition.setTranslateY(BG_HEIGHT / 2 - ILLUSTRATION_WIDTH / 4);

        illustration.setImage(new Image(illustrationPath));
        illustration.setTranslateY(ILLUSTRATION_WIDTH - BG_HEIGHT / 2);
        illustration.setOnMouseClicked(_ -> {
            Util.getSetStage(this).playChart(SetStage.Loading.Type.NORMAL,
                    music,
                    composer,
                    illustrationPath,
                    illustrator,
                    noteDesigner,
                    paradigms);
            onContentAddedFT.stop();
            onAddedST.stop();
            onContentRemovedFT.playFromStart();
            onRemovedST.playFromStart();
        });

        onRemovedST.setOnFinished(_ -> parent.getChildren().remove(this));
        onContentAddedFT.playFromStart();
        onAddedST.playFromStart();

        this.partnerAvatarPane.setOpacity(1);
        arrow.setOpacity(1);
    }

    public void display(StackPane parent, @NotNull Chart chart, @NotNull Partner partner) {
        display(parent,
                chart.music,
                chart.composer,
                chart.illustrationPath,
                chart.illustrator,
                chart.noteDesigner,
                chart.paradigms,
                partner.name(),
                partner.avatarPath());
    }

    public void display(StackPane parent, @NotNull Chart chart, String partner, @NotNull String partnerPath) {
        display(parent,
                chart.music,
                chart.composer,
                chart.illustrationPath,
                chart.illustrator,
                chart.noteDesigner,
                chart.paradigms,
                partner,
                partnerPath);
    }

    public void display(StackPane parent,
                        String music,
                        String composer,
                        @NotNull String illustrationPath,
                        String illustrator,
                        String noteDesigner,
                        @NotNull Chart.Paradigms paradigms,
                        @NotNull Partner partner) {
        display(parent,
                music,
                composer,
                illustrationPath,
                illustrator,
                noteDesigner,
                paradigms,
                partner.name(),
                partner.avatarPath());
    }
}
