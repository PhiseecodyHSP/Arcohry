package io.github.phiseecodyhsp.arcstory.storyMode.screen.viewModel;

import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;
import io.github.phiseecodyhsp.arcstory.storyMode.viewModel.StoryBranchViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @see io.github.phiseecodyhsp.arcstory.storyMode.screen.StoryScreen
 *
 * @author RikkaKawaii0612
 */
public class StoryScreenViewModel {
    private final ObservableList<StoryBranchViewModel> storyBranches = FXCollections.observableArrayList();
    private final ObjectProperty<ResourceLocation> background;

    public StoryScreenViewModel(ResourceLocation background) {
        this.background = new SimpleObjectProperty<>(background);
    }

    public ObservableList<StoryBranchViewModel> getStoryBranches() {
        return this.storyBranches;
    }

    public ResourceLocation getBackground() {
        return this.background.get();
    }

    public ObjectProperty<ResourceLocation> backgroundProperty() {
        return this.background;
    }
}