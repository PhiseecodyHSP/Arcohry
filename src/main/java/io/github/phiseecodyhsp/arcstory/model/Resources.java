package io.github.phiseecodyhsp.arcstory.model;

import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;

import java.io.InputStream;

// TODO: 把这些东西全部分离, 并且统一命名规范 (包括资源文件夹内的)
// (之前的) TODO: 播放音频、lock、star、NEW、和分侧素材
public final class Resources {
    private Resources() {}

    public static final String GeosansLight_FONT;

    public static final String NEW_ICON;
    public static final String STAR;
    public static final String Tutorial_ILLUSTRTION;
    public static final String Beyond_BACKGROUND;
    public static final String CHAPTER5_SCENERY;
    public static final String Init_ILLUSTRATION;
    public static final String Hikari_AVATAR;
    public static final String Hikari_ILLUSTRATION;
    public static final String DOROC_AVATAR;
    public static final String Tairitsu_AWAKEN_AVATAR;
    public static final String SUC_BG0;
    public static final String SUC_BG1;
    public static final String NORMAL_LOADING_L;
    public static final String NORMAL_LOADING_R;
    public static final String LOADING_SHADOW;
    public static final String LIGHT;
    public static final String CONFLICT;
    public static final String ACHROMIC;
    public static final String LEPHON;

    public static final String STORY_EMPTY;
    public static final String STORY_TEST;

    public static final String START_SOUND;
    public static final String LOADING_START_SOUND;
    public static final String LOADING_END_SOUND;
    public static final String STORY_MODE_BGM;

    static {
        GeosansLight_FONT = "fonts/GeosansLight.ttf";

        NEW_ICON = ofString("images/Icon_scenery_Beyond.png");
        STAR = ofString("images/Icon_scenery_Beyond.png");
        Tutorial_ILLUSTRTION = ofString("images/Songs_tutorial.jpg");
        Beyond_BACKGROUND = ofString("images/Background_beyond.jpg");
        CHAPTER5_SCENERY = ofString("images/Background_scenery_chap5_16-9.jpg");
        Init_ILLUSTRATION = ofString("images/Songs_init.jpg");
        Hikari_AVATAR = ofString("images/Partner_0_icon.png");
        Hikari_ILLUSTRATION = ofString("images/Partner_0_new.png");
        DOROC_AVATAR = ofString("images/143px-Partner_doroc_awaken_icon.png");
        Tairitsu_AWAKEN_AVATAR = ofString("images/Partner_1_awaken_icon.png");
        SUC_BG0 = ofString("images/未标题-1.png");
        SUC_BG1 = ofString("images/未标题-1.png");
        NORMAL_LOADING_L = ofString("images/TransAnimaL_resized.png");
        NORMAL_LOADING_R = ofString("images/TransAnimaR_resized.png");
        LOADING_SHADOW = ofString("images/TransAnimaL_resized.png");
        LIGHT = ofString("images/0-3.jpg");
        CONFLICT = ofString("images/0-3.jpg");
        ACHROMIC = ofString("images/0-3.jpg");
        LEPHON = ofString("images/0-3.jpg");

        STORY_EMPTY = "stories/empty.json";
        STORY_TEST = "stories/test.json";

        START_SOUND = ofString("audios/START.mp3");

        //TODO: 这两个音效无法播放
        LOADING_START_SOUND = ofString("audios/LoadingStart.mp3");
        LOADING_END_SOUND = ofString("audios/LoadingEnd.mp3");

        STORY_MODE_BGM = ofString("audios/故事模式BGM.wav");
    }

    public static String ofString(String path) {
        return String.valueOf(Resources.class.getResource("/io/github/phiseecodyhsp/arcstory/" + path));
    }

    public static InputStream ofStream(String path) {
        InputStream is = Resources.class.getResourceAsStream("/io/github/phiseecodyhsp/arcstory/" + path);
        if (is != null) {
            return is;
        }
        throw new IllegalArgumentException("Path '" + path + "' is invalid");
    }

    public static void playSound(String path) {
        new AudioClip(path).play();
    }

    public static Font getFont(String path, double px) {
        return Font.loadFont(ofStream(path), px);
    }
}
