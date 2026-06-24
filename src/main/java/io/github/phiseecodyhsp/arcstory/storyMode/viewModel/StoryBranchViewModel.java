package io.github.phiseecodyhsp.arcstory.storyMode.viewModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @see io.github.phiseecodyhsp.arcstory.storyMode.view.StoryBranch
 *
 * @author RikkaKawaii0612
 */
public class StoryBranchViewModel {
    private final ObservableList<StoryNodeViewModel> storyNodes;

    public StoryBranchViewModel() {
        this.storyNodes = FXCollections.observableArrayList();
    }

    public ObservableList<StoryNodeViewModel> getStoryNodes() {
        return this.storyNodes;
    }
}
