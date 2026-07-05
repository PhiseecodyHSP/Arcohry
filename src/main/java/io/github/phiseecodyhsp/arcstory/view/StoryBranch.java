package io.github.phiseecodyhsp.arcstory.view;

import io.github.phiseecodyhsp.arcstory.view.node.StoryNode;
import io.github.phiseecodyhsp.arcstory.viewmodel.StoryBranchViewModel;
import io.github.phiseecodyhsp.arcstory.viewmodel.node.StoryNodeViewModel;
import io.github.phiseecodyhsp.arcstory.util.Alerts;
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
     * 故事节点禁用时的不透明度.
     */
    public static final double DISABLED_OPACITY = 0.75D;

    /**
     * 故事分支的线索宽度.
     */
    private static final double BRANCH_LINE_WIDTH = 2.0D;

    /**
     * 故事节点的间距.
     */
    public static final double NODE_SPACING = 250.0D;

    private final StoryBranchViewModel viewModel;

    private final ObservableMap<StoryNodeViewModel, StoryNode<?>> storyNodes = FXCollections.observableHashMap();

    private final ObservableList<Rectangle> lines = FXCollections.observableArrayList();

    private final ObservableList<Node> opaque;

    private final ObservableList<Node> translucent;

    public StoryBranch(StoryBranchViewModel viewModel) {
        this.viewModel = viewModel;
        this.setMaxSize(0.0D, 0.0D);

        StackPane opaque = new StackPane();
        StackPane translucent = new StackPane();
        translucent.setOpacity(DISABLED_OPACITY);
        this.opaque = opaque.getChildren();
        this.translucent = translucent.getChildren();
        this.getChildren().addAll(translucent, opaque);

        // 监听 ViewModel, 自动生成新的节点 View
        this.viewModel.getStoryNodes().addListener(this::onStoryNodesChanged);
        this.viewModel.getStoryNodes().forEach(this::addStoryNode);
        this.updateLines();
        this.updateGrouping();
    }

    private void onStoryNodesChanged(ListChangeListener.Change<? extends StoryNodeViewModel> change) {
        while (change.next()) {
            if (change.wasAdded()) {
                for (StoryNodeViewModel viewModel : change.getAddedSubList()) {
                    this.addStoryNode(viewModel);
                }
            }
            if (change.wasRemoved()) {
                for (StoryNodeViewModel viewModel : change.getRemoved()) {
                    this.removeStoryNode(viewModel);
                }
            }
        }

        this.updateLines();
        this.updateGrouping();
        requestLayout();
    }

    private void addStoryNode(StoryNodeViewModel viewModel) {
        StoryNode<?> storyNode = StoryNodeRegistry.create(viewModel);
        if (storyNode == null) {
            Alerts.alertException(new IllegalArgumentException("Story Node '" + viewModel.getClass().getName() + "' is not registered"));
            return;
        }
        this.storyNodes.put(viewModel, storyNode);
    }

    private void removeStoryNode(StoryNodeViewModel viewModel) {
        this.storyNodes.remove(viewModel);
    }

    private void updateLines() {
        int count = this.viewModel.getStoryNodes().size();

        // 线索数量过少时补充, 过多时移除
        while (this.lines.size() < count - 1) {
            Rectangle line = new Rectangle(NODE_SPACING, BRANCH_LINE_WIDTH, Color.WHITE);
            this.lines.add(line);
        }

        while (this.lines.size() > count - 1) {
            Node node = this.lines.removeLast();
            this.getChildren().remove(node);
        }
    }

    private void updateGrouping() {
        this.opaque.clear();
        this.translucent.clear();

        ObservableList<StoryNodeViewModel> list = this.viewModel.getStoryNodes();
        if (list.isEmpty()) {
            return;
        }
        int count = list.size();
        StoryNodeViewModel first = list.getFirst();
        boolean translucent = !first.isEnabled();
        (translucent ? this.translucent : this.opaque).add(this.storyNodes.get(first));

        for (int i = 1; i < count; i++) {
            StoryNodeViewModel viewModel = list.get(i);
            if (!translucent && !viewModel.isEnabled()) {
                translucent = true;
            }
            ObservableList<Node> group = translucent ? this.translucent : this.opaque;
            group.add(this.storyNodes.get(viewModel));
            group.addFirst(this.lines.get(i - 1));
        }
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
            Rectangle line = this.lines.get(i);
            line.setTranslateX(0.5D * line.getWidth() + NODE_SPACING * i);
        }

        super.layoutChildren();
    }
}
