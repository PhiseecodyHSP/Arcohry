package io.github.phiseecodyhsp.arcstory.storyMode;


import io.github.phiseecodyhsp.arcstory.Util;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import org.jetbrains.annotations.NotNull;

public class ChapterButton extends StackPane {
    private ChapterButton(ChapterPane pane, @NotNull String imagePath) {
        ImageView view = new ImageView(imagePath);

        setOnMouseClicked(_ -> Util.getSetStage(this).switchPane(pane));
    }
}
