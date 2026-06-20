package io.github.phiseecodyhsp.arcstory.story.view.node;

import io.github.phiseecodyhsp.arcstory.story.viewModel.StoryNodeViewModel;
import javafx.scene.layout.StackPane;

/**
 * 故事分支的节点.
 *
 * @author RikkaKawaii0612
 */
public abstract class StoryNode<T extends StoryNodeViewModel> extends StackPane {

    /**
     * 故事节点禁用时的不透明度.
     */
    public static final double DISABLED_OPACITY = 0.75;

    protected final T viewModel;

    protected StoryNode(T viewModel) {
        this.viewModel = viewModel;
        this.opacityProperty().bind(this.viewModel.enabledProperty().map(v -> v ? 1.0 : DISABLED_OPACITY));
    }
}
