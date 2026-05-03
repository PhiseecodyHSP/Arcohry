package io.github.phiseecodyhsp.demo;

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
                1.0,
                Resources.Tutorial_ILLUSTRTION,
                null,
                null,
                Chart.Paradigms.LIGHT);
    }
}
