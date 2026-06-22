package io.github.phiseecodyhsp.arcstory.res;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.regex.Pattern;

/**
 * 资源路径. 可用于 {@link ResourceLoader}, 代表特定的一个资源文件.
 *
 * <p>分类和键必须遵循 <b>lower_snake_case</b>, 否则抛异常.
 *
 * @param category 资源分类
 * @param key 资源键
 *
 * @see ResourceLoader
 *
 * @author RikkaKawaii0612
 */
public record ResourceLocation(@JsonProperty String category, @JsonProperty String key) {

    private static final Pattern LOWER_SNAKE_CASE = Pattern.compile("^[a-z0-9]+(_[a-z0-9]+)*$");

    public ResourceLocation {
        if (!LOWER_SNAKE_CASE.matcher(category).matches()) {
            throw new IllegalArgumentException("Category must match lower_snake_case");
        }
        if (!LOWER_SNAKE_CASE.matcher(key).matches()) {
            throw new IllegalArgumentException("Key must match lower_snake_case");
        }
    }

    public static ResourceLocation image(String key) {
        return new ResourceLocation("images", key);
    }

    public static ResourceLocation audio(String key) {
        return new ResourceLocation("audios", key);
    }

    public static ResourceLocation font(String key) {
        return new ResourceLocation("fonts", key);
    }

    public static ResourceLocation story(String key) {
        return new ResourceLocation("stories", key);
    }
}
