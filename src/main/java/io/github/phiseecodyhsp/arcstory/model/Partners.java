package io.github.phiseecodyhsp.arcstory.model;

import static io.github.phiseecodyhsp.arcstory.res.ResourceLocation.image;

public final class Partners {
    private Partners() {}

    public static final Partner Hikari;
    public static final Partner DORO_C;

    static {
        Hikari = new Partner("Hikari", image("hikari_avatar"), image("hikari_illustration"));
        DORO_C = new Partner("DORO*C", image("doroc_avatar"), image("hikari_illustration")); // TODO: 占位符
    }
}
