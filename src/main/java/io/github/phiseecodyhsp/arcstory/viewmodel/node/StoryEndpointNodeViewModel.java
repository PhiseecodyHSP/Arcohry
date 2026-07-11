package io.github.phiseecodyhsp.arcstory.viewmodel.node;

import io.github.phiseecodyhsp.arcstory.model.Chart;
import io.github.phiseecodyhsp.arcstory.model.Partner;
import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class StoryEndpointNodeViewModel extends ButtonNodeViewModel {

    private final ObjectProperty<ResourceLocation> storyLocation;

    public StoryEndpointNodeViewModel(@NotNull String title,
                                      @NotNull ResourceLocation illustrationLocation,
                                      @NotNull ResourceLocation storyLocation,
                                      @Nullable Chart chart,
                                      @Nullable Partner partner,
                                      @NotNull Consumer<ResourceLocation> onStoryShownCallback) {
        super(title, illustrationLocation, chart, partner);

        this.storyLocation = new SimpleObjectProperty<>(storyLocation);

        this.setOnMouseClicked(_ -> onStoryShownCallback.accept(this.storyLocation.get()));
    }

    public ResourceLocation getStoryLocation() {
        return this.storyLocation.get();
    }

    public ObjectProperty<ResourceLocation> storyLocationProperty() {
        return this.storyLocation;
    }
}
