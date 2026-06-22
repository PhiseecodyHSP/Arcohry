package io.github.phiseecodyhsp.arcstory.story.viewModel;

import io.github.phiseecodyhsp.arcstory.model.Partner;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.jetbrains.annotations.NotNull;

/**
 * @see io.github.phiseecodyhsp.arcstory.story.view.node.AvatarNode
 *
 * @author RikkaKawaii0612
 */
public class AvatarNodeViewModel extends StoryNodeViewModel {
    private final ObjectProperty<Partner> partner;

    public AvatarNodeViewModel(@NotNull Partner partner) {
        super();
        this.partner = new SimpleObjectProperty<>(partner);
    }

    public Partner getPartner() {
        return this.partner.get();
    }

    public ObjectProperty<Partner> partnerProperty() {
        return this.partner;
    }
}
