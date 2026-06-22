package io.github.phiseecodyhsp.arcstory.ui.util;

import io.github.phiseecodyhsp.arcstory.res.ResourceLoader;
import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;
import io.github.phiseecodyhsp.arcstory.util.Alerts;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;

public class PropertyUtil {
    public static ObjectBinding<Image> createImage(ObservableValue<ResourceLocation> value) {
        return Bindings.createObjectBinding(
                () -> {
                    ResourceLocation location = value.getValue();
                    try {
                        return ResourceLoader.loadImage(location);
                    } catch (Exception e) {
                        Alerts.alertException(e);
                        return null;
                    }
                },
                value);
    }
}
