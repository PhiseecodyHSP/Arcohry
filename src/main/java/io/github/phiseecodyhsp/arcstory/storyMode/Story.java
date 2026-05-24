package io.github.phiseecodyhsp.arcstory.storyMode;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.phiseecodyhsp.arcstory.storage.Resources;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

//TODO
public class Story extends StackPane {
    public final boolean withCG;

    private final List<Item> items;
    private final ImageView lastCg = new ImageView();
    private final ImageView currentCg = new ImageView();
    private final Label label = new Label();

    public Story(String jsonPath) throws IOException {
        items = new ObjectMapper().readValue(Resources.ofStream(jsonPath), new TypeReference<>() {});
        withCG = Objects.equals(items.getFirst().type, Item.CG_TYPE);
        items.forEach(i -> {
            if (!Objects.equals(i.type, Item.CG_TYPE) && !Objects.equals(i.type, Item.TEXT_TYPE)) {
                throw new IllegalStateException(
                        "A " + Item.class.getSimpleName() + "'s type must be \"cg\" or \"text\", " +
                                "but found \"" + i.type + "\"");
            }
        });
    }

    public void play(ChapterPane parent) throws IOException {
        parent.getChildren().add(this);

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            if (Objects.equals(item.type, Item.CG_TYPE)) {

            } else {
                String text = new String(Resources.ofStream(item.path).readAllBytes(), StandardCharsets.UTF_8);
            }
        }
    }

    private static class Item {
        private static final String CG_TYPE = "cg";
        private static final String TEXT_TYPE = "text";

        @JsonProperty
        private String type;
        @JsonProperty
        private String path;

        private Item() {}

        private Item(String type, String path) {
            this.type = type;
            this.path = path;
        }
    }
}
