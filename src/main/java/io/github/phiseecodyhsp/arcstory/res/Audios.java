package io.github.phiseecodyhsp.arcstory.res;

import javafx.scene.media.AudioClip;

/**
 * 播放短音频音效的工具类.
 *
 * @author RikkaKawaii0612
 */
public class Audios {

    public static void playSfx(ResourceLocation location) {
        AudioClip audioClip = ResourceLoader.loadAudio(location);
        if (audioClip != null) {
            audioClip.play();
        }
    }
}
