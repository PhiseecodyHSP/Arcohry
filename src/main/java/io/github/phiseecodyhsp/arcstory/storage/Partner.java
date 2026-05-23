package io.github.phiseecodyhsp.arcstory.storage;

import io.github.phiseecodyhsp.arcstory.Util;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;

import static io.github.phiseecodyhsp.arcstory.storyMode.StoryButton.*;

public record Partner(String name, @NotNull String avatarPath, @NotNull String illustrationPath) {
    public StackPane getAvatarPane(int sideLength, Paint borderColor) {
        ImageView avatarView = new ImageView(avatarPath);
        avatarView.setFitWidth(Util.doubleToEven((sideLength - 2 * BORDER_WIDTH) * Util.SQRT_2));
        avatarView.setPreserveRatio(true);

        Rectangle border = new Rectangle(sideLength, sideLength);
        border.setFill(borderColor);
        border.setArcWidth(ARC_SIZE);
        border.setArcHeight(ARC_SIZE);
        border.setRotate(45);
        border.setEffect(new DropShadow(
                OUTER_GLOW_INTENSITY,
                OUTER_GLOW_OFFSET,
                OUTER_GLOW_OFFSET,
                TRANSPARENT_BLACK));
         return new StackPane(border, avatarView);
    }
}
