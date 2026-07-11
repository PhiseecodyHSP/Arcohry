package io.github.phiseecodyhsp.arcstory.model;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Arcaea 谱面难度, 分为 Past, Present, Future, Eternal 和 Beyond.
 */
public enum Difficulty {
    PST("Past"),
    PRS("Present"),
    FTR("Future"),
    BYD("Beyond"),
    ETR("Eternal");

    private final String name;

    Difficulty(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @JsonCreator
    public static Difficulty fromString(String string) {
        for (Difficulty d : Difficulty.values()) {
            if (d.name().equalsIgnoreCase(string)) {
                return d;
            }
        }
        throw new IllegalArgumentException("Difficulty '" + string + "' does not exist");
    }
}
