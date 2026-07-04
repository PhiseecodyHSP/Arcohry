package io.github.phiseecodyhsp.arcstory.res;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

@Deprecated
public class AudioManager {

    private AudioClip bgm;
    private String currentBgmKey;
    private double volume = 0.2;
    private Timeline fadeOutTimeline;

    public void playBgm(String configKey) {
        stopBgm();
        String path = ResourceLoader.resolvePath("audio", configKey);
        if (path != null) {
            currentBgmKey = configKey;
            bgm = ResourceLoader.loadAudio(path);
            bgm.setVolume(volume);
            bgm.setCycleCount(AudioClip.INDEFINITE);
            bgm.play();
        }
    }

    public void stopBgm() {
        cancelFadeOut();
        if (bgm != null) {
            bgm.stop();
            bgm = null;
        }
        currentBgmKey = null;
    }

    public void fadeOutBgm(double seconds) {
        if (bgm == null) return;
        cancelFadeOut();

        double startVolume = volume;
        double steps = Math.max(1, seconds / 0.05);
        double decrement = startVolume / steps;

        fadeOutTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0.05), e -> {
                    if (bgm != null) {
                        double newVol = bgm.getVolume() - decrement;
                        if (newVol <= 0) {
                            stopBgm();
                        } else {
                            bgm.setVolume(newVol);
                        }
                    }
                })
        );
        fadeOutTimeline.setCycleCount((int) steps);
        fadeOutTimeline.setOnFinished(e -> stopBgm());
        fadeOutTimeline.play();
    }

    public void playSfx(String configKey) {
        String path = ResourceLoader.resolvePath("audio", configKey);
        if (path != null) {
            ResourceLoader.loadAudio(path).play();
        }
    }

    public void setVolume(double volume) {
        this.volume = Math.max(0, Math.min(1, volume));
        if (bgm != null) {
            bgm.setVolume(this.volume);
        }
    }

    public double getVolume() {
        return volume;
    }

    public String getCurrentBgmKey() {
        return currentBgmKey;
    }

    public boolean isPlaying() {
        return bgm != null && bgm.isPlaying();
    }

    private void cancelFadeOut() {
        if (fadeOutTimeline != null) {
            fadeOutTimeline.stop();
            fadeOutTimeline = null;
        }
    }
}
