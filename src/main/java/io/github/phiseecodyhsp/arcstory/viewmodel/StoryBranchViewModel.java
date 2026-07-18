package io.github.phiseecodyhsp.arcstory.viewmodel;

import io.github.phiseecodyhsp.arcstory.view.StoryBranch;
import io.github.phiseecodyhsp.arcstory.viewmodel.node.AvatarNodeViewModel;
import io.github.phiseecodyhsp.arcstory.viewmodel.node.ButtonNodeViewModel;
import io.github.phiseecodyhsp.arcstory.viewmodel.node.StoryNodeViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.HashSet;
import java.util.List;
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
        this.storyNodes.addListener(this::onStoryNodesChanged);
    }

    public ObservableList<StoryNodeViewModel> getStoryNodes() {
        return this.storyNodes;
    }

    private void onStoryNodesChanged(ListChangeListener.Change<? extends StoryNodeViewModel> change) {
        while (change.next()) {
            if (change.wasAdded()) {
                List<? extends StoryNodeViewModel> added = change.getAddedSubList();

                added.forEach(storyNode -> {
                    if (!listenedStoryNodes.contains(storyNode)) {
                        if (storyNode instanceof AvatarNodeViewModel) {
                            storyNode.enabledProperty().addListener(_ -> updateStoryNodes());
                        }
                        if (storyNode instanceof ButtonNodeViewModel buttonNode) {
                            buttonNode.lockedProperty().addListener(_ -> updateStoryNodes());
                        }
                    }
                });

                listenedStoryNodes.addAll(added);
            }
        }
        updateStoryNodes();
    }

    private void updateStoryNodes() {
        int size = this.storyNodes.size();
        for (int i = 0; i + 1 < size; i++) {
            StoryNodeViewModel storyNode = this.storyNodes.get(i);
            StoryNodeViewModel nextStoryNode = this.storyNodes.get(i + 1);

            if (i == 0) {
                storyNode.enable();
            }

            if ((storyNode instanceof AvatarNodeViewModel && storyNode.isEnabled()) ||
                    (storyNode instanceof ButtonNodeViewModel buttonNode && !buttonNode.isLocked())) {
                nextStoryNode.enable();
            }
        }
    }
}
