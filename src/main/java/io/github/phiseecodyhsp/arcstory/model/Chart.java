package io.github.phiseecodyhsp.arcstory.model;

import io.github.phiseecodyhsp.arcstory.model.difficulty.Difficulty;
import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;
import io.github.phiseecodyhsp.arcstory.util.MathUtil;

/**
 * Arcaea 谱面基本信息.
 */
public final class Chart {
    public final String music;
    public final ResourceLocation musicLocation;
    public final String composer;
    public final String bpm;
    public final Difficulty difficulty;
    public final double rating;
    public final String level;
    public final ResourceLocation illustrationLocation;
    public final String illustrator;
    public final String noteDesigner;
    public final Paradigms paradigms;

    public Chart(String music,
                 ResourceLocation musicLocation,
                 String composer,
                 double leftBPM,
                 double rightBPM,
                 Difficulty difficulty,
                 double rating,
                 ResourceLocation illustrationLocation,
                 String illustrator,
                 String noteDesigner,
                 Paradigms paradigms) {
        this.music = music;
        this.musicLocation = musicLocation;
        this.composer = composer;
        this.difficulty = difficulty;
        this.rating = rating;
        this.level = MathUtil.ratingToLevel(this.rating);
        this.illustrationLocation = illustrationLocation;
        this.illustrator = illustrator;
        this.noteDesigner = noteDesigner;
        this.paradigms = paradigms;

        // BPM 左右值相等时视为不变, 只显示单个数字
        this.bpm = leftBPM == rightBPM ? MathUtil.doubleToString(leftBPM) : MathUtil.doubleToString(leftBPM) + "-" + MathUtil.doubleToString(rightBPM);
    }
}
