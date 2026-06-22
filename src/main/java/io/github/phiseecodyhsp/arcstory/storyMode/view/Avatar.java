package io.github.phiseecodyhsp.arcstory.storyMode.view;

import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;
import io.github.phiseecodyhsp.arcstory.ui.util.PropertyUtil;
import io.github.phiseecodyhsp.arcstory.util.MathUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Arcaea 头像栏. 这是一个旋转了 45 度的正方形头像展示栏. 头像素材要自己调成菱形.
 *
 * @see io.github.phiseecodyhsp.arcstory.storyMode.view.node.AvatarNode
 *
 * @author RikkaKawaii0612
 */
public class Avatar extends StackPane {

    /**
     * 头像的边框宽度.
     */
    private static final double AVATAR_BORDER_WIDTH = 2.0D;

    /**
     * 边界圆角大小.
     */
    private static final double NODE_BORDER_ARC_SIZE = 5.0D;

    private final ObjectProperty<ResourceLocation> avatarLocation;

    /**
     * 构造一个 Arcaea 头像栏.
     *
     * @param avatarLocation 头像的图片路径
     * @param sideLength 正方形边长
     * @param borderPaint 边框填料
     * @param dropShadow 阴影, 留空则不应用阴影
     */
    public Avatar(@Nullable ResourceLocation avatarLocation,
                  double sideLength,
                  @NotNull Paint borderPaint,
                  @Nullable DropShadow dropShadow) {
        this.avatarLocation = new SimpleObjectProperty<>(avatarLocation);

        ImageView avatarView = new ImageView();
        avatarView.imageProperty().bind(PropertyUtil.createImage(this.avatarLocation));
        avatarView.setFitWidth(MathUtil.SQRT_2 * (sideLength - 2.0D * AVATAR_BORDER_WIDTH));
        avatarView.setPreserveRatio(true);

        Rectangle border = new Rectangle(sideLength, sideLength);
        border.setFill(borderPaint);
        border.setArcWidth(NODE_BORDER_ARC_SIZE);
        border.setArcHeight(NODE_BORDER_ARC_SIZE);
        border.setRotate(45.0D);
        border.setEffect(dropShadow);

        getChildren().addAll(border, avatarView);
    }

    public ResourceLocation getAvatarLocation() {
        return this.avatarLocation.get();
    }

    public ObjectProperty<ResourceLocation> avatarLocationProperty() {
        return this.avatarLocation;
    }
}
