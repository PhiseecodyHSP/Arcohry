package io.github.phiseecodyhsp.arcstory.storage;

import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;

import java.io.InputStream;

//TODO: 播放音频、lock、star和NEW素材
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
    public static final String TRANSANIMA_SHADOW;
    public static final String COVER;

    public static final String STORY1_ZR;
    public static final String STORYCG;
    public static final String STORYA1;
    public static final String STORYA2;
    public static final String STORYA3;
    public static final String STORYA4;
    public static final String STORYA5;
    public static final String STORYA6;
    public static final String STORYAE;

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
        TRANSANIMA_SHADOW = ofString("images/TransAnimaL_resized.png");
        COVER = ofString("images/cover.png");

        STORY1_ZR = "stories/1-ZR.json";
        STORYCG = "stories/cg.json";
        STORYA1 = "stories/A1.json";
        STORYA2 = "stories/A2.json";
        STORYA3 = "stories/A3.json";
        STORYA4 = "stories/A4.json";
        STORYA5 = "stories/A5.json";
        STORYA6 = "stories/A6.json";
        STORYAE = "stories/AE.json";

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
