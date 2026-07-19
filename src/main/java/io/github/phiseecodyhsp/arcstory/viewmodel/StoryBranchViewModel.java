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
        this.storyNodes.addListener(this::onStoryNodesAdded);
    }

    public ObservableList<StoryNodeViewModel> getStoryNodes() {
        return this.storyNodes;
    }

    private void onStoryNodesAdded(ListChangeListener.Change<? extends StoryNodeViewModel> change) {
        while (change.next()) {
            if (change.wasAdded()) {
                List<? extends StoryNodeViewModel> added = change.getAddedSubList();

                //为每一个新添加的节点添加监听器
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

                //将已添加监听器的节点添加进该 HashSet 中, 防止重复添加
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

            //若节点为分支中的第一个, enable 其
            if (i == 0) {
                storyNode.enable();
            }

            //enable 头像节点及已 unlock 的 ButtonNode 的下一节点
            if ((storyNode instanceof AvatarNodeViewModel && storyNode.isEnabled()) ||
                    (storyNode instanceof ButtonNodeViewModel buttonNode && !buttonNode.isLocked())) {
                nextStoryNode.enable();
            }
        }
    }
}
