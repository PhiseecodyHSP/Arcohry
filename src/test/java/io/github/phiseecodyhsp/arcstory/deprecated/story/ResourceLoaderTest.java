package io.github.phiseecodyhsp.arcstory.deprecated.story;

import io.github.phiseecodyhsp.arcstory.res.ResourceLoader;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class ResourceLoaderTest {

    @Test
    void resolvePath_shouldReturnValidImagePath() {
        String path = ResourceLoader.resolvePath("images", "hikari_avatar");
        assertNotNull(path);
        assertEquals("images/Partner_0_icon.png", path);
    }

    @Test
    void resolvePath_shouldReturnValidAudioPath() {
        String path = ResourceLoader.resolvePath("audio", "story_bgm");
        assertNotNull(path);
        assertEquals("audios/故事模式BGM.wav", path);
    }

    @Test
    void resolvePath_shouldReturnNullForInvalidKey() {
        String path = ResourceLoader.resolvePath("images", "nonexistent_key");
        assertNull(path);
    }

    @Test
    void resolvePath_shouldReturnNullForInvalidCategory() {
        String path = ResourceLoader.resolvePath("nonexistent", "any_key");
        assertNull(path);
    }

    @Test
    void getResourceUrl_shouldResolveExistingResource() {
        var url = ResourceLoader.getResourceUrl("images/0-3.jpg");
        assertNotNull(url);
    }

    @Test
    void getResourceUrl_shouldReturnNullForMissingResource() {
        var url = ResourceLoader.getResourceUrl("images/nonexistent.png");
        assertNull(url);
    }

    @Test
    void loadStream_shouldLoadExistingResource() throws Exception {
        try (InputStream is = ResourceLoader.loadStream("stories/test.json")) {
            assertNotNull(is);
            byte[] bytes = is.readAllBytes();
            assertTrue(bytes.length > 0);
        }
    }

    @Test
    void loadStream_shouldThrowForMissingResource() {
        assertThrows(IllegalArgumentException.class,
                () -> ResourceLoader.loadStream("stories/nonexistent.json"));
    }

    @Test
    void loadUrl_shouldReturnNonNullForExistingResource() {
        String url = ResourceLoader.loadUrl("images/0-3.jpg");
        assertNotNull(url);
        assertTrue(url.startsWith("file:") || url.startsWith("jar:"));
    }

    @Test
    void loadUrl_shouldReturnNullForMissingResource() {
        String url = ResourceLoader.loadUrl("images/nonexistent.png");
        assertNull(url);
    }

    @Test
    void clearCaches_shouldNotThrow() {
        assertDoesNotThrow(ResourceLoader::clearCaches);
    }
}
