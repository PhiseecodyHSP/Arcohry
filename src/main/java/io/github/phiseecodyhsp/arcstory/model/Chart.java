package io.github.phiseecodyhsp.arcstory.model;

import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;

/**
 * Arcaea 谱面基本信息.
 */
public final class Chart {
    public final String music;
    public final ResourceLocation musicLocation;
    public final String composer;
    public final String bpm;
    public final Difficulty difficulty;
    public final DifficultyLevel level;
    public final ResourceLocation illustrationLocation;
    public final String illustrator;
    public final String noteDesigner;
    public final Paradigms paradigms;

    public Chart(String music,
                 ResourceLocation musicLocation,
                 String composer,
                 double minBPM,
                 double maxBPM,
                 Difficulty difficulty,
                 DifficultyLevel level,
                 ResourceLocation illustrationLocation,
                 String illustrator,
                 String noteDesigner,
                 Paradigms paradigms) {
        this.music = music;
        this.musicLocation = musicLocation;
        this.composer = composer;
        this.difficulty = difficulty;
        this.level = level;
        this.illustrationLocation = illustrationLocation;
        this.illustrator = illustrator;
        this.noteDesigner = noteDesigner;
        this.paradigms = paradigms;

        // BPM 最大最小值相等时视为不变, 只显示单个数字
        this.bpm = minBPM == maxBPM ? String.valueOf(minBPM) : minBPM + "-" + maxBPM;
    }
}
