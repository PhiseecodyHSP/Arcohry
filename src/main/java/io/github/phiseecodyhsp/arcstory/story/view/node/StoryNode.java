package io.github.phiseecodyhsp.arcstory.story.view.node;

import io.github.phiseecodyhsp.arcstory.story.viewModel.StoryNodeViewModel;
import javafx.scene.layout.StackPane;

/**
 * 故事分支的节点.
 *
 * @author RikkaKawaii0612
 */
public abstract class StoryNode<T extends StoryNodeViewModel> extends StackPane {

    protected final T viewModel;

    protected StoryNode(T viewModel) {
        this.viewModel = viewModel;
    }
}
