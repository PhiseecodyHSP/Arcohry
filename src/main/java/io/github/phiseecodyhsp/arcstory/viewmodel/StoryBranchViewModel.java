package io.github.phiseecodyhsp.arcstory.viewmodel;

import io.github.phiseecodyhsp.arcstory.view.StoryBranch;
import io.github.phiseecodyhsp.arcstory.viewmodel.node.StoryNodeViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashSet;
import java.util.Set;

/**
 * @see StoryBranch
 *
 * @author RikkaKawaii0612
 */
public class StoryBranchViewModel {

    private final ObservableList<StoryNodeViewModel> storyNodes;

    private final Set<StoryNodeViewModel> listenedStoryNodes = new HashSet<>();

    public StoryBranchViewModel() {
        this.storyNodes = FXCollections.observableArrayList();
    }

    public ObservableList<StoryNodeViewModel> getStoryNodes() {
        return this.storyNodes;
    }
}
