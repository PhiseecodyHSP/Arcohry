package io.github.phiseecodyhsp.demo;

public class Chart {
    public final String music;
    public final String musicPath;
    public final String composer;
    public final String bpm;
    public final Difficulty difficulty;
    public final String level;
    public final String illustrationPath;
    public final String illustrator;
    public final String noteDesigner;

    public Chart(String music,
                 String musicPath,
                 String composer,
                 double minBPM,
                 double maxBPM,
                 Difficulty difficulty,
                 double rating,
                 String illustrationPath,
                 String illustrator,
                 String noteDesigner) {
        this.music = music;
        this.musicPath = musicPath;
        this.composer = composer;
        this.difficulty = difficulty;
        this.illustrationPath = illustrationPath;
        this.illustrator = illustrator;
        this.noteDesigner = noteDesigner;

        if (minBPM == maxBPM) {
            bpm = String.valueOf((int) minBPM);
        } else {
            bpm = (int) minBPM + "-" + (int) maxBPM;
        }

        if (rating - (int) rating < 0.7) {
            level = String.valueOf((int) rating);
        } else {
            level = (int) rating + "+";
        }
    }

    public enum Difficulty {
        PST("Past"),
        PRS("Present"),
        FTR("Future"),
        BYD("Beyond"),
        ETR("Eternal");

        public final String name;

        Difficulty(String name) {
            this.name = name;
        }
    }
}
