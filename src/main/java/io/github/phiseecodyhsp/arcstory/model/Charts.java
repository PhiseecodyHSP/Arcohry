package io.github.phiseecodyhsp.arcstory.model;

import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;

public final class Charts {
    private Charts() {}

    public static final Chart TUTORIAL_PST;

    static {
        TUTORIAL_PST = new Chart(
                "Tutorial",
                ResourceLocation.audio("story_bgm"), // TODO: 占位符
                "ak+q",
                128, 128,
                Difficulty.PST,
                DifficultyLevel.LEVEL_1,
                ResourceLocation.image("tutorial_illustration"),
                null,
                null,
                Paradigms.LIGHT);
    }
}
