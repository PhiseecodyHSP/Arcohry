package io.github.phiseecodyhsp.arcstory.storyMode;

import io.github.phiseecodyhsp.arcstory.Util;
import io.github.phiseecodyhsp.arcstory.storage.Resources;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import org.jetbrains.annotations.NotNull;

//TODO
public class ChapterSelectionPane extends StackPane {
    private final ImageView neo = new ImageView(Resources.NEW_ICON);

    public ChapterSelectionPane(@NotNull String bgPath) {
        ImageView bg = new ImageView(bgPath);
        bg.setPreserveRatio(true);
        bg.setFitWidth(Util.getScreenWidth());

        //TODO
        neo.setPreserveRatio(true);
        neo.setFitWidth(StoryButton.NEW_ICON_SIZE);
        neo.setTranslateY(0);

        getChildren().add(bg);
    }

    public void add(ChapterPane pane, String imagePath) {
        
    }
}
