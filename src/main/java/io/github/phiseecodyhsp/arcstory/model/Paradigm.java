package io.github.phiseecodyhsp.arcstory.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.EnumNamingStrategies;
import com.fasterxml.jackson.databind.annotation.EnumNaming;
import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;

/**
 * 谱面的色侧, 分为光芒侧, 纷争侧, 消色侧和 Lephon 侧 (Lucent Historia 包特有).
 *
 * @author RikkaKawaii0612, HSP
 */
@EnumNaming(EnumNamingStrategies.SnakeCaseStrategy.class)
public enum Paradigm {
    LIGHT(ResourceLocation.image("light")),
    CONFLICT(ResourceLocation.image("conflict")),
    ACHROMIC(ResourceLocation.image("achromic")),
    LEPHON(ResourceLocation.image("lephon"));

    private final ResourceLocation imageLocation;

    Paradigm(ResourceLocation imageLocation) {
        this.imageLocation = imageLocation;
    }

    public ResourceLocation getImageLocation() {
        return this.imageLocation;
    }

    @JsonCreator
    public static Paradigm fromString(String string) {
        for (Paradigm p : Paradigm.values()) {
            if (p.name().equalsIgnoreCase(string)) {
                return p;
            }
        }
        throw new IllegalArgumentException("Paradigm '" + string + "' does not exist");
    }
}
