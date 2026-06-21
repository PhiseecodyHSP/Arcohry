package io.github.phiseecodyhsp.arcstory.res;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;

import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ResourceLoader {

    private static final String BASE = "/io/github/phiseecodyhsp/arcstory/";
    private static final String CONFIG_PATH = "resource-config.json";

    private static final ObjectMapper mapper = new ObjectMapper();
    private static volatile JsonNode config;

    private static final Map<String, Image> imageCache = new ConcurrentHashMap<>();
    private static final Map<String, Font> fontCache = new ConcurrentHashMap<>();
    private static final Map<String, AudioClip> audioCache = new ConcurrentHashMap<>();

    private ResourceLoader() {}

    private static JsonNode config() {
        if (config == null) {
            synchronized (ResourceLoader.class) {
                if (config == null) {
                    try (InputStream is = ResourceLoader.class.getResourceAsStream(BASE + CONFIG_PATH)) {
                        if (is != null) {
                            config = mapper.readTree(is);
                        } else {
                            config = mapper.createObjectNode();
                        }
                    } catch (Exception e) {
                        config = mapper.createObjectNode();
                    }
                }
            }
        }
        return config;
    }

    public static String resolvePath(String category, String key) {
        JsonNode node = config().path(category).path(key);
        if (node.isTextual()) {
            return node.asText();
        }
        return null;
    }

    public static URL getResourceUrl(String relativePath) {
        return ResourceLoader.class.getResource(BASE + relativePath);
    }

    public static String loadUrl(String relativePath) {
        URL url = getResourceUrl(relativePath);
        return url != null ? url.toExternalForm() : null;
    }

    public static InputStream loadStream(String relativePath) {
        InputStream is = ResourceLoader.class.getResourceAsStream(BASE + relativePath);
        if (is == null) {
            throw new IllegalArgumentException("Resource not found: " + relativePath);
        }
        return is;
    }

    public static Font loadFont(String relativePath, double size) {
        String cacheKey = relativePath + "@" + size;
        return fontCache.computeIfAbsent(cacheKey, k -> {
            InputStream is = loadStream(relativePath);
            return Font.loadFont(is, size);
        });
    }

    public static Image loadImage(String relativePath) {
        return imageCache.computeIfAbsent(relativePath, k -> {
            String url = loadUrl(relativePath);
            if (url == null) {
                throw new IllegalArgumentException("Image not found: " + relativePath);
            }
            return new Image(url);
        });
    }

    public static AudioClip loadAudio(String relativePath) {
        return audioCache.computeIfAbsent(relativePath, k -> {
            String url = loadUrl(relativePath);
            if (url == null) {
                throw new IllegalArgumentException("Audio not found: " + relativePath);
            }
            return new AudioClip(url);
        });
    }

    public static void playSound(String relativePath) {
        loadAudio(relativePath).play();
    }

    public static void clearCaches() {
        imageCache.clear();
        fontCache.clear();
        audioCache.clear();
    }
}
