package io.github.phiseecodyhsp.arcstory.model;

import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;

public final class Charts {
    private Charts() {}

    public static final Chart TUTORIAL_PST;

    static {
        TUTORIAL_PST = new Chart(
                "Tutorial",
                new ResourceLocation("audios", "null"), // TODO
                "ak+q",
                128, 128,
                Difficulty.PST,
                DifficultyLevel.LEVEL_1,
                new ResourceLocation("images", "tutorial_illustration"),
                null,
                null,
                Paradigms.LIGHT);
    }
}
