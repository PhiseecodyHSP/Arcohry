package io.github.phiseecodyhsp.arcstory.storage;

public final class Charts {
    private Charts() {}

    public static final Chart Tutorial_PST;

    static {
        Tutorial_PST = new Chart(
                "Tutorial",
                "",
                "ak+q",
                128, 128,
                Chart.Difficulty.PST,
                1,
                Resources.Tutorial_ILLUSTRTION,
                null,
                null,
                Chart.Paradigms.LIGHT);
    }
}
