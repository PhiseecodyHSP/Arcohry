package io.github.phiseecodyhsp.arcstory.viewmodel;

import io.github.phiseecodyhsp.arcstory.view.StoryBranch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @see StoryBranch
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
