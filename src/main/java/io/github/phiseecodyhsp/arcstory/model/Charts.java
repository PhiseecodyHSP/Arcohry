package io.github.phiseecodyhsp.arcstory.model;

import io.github.phiseecodyhsp.arcstory.res.ResourceLoader;
import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;

public final class Charts {
    private Charts() {}

    public static final Chart TUTORIAL_PST;

    static {
        TUTORIAL_PST = ResourceLoader.loadChart(ResourceLocation.chart("tutorial_pst"));
    }
}
