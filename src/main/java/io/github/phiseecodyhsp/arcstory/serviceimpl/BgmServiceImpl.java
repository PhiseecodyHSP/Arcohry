package io.github.phiseecodyhsp.arcstory.serviceimpl;

import io.github.phiseecodyhsp.arcstory.res.ResourceLoader;
import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;
import io.github.phiseecodyhsp.arcstory.service.BgmService;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * @author RikkaKawaii0612
 */
public class BgmServiceImpl implements BgmService {

    private MediaPlayer player;
    private ResourceLocation currentBgm;
    private final DoubleProperty volume = new SimpleDoubleProperty(0.2D);
    private Timeline fadeOutTimeline;

    @Override
    public void playBgm(ResourceLocation location) {
        this.stopBgm();
        String path = ResourceLoader.resolvePath(location);
        if (path != null) {
            this.currentBgm = location;

            this.player = new MediaPlayer(new Media(path));
            this.player.volumeProperty().bind(this.volume);
            this.player.setCycleCount(AudioClip.INDEFINITE);
            this.player.play();
        } else {
            this.currentBgm = null;
        }
    }

    @Override
    public void stopBgm() {
        this.fadeOutTimeline = null;
        this.player.stop();
        this.player.dispose();
        this.player = null;
    }

    @Override
    public void fadeOutBgm(double seconds) {
        if (this.currentBgm == null) {
            return;
        }

        this.player.volumeProperty().unbind();
        this.fadeOutTimeline = new Timeline(
                new KeyFrame(Duration.seconds(seconds),
                        new KeyValue(this.player.volumeProperty(), 0.0D)
                )
        );
        this.fadeOutTimeline.setOnFinished(_ -> this.stopBgm());
        this.fadeOutTimeline.play();
    }

    @Override
    public ResourceLocation getCurrentBgm() {
        return this.currentBgm;
    }

    @Override
    public boolean isPlaying() {
        return this.currentBgm != null;
    }

    @Override
    public void setVolume(double volume) {
        this.volume.set(volume);
    }

    @Override
    public double getVolume() {
        return this.player.getVolume();
    }
}
