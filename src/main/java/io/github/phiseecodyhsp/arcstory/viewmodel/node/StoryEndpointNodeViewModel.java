package io.github.phiseecodyhsp.arcstory.viewmodel.node;

import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class StoryEndpointNodeViewModel extends ButtonNodeViewModel {

    private final ObjectProperty<ResourceLocation> storyLocation;

    private final ObjectProperty<ResourceLocation> conditionLocation;

    public StoryEndpointNodeViewModel(@NotNull String title,
                                      @NotNull ResourceLocation illustrationLocation,
                                      @Nullable ResourceLocation conditionLocation,
                                      @NotNull ResourceLocation storyLocation,
                                      @Nullable Consumer<ResourceLocation> onConditionShownCallback,
                                      @NotNull Consumer<ResourceLocation> onStoryShownCallback) {
        super(title, illustrationLocation);

        this.conditionLocation = new SimpleObjectProperty<>(conditionLocation);
        this.storyLocation = new SimpleObjectProperty<>(storyLocation);

        this.setOnMouseClicked(_ -> onConditionShownCallback.accept(this.conditionLocation.get()));
    }

    public ResourceLocation getStoryLocation() {
        return this.storyLocation.get();
    }

    public ObjectProperty<ResourceLocation> storyLocationProperty() {
        return this.storyLocation;
    }
}
