package io.github.phiseecodyhsp.arcstory.core.story;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SceneType {
    CG("cg"),
    DIALOGUE("dialogue"),
    CHOICE("choice"),
    EFFECT("effect"),
    CHANGE_BG("change_bg"),
    END("end");

    private final String value;

    SceneType(String value) {
        this.value = value;
    }

    @JsonCreator
    public static SceneType fromValue(String value) {
        for (SceneType type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown SceneType: " + value);
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
