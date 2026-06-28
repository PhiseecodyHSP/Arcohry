package io.github.phiseecodyhsp.arcstory.viewmodel;

import io.github.phiseecodyhsp.arcstory.model.Chart;
import io.github.phiseecodyhsp.arcstory.model.Partner;
import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;
import io.github.phiseecodyhsp.arcstory.view.node.ButtonNode;
import javafx.beans.property.*;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @see ButtonNode
 *
 * @author RikkaKawaii0612
 */
public class ButtonNodeViewModel extends StoryNodeViewModel {
    private final StringProperty title;
    private final ObjectProperty<ResourceLocation> illustrationLocation;
    private final ObjectProperty<ResourceLocation> storyLocation;
    private final ObjectProperty<Chart> chart;
    private final ObjectProperty<Partner> partner;
    private final ObjectProperty<EventHandler<? super MouseEvent>> onMouseClicked;
    private final BooleanProperty neo;
    private final BooleanProperty enabled;
    private final BooleanProperty locked;

    public ButtonNodeViewModel(@NotNull String title,
                               @NotNull ResourceLocation illustrationLocation,
                               @NotNull ResourceLocation storyLocation,
                               @Nullable Chart chart,
                               @Nullable Partner partner) {
        this.title = new SimpleStringProperty(title);
        this.illustrationLocation = new SimpleObjectProperty<>(illustrationLocation);
        this.storyLocation = new SimpleObjectProperty<>(storyLocation);
        this.chart = new SimpleObjectProperty<>(chart);
        this.partner = new SimpleObjectProperty<>(partner);
        this.onMouseClicked = new SimpleObjectProperty<>();
        this.neo = new SimpleBooleanProperty(false);
        this.enabled = new SimpleBooleanProperty(false);
        this.locked = new SimpleBooleanProperty(false);

        this.locked.addListener((_, _, v) -> {
            if (!v) {
                this.onUnlocked();
            }
        });
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

    public ResourceLocation getStoryLocation() {
        return this.storyLocation.get();
    }

    public ObjectProperty<ResourceLocation> storyLocationProperty() {
        return this.storyLocation;
    }

    public Chart getChart() {
        return this.chart.get();
    }

    public ObjectProperty<Chart> chartProperty() {
        return this.chart;
    }

    public Partner getPartner() {
        return this.partner.get();
    }

    public ObjectProperty<Partner> partnerProperty() {
        return this.partner;
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

    public boolean isEnabled() {
        return this.enabled.get();
    }

    @Override
    public BooleanProperty enabledProperty() {
        return this.enabled;
    }

    public boolean isLocked() {
        return this.locked.get();
    }

    public BooleanProperty lockedProperty() {
        return this.locked;
    }

    private void onUnlocked() {
        this.enable();
    }
}
