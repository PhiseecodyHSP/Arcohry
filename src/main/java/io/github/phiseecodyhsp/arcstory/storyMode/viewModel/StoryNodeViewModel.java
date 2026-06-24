package io.github.phiseecodyhsp.arcstory.storyMode.viewModel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * @see io.github.phiseecodyhsp.arcstory.storyMode.view.node.StoryNode
 *
 * @author RikkaKawaii0612
 */
public abstract class StoryNodeViewModel {
    protected final BooleanProperty enabled = new SimpleBooleanProperty(false);

    protected StoryNodeViewModel() {
    }

    public boolean isEnabled() {
        return this.enabled.get();
    }

    public BooleanProperty enabledProperty() {
        return this.enabled;
    }

    public void enable() {
        this.enabled.set(true);
    }
}
