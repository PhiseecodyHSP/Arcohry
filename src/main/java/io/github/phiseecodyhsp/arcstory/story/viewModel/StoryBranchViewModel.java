package io.github.phiseecodyhsp.arcstory.story.viewModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @see io.github.phiseecodyhsp.arcstory.story.view.StoryBranch
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
