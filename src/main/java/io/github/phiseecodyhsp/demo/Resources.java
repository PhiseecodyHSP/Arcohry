package io.github.phiseecodyhsp.demo;

public final class Resources {
    private Resources() {}

    public static final String Futura_LT_Light_FONT;
    public static final String Noto_Sans_FONT;
    public static final String Tutorial_ILLUSTRTION;
    public static final String Beyond_BACKGROUND;
    public static final String Init_ILLUSTRATION;
    public static final String DOROC_AVATAR;
    public static final String SUCV_BG0;
    public static final String SUCV_BG1;
    public static final String TrAnL;
    public static final String TrAnR;

    public static final String STORYMODE_BGM;

    static {
        Futura_LT_Light_FONT = "Futura LT Light";
        Noto_Sans_FONT = "Noto Sans";
        Tutorial_ILLUSTRTION = of("images/Songs_tutorial.jpg");
        Beyond_BACKGROUND = of("images/Background_beyond.jpg");
        Init_ILLUSTRATION = of("images/Songs_init.jpg");
        DOROC_AVATAR = of("images/143px-Partner_doroc_awaken_icon.png");
        SUCV_BG0 = of("images/未标题-1.png");
        SUCV_BG1 = of("images/未标题-1.png");
        TrAnL = of("images/TransAnimaL_resized.png");
        TrAnR = of("images/TransAnimaR_resized.png");

        STORYMODE_BGM = of("audios/故事菜单音乐.wav");
    }

    private static String of(String path) {
        return String.valueOf(Resources.class.getResource("/io/github/phiseecodyhsp/demo/" + path));
    }
}
