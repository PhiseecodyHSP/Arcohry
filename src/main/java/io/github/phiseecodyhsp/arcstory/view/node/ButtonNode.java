package io.github.phiseecodyhsp.arcstory.view.node;

import io.github.phiseecodyhsp.arcstory.res.ResourceLoader;
import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;
import io.github.phiseecodyhsp.arcstory.view.Effects;
import io.github.phiseecodyhsp.arcstory.viewmodel.node.ButtonNodeViewModel;
import io.github.phiseecodyhsp.arcstory.util.PropertyUtil;
import io.github.phiseecodyhsp.arcstory.util.MathUtil;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**
 * 按钮故事节点, 可以用于触发事件.
 *
 * @author RikkaKawaii0612
 */
public class ButtonNode<T extends ButtonNodeViewModel> extends StoryNode<T> {

    /**
     * 按钮边长.
     */
    private static final double SIDE_LENGTH = 100.0D;

    /**
     * 按钮对角线长.
     */
    private static final double DIAGONAL_LENGTH = MathUtil.SQRT_2 * SIDE_LENGTH;

    /**
     * 按钮边框宽度.
     */
    private static final double BORDER_WIDTH = 2.0D;

    /**
     * 按钮边框圆角半径.
     */
    private static final double ARC_SIZE = 5.0D;

    /**
     * 按钮图像大小.
     */
    private static final double IMAGE_SIZE = SIDE_LENGTH - 2.0D * BORDER_WIDTH;

    /**
     * 新提示图标大小.
     */
    private static final double NEW_ICON_SIZE = SIDE_LENGTH * MathUtil.SQRT_2 / 3.0D;

    /**
     * 光标悬浮在按钮上时, 按钮变深的效果.
     */
    private static final ColorAdjust HOVERED = new ColorAdjust(0.0D, 0.0D, -0.25D, 0.0D);

    /**
     * 文本字体.
     */
    private static final Font FONT = ResourceLoader.loadFont(ResourceLocation.font("geosans_light"), 36.0D);

    public ButtonNode(T viewModel) {
        super(viewModel);

        Label label = new Label();
        label.textProperty().bind(viewModel.titleProperty());
        label.setFont(FONT);
        label.setTextFill(Color.WHITE);
        label.setRotate(-45.0D);
        label.setEffect(Effects.SHADOW);
        label.setMouseTransparent(true);
        label.visibleProperty().bind(viewModel.lockedProperty().not());

        Polygon banner = new Polygon(
                -IMAGE_SIZE / 2.0D, IMAGE_SIZE / 4.0D,
                -IMAGE_SIZE / 2.0D, IMAGE_SIZE / 2.0D,
                -IMAGE_SIZE / 4.0D, IMAGE_SIZE / 2.0D,
                IMAGE_SIZE / 2.0D, -IMAGE_SIZE / 4.0D,
                IMAGE_SIZE / 2.0D, -IMAGE_SIZE / 2.0D,
                IMAGE_SIZE / 4.0D, -IMAGE_SIZE / 2.0D);
        banner.setFill(Color.BLACK);
        banner.setOpacity(0.5D);
        banner.setMouseTransparent(true);

        ImageView newIcon = new ImageView(ResourceLoader.loadImage(ResourceLoader.resolvePath("images", "new_icon")));
        newIcon.setPreserveRatio(true);
        newIcon.setRotate(-45.0D);
        newIcon.setFitWidth(NEW_ICON_SIZE);
        newIcon.setTranslateY(-0.5833333333333333D * SIDE_LENGTH); /* 7 / 12 */
        newIcon.setMouseTransparent(true);
        newIcon.visibleProperty().bind(viewModel.newProperty());

        ImageView view = new ImageView();
        view.imageProperty().bind(PropertyUtil.createImage(this.viewModel.illustrationLocationProperty()));
        view.setFitWidth(IMAGE_SIZE);
        view.setPreserveRatio(true);
        view.setMouseTransparent(true);

        // TODO: 锁素材替换
        ImageView lock = new ImageView(ResourceLoader.loadImage(ResourceLoader.resolvePath("images", "init_illustration")));
        lock.setRotate(-45.0D);
        lock.setFitWidth(DIAGONAL_LENGTH / 3.0D);
        lock.setPreserveRatio(true);
        lock.setMouseTransparent(true);
        lock.visibleProperty().bind(viewModel.lockedProperty());

        Rectangle border = new Rectangle(SIDE_LENGTH, SIDE_LENGTH, Color.WHITE);
        border.setArcWidth(ARC_SIZE);
        border.setArcHeight(ARC_SIZE);
        border.setEffect(Effects.SHADOW);
        border.setOnMouseEntered(_ -> view.setEffect(HOVERED));
        border.setOnMouseExited(_ -> view.setEffect(null));
        border.onMouseClickedProperty().bind(viewModel.onMouseClickedProperty().map(
                eventHandler -> e -> {
                    this.viewModel.newProperty().setValue(false);
                    eventHandler.handle(e);
                }));

        this.setRotate(45.0D);
        this.getChildren().addAll(border, view, banner, label, newIcon, lock);
    }
}
