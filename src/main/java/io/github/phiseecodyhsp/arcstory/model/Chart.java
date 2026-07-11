package io.github.phiseecodyhsp.arcstory.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;
import io.github.phiseecodyhsp.arcstory.util.MathUtil;

/**
 * Arcaea 谱面基本信息.
 */
public final class Chart {

    @JsonProperty
    private String music;

    @JsonProperty
    private ResourceLocation musicLocation;

    @JsonProperty
    private String composer;

    @JsonProperty
    private double leftBpm;

    @JsonProperty
    private double rightBpm;

    @JsonProperty
    private Difficulty difficulty;

    @JsonProperty
    private double rating;

    @JsonProperty
    private ResourceLocation illustrationLocation;

    @JsonProperty
    private String illustrator;

    @JsonProperty
    private String noteDesigner;

    @JsonProperty
    private Paradigm paradigm;

    public Chart() {}

    public String getMusic() {
        return this.music;
    }

    public ResourceLocation getMusicLocation() {
        return this.musicLocation;
    }

    public String getComposer() {
        return this.composer;
    }

    public String getBpm() {
        // BPM 左右值相等时视为不变, 只显示单个数字
        return this.leftBpm == this.rightBpm ? MathUtil.doubleToString(this.leftBpm) : MathUtil.doubleToString(this.leftBpm) + "-" + MathUtil.doubleToString(this.rightBpm);
    }

    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    public double getRating() {
        return this.rating;
    }

    public String getLevel() {
        return MathUtil.ratingToLevel(this.rating);
    }

    public ResourceLocation getIllustrationLocation() {
        return this.illustrationLocation;
    }

    public String getIllustrator() {
        return this.illustrator;
    }

    public String getNoteDesigner() {
        return this.noteDesigner;
    }

    public Paradigm getParadigm() {
        return this.paradigm;
    }
}
