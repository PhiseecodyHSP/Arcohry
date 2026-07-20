package io.github.phiseecodyhsp.arcstory.ui.base;

import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;

/**
 * @author HSP
 */
public enum Shutter {

    NORMAL(ResourceLocation.image("shutter_l"), ResourceLocation.image("shutter_r"), ResourceLocation.audio("shutter_close"), ResourceLocation.audio("shutter_open")),
    WITH(ResourceLocation.image("shutter_withoverlay_l"), ResourceLocation.image("shutter_withoverlay_r"), ResourceLocation.audio("shutter_course_close"), ResourceLocation.audio("shutter_course_open")),
    GRIEVOUS(ResourceLocation.image("shutter_grievouslady_l"), ResourceLocation.image("shutter_grievouslady_r"), ResourceLocation.audio("shutter_alt_close"), ResourceLocation.audio("shutter_alt_open")),
    FRACTURE(ResourceLocation.image("shutter_fractureray_l"), ResourceLocation.image("shutter_fractureray_r"), ResourceLocation.audio("shutter_alt_close"), ResourceLocation.audio("shutter_alt_open")),
    TEMPESTISSIMO(ResourceLocation.image("shutter_tempestissimo_l"), ResourceLocation.image("shutter_tempestissimo_r"), ResourceLocation.audio("shutter_alt_close"), ResourceLocation.audio("shutter_alt_open")),
    FINALE(ResourceLocation.image("shutter_finale_l"), ResourceLocation.image("shutter_finale_r"), ResourceLocation.audio("shutter_alt_close"), ResourceLocation.audio("shutter_alt_open")),
    ARGHENA(ResourceLocation.image("shutter_arghena_l"), ResourceLocation.image("shutter_arghena_r"), ResourceLocation.audio("shutter_alt_close"), ResourceLocation.audio("shutter_alt_open")),
    LEPHON(ResourceLocation.image("shutter_lephon_l"), ResourceLocation.image("shutter_lephon_r"), ResourceLocation.audio("shutter_alt_close"), ResourceLocation.audio("shutter_alt_open")),
    UNDYING(ResourceLocation.image("shutter_undyingmacula_l"), ResourceLocation.image("shutter_undyingmacula_r"), ResourceLocation.audio("shutter_alt_close"), ResourceLocation.audio("shutter_alt_open"));

    public final ResourceLocation left;
    public final ResourceLocation right;
    public final ResourceLocation open;
    public final ResourceLocation close;

    Shutter(ResourceLocation left, ResourceLocation right, ResourceLocation open, ResourceLocation close) {
        this.left = left;
        this.right = right;
        this.open = open;
        this.close = close;
    }
}
