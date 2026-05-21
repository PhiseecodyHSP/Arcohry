package io.github.phiseecodyhsp.arcstory.storage;

import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;

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

    public static final AudioClip START_SOUND;
    public static final AudioClip TRANSANIMA_STRAT_SOUND;
    public static final AudioClip TRANSANIMA_END_SOUND;

    static {
        GeosansLight_FONT = "GeosansLight.ttf";

        NEW_ICON = of("images/Songs_init.jpg");
        Tutorial_ILLUSTRTION = of("images/Songs_tutorial.jpg");
        Beyond_BACKGROUND = of("images/Background_beyond.jpg");
        Init_ILLUSTRATION = of("images/Songs_init.jpg");
        DOROC_AVATAR = of("images/143px-Partner_doroc_awaken_icon.png");
        SUCV_BG0 = of("images/未标题-1.png");
        SUCV_BG1 = of("images/未标题-1.png");
        NORMAL_TRANSANIMA_L = of("images/TransAnimaL_resized.png");
        NORMAL_TRANSANIMA_R = of("images/TransAnimaR_resized.png");

        START_SOUND = new AudioClip(of("audios/START.mp3"));
        TRANSANIMA_STRAT_SOUND = new AudioClip(of("audios/转场开始.mp3"));
        TRANSANIMA_END_SOUND = new AudioClip(of("audios/转场结束.mp3"));
    }

    private static String of(String path) {
        return String.valueOf(Resources.class.getResource("/io/github/phiseecodyhsp/arcstory/" + path));
    }

    public static Font getFont(String fontPath, double px) {
        return Font.loadFont(Resources.class.getResourceAsStream(
                "/io/github/phiseecodyhsp/arcstory/fonts/" + fontPath), px / 0.75);
    }
}
