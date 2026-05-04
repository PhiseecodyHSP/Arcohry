package io.github.phiseecodyhsp.demo.storage;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;

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
    public final Paradigms paradigms;

    public Chart(String music,
                 @NotNull String musicPath,
                 String composer,
                 double minBPM,
                 double maxBPM,
                 Difficulty difficulty,
                 double rating,
                 @NotNull String illustrationPath,
                 String illustrator,
                 String noteDesigner,
                 @NotNull Paradigms paradigms) {
        this.music = music;
        this.musicPath = musicPath;
        this.composer = composer;
        this.difficulty = difficulty;
        this.illustrationPath = illustrationPath;
        this.illustrator = illustrator;
        this.noteDesigner = noteDesigner;
        this.paradigms = paradigms;

        if (minBPM == maxBPM) {
            bpm = String.valueOf(minBPM);
        } else {
            bpm = minBPM + "-" + maxBPM;
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

    //TODO: 重取色
    public enum Paradigms {
        LIGHT(Color.WHITE),
        CONFLICT(Color.WHITE);

        private final Color color;
        Paradigms(Color color) {
            this.color = color;
        }

        public void setParadigms(Rectangle square) {
            square.setFill(color);
        }
    }
}
