package io.github.phiseecodyhsp.arcstory.model;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * 谱面的色侧, 分为光芒侧, 纷争侧, 消色侧和 Lephon 侧 (Lucent Historia 包特有)
 *
 * <p>谱面的色侧会决定转场动画的样式. <b>注意: 转场素材暂时缺失. //TODO</b>
 */
public enum Paradigm {
    LIGHT("images/0-3.jpg"),
    CONFLICT("images/0-3.jpg"),
    ACHROMIC("images/0-3.jpg"),
    LEPHON("images/0-3.jpg");

    private final String path;

    Paradigm(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }

    @JsonCreator
    public static Paradigm fromString(String string) {
        for (Paradigm p : Paradigm.values()) {
            if (p.name().equalsIgnoreCase(string)) {
                return p;
            }
        }
        throw new IllegalArgumentException("Paradigm '" + string + "' does not exist");
    }
}
