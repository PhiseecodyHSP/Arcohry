package io.github.phiseecodyhsp.arcstory.model;

public final class Charts {
    private Charts() {}

    public static final Chart Tutorial_PST;

    static {
        Tutorial_PST = new Chart(
                "Tutorial",
                "",
                "ak+q",
                128, 128,
                Difficulty.PST,
                DifficultyLevel.LEVEL_1,
                "images/Songs_tutorial.jpg",
                null,
                null,
                Paradigms.LIGHT);
    }
}
