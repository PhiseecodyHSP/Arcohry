package io.github.phiseecodyhsp.arcstory.storage;

import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;

import java.io.InputStream;

//TODO: 播放音频、lock、star和NEW素材
public final class Resources {
    private Resources() {}

    public static final String GeosansLight_FONT;

    public static final String NEW_ICON;
    public static final String Tutorial_ILLUSTRTION;
    public static final String Beyond_BACKGROUND;
    public static final String Init_ILLUSTRATION;
    public static final String DOROC_AVATAR;
    public static final String SUCV_BG0;
    public static final String SUCV_BG1;
    public static final String NORMAL_TRANSANIMA_L;
    public static final String NORMAL_TRANSANIMA_R;

    public static final String STORY1_1;

    public static final AudioClip START_SOUND;
    public static final AudioClip TRANSANIMA_STRAT_SOUND;
    public static final AudioClip TRANSANIMA_END_SOUND;

    static {
        GeosansLight_FONT = "fonts/GeosansLight.ttf";

        NEW_ICON = ofString("images/Songs_init.jpg");
        Tutorial_ILLUSTRTION = ofString("images/Songs_tutorial.jpg");
        Beyond_BACKGROUND = ofString("images/Background_beyond.jpg");
        Init_ILLUSTRATION = ofString("images/Songs_init.jpg");
        DOROC_AVATAR = ofString("images/143px-Partner_doroc_awaken_icon.png");
        SUCV_BG0 = ofString("images/未标题-1.png");
        SUCV_BG1 = ofString("images/未标题-1.png");
        NORMAL_TRANSANIMA_L = ofString("images/TransAnimaL_resized.png");
        NORMAL_TRANSANIMA_R = ofString("images/TransAnimaR_resized.png");

        STORY1_1 = "stories/test.json";

        START_SOUND = ofClip("audios/START.mp3");
        TRANSANIMA_STRAT_SOUND = ofClip("audios/转场开始.mp3");
        TRANSANIMA_END_SOUND = ofClip("audios/转场结束.mp3");
    }

    private static String ofString(String path) {
        return String.valueOf(Resources.class.getResource("/io/github/phiseecodyhsp/arcstory/" + path));
    }

    private static InputStream ofStream(String path) {
        return Resources.class.getResourceAsStream("/io/github/phiseecodyhsp/arcstory/" + path);
    }

    private static AudioClip ofClip(String path) {
        return new AudioClip(ofString(path));
    }

    public static Font getFont(String path, double px) {
        return Font.loadFont(ofStream(path), px / 0.75);
    }
}
