package io.github.phiseecodyhsp.arcstory.storyMode;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.phiseecodyhsp.arcstory.storage.Resources;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

//TODO
public class Story extends StackPane {
    public final boolean withCG;

    private final List<Item> items;
    private final ImageView lastCg = new ImageView();
    private final ImageView currentCg = new ImageView();

    public Story(String jsonPath) throws IOException {
        items = new ObjectMapper().readValue(getClass().getResourceAsStream(
                "/io/github/phiseecodyhsp/arcstory/" + jsonPath), new TypeReference<>() {});
        withCG = Objects.equals(items.getFirst().type, Item.CG_TYPE);
    }

    public void play(ChapterPane parent) {
        parent.getChildren().add(this);
    }

    private static class Item {
        private static final String CG_TYPE = "cg";
        private static final String TEXT_TYPE = "text";

        private String type;
        private String path;

        private Item() {}

        private Item(String type, String path) {
            this.type = type;
            this.path = path;
        }
    }
}
