package io.github.phiseecodyhsp.arcstory.res;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

@Deprecated
public class AudioManager {

    private AudioClip bgm;
    private double volume = 0.2D;
    private Timeline fadeOutTimeline;

    public void playBgm(ResourceLocation loc) {
        this.stopBgm();
        this.bgm = ResourceLoader.loadAudio(loc);
        if (this.bgm != null) {
            this.bgm.setVolume(this.volume);
            this.bgm.setCycleCount(AudioClip.INDEFINITE);
            this.bgm.play();
        }
    }

    public void stopBgm() {
        this.cancelFadeOut();
        if (this.bgm != null) {
            this.bgm.stop();
            this.bgm = null;
        }
    }

    public void fadeOutBgm(double seconds) {
        if (this.bgm == null) return;
        cancelFadeOut();

        double startVolume = this.volume;
        double steps = Math.max(1.0D, seconds / 0.05D);
        double decrement = startVolume / steps;

        this.fadeOutTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0.05D), _ -> {
                    if (this.bgm != null) {
                        double newVol = this.bgm.getVolume() - decrement;
                        if (newVol <= 0.0D) {
                            this.stopBgm();
                        } else {
                            this.bgm.setVolume(newVol);
                        }
                    }
                })
        );
        this.fadeOutTimeline.setCycleCount((int) steps);
        this.fadeOutTimeline.setOnFinished(_ -> this.stopBgm());
        this.fadeOutTimeline.play();
    }

    public void playSfx(String configKey) {
        String path = ResourceLoader.resolvePath("audio", configKey);
        if (path != null) {
            ResourceLoader.loadAudio(path).play();
        }
    }

    public void setVolume(double volume) {
        this.volume = Math.max(0.0D, Math.min(1.0D, volume));
        if (this.bgm != null) {
            this.bgm.setVolume(this.volume);
        }
    }

    public double getVolume() {
        return this.volume;
    }

    public boolean isPlaying() {
        return this.bgm != null && this.bgm.isPlaying();
    }

    private void cancelFadeOut() {
        if (this.fadeOutTimeline != null) {
            this.fadeOutTimeline.stop();
            this.fadeOutTimeline = null;
        }
    }
}
