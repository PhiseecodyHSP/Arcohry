package io.github.phiseecodyhsp.arcstory.viewmodel.node;

import io.github.phiseecodyhsp.arcstory.model.Partner;
import io.github.phiseecodyhsp.arcstory.view.node.AvatarNode;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.jetbrains.annotations.NotNull;

/**
 * @see AvatarNode
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
