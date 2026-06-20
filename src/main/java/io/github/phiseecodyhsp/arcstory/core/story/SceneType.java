package io.github.phiseecodyhsp.arcstory.core.story;

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

    @JsonValue
    public String getValue() {
        return value;
    }
}
