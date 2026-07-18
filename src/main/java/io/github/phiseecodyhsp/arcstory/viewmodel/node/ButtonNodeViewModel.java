package io.github.phiseecodyhsp.arcstory.viewmodel.node;

import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;
import io.github.phiseecodyhsp.arcstory.view.node.ButtonNode;
import javafx.beans.property.*;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import org.jetbrains.annotations.NotNull;

/**
 * @see ButtonNode
 *
 * @author RikkaKawaii0612
 */
public class ButtonNodeViewModel extends StoryNodeViewModel {

    private final StringProperty title;

    private final ObjectProperty<ResourceLocation> illustrationLocation;

    private final ObjectProperty<EventHandler<? super MouseEvent>> onMouseClicked;

    private final BooleanProperty neo;

    private final BooleanProperty locked;

    public ButtonNodeViewModel(@NotNull String title, @NotNull ResourceLocation illustrationLocation) {
        this.title = new SimpleStringProperty(title);
        this.illustrationLocation = new SimpleObjectProperty<>(illustrationLocation);
        this.onMouseClicked = new SimpleObjectProperty<>();
        this.neo = new SimpleBooleanProperty(false);
        this.locked = new SimpleBooleanProperty(true);
    }

    public String getTitle() {
        return this.title.get();
    }

    public StringProperty titleProperty() {
        return this.title;
    }

    public ResourceLocation getIllustrationLocation() {
        return this.illustrationLocation.get();
    }

    public ObjectProperty<ResourceLocation> illustrationLocationProperty() {
        return this.illustrationLocation;
    }

    public EventHandler<? super MouseEvent> getOnMouseClicked() {
        return this.onMouseClicked.get();
    }

    public void setOnMouseClicked(EventHandler<? super MouseEvent> action) {
        this.onMouseClicked.setValue(action);
    }

    public ObjectProperty<EventHandler<? super MouseEvent>> onMouseClickedProperty() {
        return this.onMouseClicked;
    }

    public boolean isNew() {
        return this.neo.get();
    }

    public BooleanProperty newProperty() {
        return this.neo;
    }

    public boolean isLocked() {
        return this.locked.get();
    }

    public BooleanProperty lockedProperty() {
        return this.locked;
    }

    public void unlock() {
        this.enable();
        this.locked.setValue(false);
        this.neo.setValue(true);
    }
}
