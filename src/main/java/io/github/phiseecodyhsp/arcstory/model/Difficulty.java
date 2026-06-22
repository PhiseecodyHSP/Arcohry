package io.github.phiseecodyhsp.arcstory.model;

/**
 * Arcaea 谱面难度, 分为 Past, Present, Future, Eternal 和 Beyond.
 */
public enum Difficulty {
    PST("Past"),
    PRS("Present"),
    FTR("Future"),
    ETR("Eternal"),
    BYD("Beyond");

    private final String name;

    Difficulty(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
