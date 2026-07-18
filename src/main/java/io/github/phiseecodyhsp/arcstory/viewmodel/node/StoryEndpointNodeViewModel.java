package io.github.phiseecodyhsp.arcstory.viewmodel.node;

import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class StoryEndpointNodeViewModel extends ButtonNodeViewModel {

    private final ObjectProperty<ResourceLocation> storyLocation;

    private final ObjectProperty<ResourceLocation> conditionLocation;

    public StoryEndpointNodeViewModel(@NotNull String title,
                                      @NotNull ResourceLocation illustrationLocation,
                                      @NotNull ResourceLocation storyLocation,
                                      @NotNull Consumer<ResourceLocation> onStoryShownCallback) {
        super(title, illustrationLocation);

        this.conditionLocation = new SimpleObjectProperty<>(null);
        this.storyLocation = new SimpleObjectProperty<>(storyLocation);

        this.enabledProperty().addListener((_, _, b) -> {
            if (b) {
                this.unlock();
            }
        });

        this.lockedProperty().addListener((_, _, b) -> {
            if (!b) {
                this.setOnMouseClicked(_ -> onStoryShownCallback.accept(this.storyLocation.get()));
            }
        });
    }

    public StoryEndpointNodeViewModel(@NotNull String title,
                                      @NotNull ResourceLocation illustrationLocation,
                                      @NotNull ResourceLocation conditionLocation,
                                      @NotNull ResourceLocation storyLocation,
                                      @NotNull Consumer<ResourceLocation> onConditionShownCallback,
                                      @NotNull Consumer<ResourceLocation> onStoryShownCallback) {
        super(title, illustrationLocation);

        this.conditionLocation = new SimpleObjectProperty<>(conditionLocation);
        this.storyLocation = new SimpleObjectProperty<>(storyLocation);

        this.enabledProperty().addListener((_, _, b) -> {
            if (b) {
                this.setOnMouseClicked(_ -> onConditionShownCallback.accept(this.conditionLocation.get()));
            }
        });

        this.lockedProperty().addListener((_, _, b) -> {
            if (!b) {
                this.setOnMouseClicked(_ -> onStoryShownCallback.accept(this.storyLocation.get()));
            }
        });
    }

    public ResourceLocation getStoryLocation() {
        return this.storyLocation.get();
    }

    public ObjectProperty<ResourceLocation> storyLocationProperty() {
        return this.storyLocation;
    }
}
