package io.github.phiseecodyhsp.arcstory.model;

public final class Partners {
    private Partners() {}

    public static final Partner Hikari;
    public static final Partner DORO_C;

    static {
        Hikari = new Partner("Hikari", "images/Partner_0_icon.png", "images/Partner_0_new.png");
        DORO_C = new Partner("DORO*C", "images/143px-Partner_doroc_awaken_icon.png", "");
    }
}
