package io.github.phiseecodyhsp.arcstory.storyMode;

import io.github.phiseecodyhsp.arcstory.Util;
import io.github.phiseecodyhsp.arcstory.storage.Resources;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import org.jetbrains.annotations.NotNull;

//TODO
public class ChapterButton extends StackPane {
    private final ImageView neo = new ImageView(Resources.NEW_ICON);

    private ChapterButton(ChapterPane pane, @NotNull String imagePath) {
        ImageView view = new ImageView(imagePath);

        setOnMouseClicked(_ -> Util.getSetStage(this).switchPane(pane));
    }
}
