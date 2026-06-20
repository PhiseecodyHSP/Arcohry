package io.github.phiseecodyhsp.arcstory.story.view;

import io.github.phiseecodyhsp.arcstory.story.StoryNodeRegistry;
import io.github.phiseecodyhsp.arcstory.story.view.node.StoryNode;
import io.github.phiseecodyhsp.arcstory.story.viewModel.StoryBranchViewModel;
import io.github.phiseecodyhsp.arcstory.story.viewModel.StoryNodeViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * 故事分支渲染视图.
 *
 * <p>其包含若干 {@link StoryNode} 作为分支上的每一个节点, 按顺序从左到右排列.
 * 每相邻两个节点之间都有一条明线或暗线相连, 取决于节点启用或不启用.
 *
 * @see StoryNode
 *
 * @author RikkaKawaii0612
 */
public class StoryBranch extends StackPane {

    /**
     * 故事分支的线索宽度.
     */
    private static final double BRANCH_LINE_WIDTH = 2.0D;

    /**
     * 故事节点的间距.
     */
    private static final double NODE_SPACING = 250.0D;

    private final StoryBranchViewModel viewModel;

    private final ObservableMap<StoryNodeViewModel, StoryNode<?>> storyNodes = FXCollections.observableHashMap();

    private final ObservableList<Node> lines = FXCollections.observableArrayList();

    public StoryBranch(StoryBranchViewModel viewModel) {
        this.viewModel = viewModel;

        // 监听 ViewModel, 自动生成新的节点 View
        this.viewModel.getStoryNodes().addListener(this::onStoryNodesChanged);
    }

    private void onStoryNodesChanged(ListChangeListener.Change<? extends StoryNodeViewModel> change) {
        while (change.next()) {
            if (change.wasAdded()) {
                for (StoryNodeViewModel viewModel : change.getAddedSubList()) {
                    StoryNode<?> storyNode = StoryNodeRegistry.create(viewModel);
                    this.storyNodes.put(viewModel, storyNode);
                    this.getChildren().add(change.getFrom(), storyNode);
                }
            }
            if (change.wasRemoved()) {
                for (StoryNodeViewModel viewModel : change.getRemoved()) {
                    StoryNode<?> storyNode = this.storyNodes.remove(viewModel);
                    if (storyNode != null) {
                        this.getChildren().remove(storyNode);
                    }
                }
            }
        }

        int count = this.viewModel.getStoryNodes().size();

        // 线索数量过少时补充, 过多时移除
        while (this.lines.size() < count - 1) {
            Rectangle line = new Rectangle(NODE_SPACING, BRANCH_LINE_WIDTH, Color.WHITE);
            this.lines.add(line);
            this.getChildren().add(line);
        }

        while (this.lines.size() > count - 1) {
            Node node = this.lines.removeLast();
            this.getChildren().remove(node);
        }
        requestLayout();
    }

    @Override
    protected void layoutChildren() {
        ObservableList<StoryNodeViewModel> list = this.viewModel.getStoryNodes();
        int count = list.size();

        // 按顺序从左到右布局节点
        for (int i = 0; i < count; i++) {
            StoryNodeViewModel viewModel = list.get(i);
            StoryNode<?> storyNode = this.storyNodes.get(viewModel);
            storyNode.setTranslateX(NODE_SPACING * i);
        }

        // 按顺序从左到右摆放线索, 并设置暗线透明度
        for (int i = 0; i < this.lines.size(); i++) {
            Node node = this.lines.get(i);
            if (!list.get(1 + i).isEnabled()) {
                node.setOpacity(StoryNode.DISABLED_OPACITY);
            }
            node.setTranslateX(NODE_SPACING * i);
        }

        super.layoutChildren();
    }
}
