package io.github.phiseecodyhsp.demo;

import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class TransitionAnimation extends StackPane {
    private static final double TT_TIME = 0.25;

    private final TranslateTransition onLAdded;
    private final TranslateTransition onRAdded;
    private final TranslateTransition onLRemoved;
    private final TranslateTransition onRRemoved;

    public TransitionAnimation(Type type) {
        ImageView l;
        ImageView r;
        switch (type) {
            case NORMAL -> {
                l = new ImageView(Resources.TrAnL);
                r = new ImageView(Resources.TrAnR);
            }
            default -> throw new IllegalArgumentException("A TransitionAnimation must be typed when newed");
        }

        onLAdded = new TranslateTransition(Duration.seconds(TT_TIME), l);
        onRAdded = new TranslateTransition(Duration.seconds(TT_TIME), r);
        onLRemoved = new TranslateTransition(Duration.seconds(TT_TIME), l);
        onRRemoved = new TranslateTransition(Duration.seconds(TT_TIME), r);

        getChildren().addAll(l, r);
    }

    public void play(Root root) {
        root.getChildren().add(this);
        onLAdded.setOnFinished(_ -> {
            onLAdded.stop();
            onRAdded.stop();
            onLRemoved.playFromStart();
            onRRemoved.playFromStart();
        });
        onLRemoved.setOnFinished(_ -> root.getChildren().remove(this));
        onLRemoved.stop();
        onRRemoved.stop();
        onLAdded.playFromStart();
        onRAdded.playFromStart();
    }

    public enum Type {
        NORMAL,
        GRIEVOUS,
        FRACTURE,
        FINAL,
        ARGHENA,
        ALTER,
        DESIGNANT
    }
}
