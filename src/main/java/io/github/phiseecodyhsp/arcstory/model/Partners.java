package io.github.phiseecodyhsp.arcstory.model;

public final class Partners {
    private Partners() {}

    public static final Partner Hikari;
    public static final Partner DORO_C;

    static {
        Hikari = new Partner("Hikari", Resources.Hikari_AVATAR, Resources.Hikari_ILLUSTRATION);
        DORO_C = new Partner("DORO*C", Resources.DOROC_AVATAR, "");
    }
}
