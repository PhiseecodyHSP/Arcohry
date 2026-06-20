package io.github.phiseecodyhsp.arcstory.model;

/**
 * Arcaea 谱面基本信息.
 */
public class Chart {
    public final String music;
    public final String musicPath;
    public final String composer;
    public final String bpm;
    public final Difficulty difficulty;
    public final DifficultyLevel level;
    public final String illustrationPath;
    public final String illustrator;
    public final String noteDesigner;
    public final Paradigms paradigms;

    public Chart(String music,
                 String musicPath,
                 String composer,
                 double minBPM,
                 double maxBPM,
                 Difficulty difficulty,
                 DifficultyLevel level,
                 String illustrationPath,
                 String illustrator,
                 String noteDesigner,
                 Paradigms paradigms) {
        this.music = music;
        this.musicPath = musicPath;
        this.composer = composer;
        this.difficulty = difficulty;
        this.level = level;
        this.illustrationPath = illustrationPath;
        this.illustrator = illustrator;
        this.noteDesigner = noteDesigner;
        this.paradigms = paradigms;

        // BPM 最大最小值相等时视为不变, 只显示单个数字
        this.bpm = minBPM == maxBPM ? String.valueOf(minBPM) : minBPM + "-" + maxBPM;
    }
}
