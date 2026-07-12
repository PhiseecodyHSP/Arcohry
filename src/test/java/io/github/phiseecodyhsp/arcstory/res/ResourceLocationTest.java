package io.github.phiseecodyhsp.arcstory.res;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceLocationTest {

    @Test
    @DisplayName("构造函数传入非 lower_snake_case 参数应抛出 IllegalArgumentException")
    void constructor_checkCase() {
        assertDoesNotThrow(() -> new ResourceLocation("test_category","test_key"));
        assertDoesNotThrow(() -> new ResourceLocation("number_1","number_2"));
        assertDoesNotThrow(() -> new ResourceLocation("123","123"));
        assertThrows(IllegalArgumentException.class, () -> new ResourceLocation("_start", "key"));
        assertThrows(IllegalArgumentException.class, () -> new ResourceLocation("end_", "key"));
        assertThrows(IllegalArgumentException.class, () -> new ResourceLocation("double__underscores", "key"));
        assertThrows(IllegalArgumentException.class, () -> new ResourceLocation("_", "key"));
        assertThrows(IllegalArgumentException.class, () -> new ResourceLocation("category", "_start"));
        assertThrows(IllegalArgumentException.class, () -> new ResourceLocation("category", "end_"));
        assertThrows(IllegalArgumentException.class, () -> new ResourceLocation("category", "double__underscores"));
        assertThrows(IllegalArgumentException.class, () -> new ResourceLocation("category", "_"));
    }

    @Test
    @DisplayName("构造函数传入非 \"null\" 单字符串时应正确拆分 category 和 key")
    void fromString_notNullSingleString() {
        assertDoesNotThrow(() -> {
            ResourceLocation loc = new ResourceLocation("test_category/test_key");
            assertEquals("test_category", loc.category());
            assertEquals("test_key", loc.key());
        });
        assertThrows(IllegalArgumentException.class, () -> new ResourceLocation("abc"));
        assertThrows(IllegalArgumentException.class, () -> new ResourceLocation("category/"));
        assertThrows(IllegalArgumentException.class, () -> new ResourceLocation("/key"));
        assertThrows(IllegalArgumentException.class, () -> new ResourceLocation("double//slashes"));
        assertThrows(IllegalArgumentException.class, () -> new ResourceLocation("/"));
    }

    @Test
    @DisplayName("image 返回 category 为 images 的 ResourceLocation")
    void image_returnsCategoryImage() {
        assertEquals("images", ResourceLocation.image("test").category());
    }

    @Test
    @DisplayName("audio 返回 category 为 audios 的 ResourceLocation")
    void audio_returnsCategoryAudio() {
        assertEquals("audios", ResourceLocation.audio("test").category());
    }

    @Test
    @DisplayName("font 返回 category 为 fonts 的 ResourceLocation")
    void font_returnsCategoryFont() {
        assertEquals("fonts", ResourceLocation.font("test").category());
    }

    @Test
    @DisplayName("story 返回 category 为 stories 的 ResourceLocation")
    void story_returnsCategoryFont() {
        assertEquals("stories", ResourceLocation.story("test").category());
    }

    @Test
    @DisplayName("text 返回 category 为 texts 的 ResourceLocation")
    void text_returnsCategoryFont() {
        assertEquals("texts", ResourceLocation.text("test").category());
    }
}
