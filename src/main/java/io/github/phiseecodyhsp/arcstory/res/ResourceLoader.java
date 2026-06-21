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

/**
 * 资源管理实用类.
 *
 * <p>该类提供了一些<b>加载 resources 资源文件夹内资源</b>的方法, 便于管理各种<b>图片</b>,
 * <b>音频</b>等资源.
 *
 * <h2>资源映射表</h2>
 *
 * <p><b>资源映射表</b>是一个 <b>JSON 文件</b>, 位于
 * {@code resources/io/github/phiseecodyhsp/arcstory/resource-config.json}.
 * 其内部结构为 <b>"分类 -> 键值对"</b>:
 *
 *  <pre>{@code
 *  {
 *      "images": {
 *          "hikari": "images/hikari.png",
 *          "tairitsu": "images/tairitsu.png"
 *      },
 *      "audios": {
 *          "bgm": "audios/bgm.mp3"
 *      }
 *  }
 *  }</pre>
 *
 *  <p>读取时, {@code images} 和 {@code audios} 为<b>分类</b>, 其中的<b>键值对</b>会被记录.
 *  我们推荐使用 <b>lower_snake_case</b> 来命名分类和键.
 *  键值对的值是一个 {@code resources/io/github/phiseecodyhsp/arcstory/} 下的文件路径.
 *
 *  <p>使用 {@link #resolvePath(String, String)} 可以根据<b>分类和键名</b>来获取映射的资源路径.
 *  在键名确定后, 要更改资源<b>仅需调整资源映射表</b>, 无需修改源代码.
 *
 *  <h2>资源读取与缓存</h2>
 *
 *  <p>{@link ResourceLoader} 提供了一些方法读取并创建<b>资源对象</b>, 如 {@link #loadImage(String)}.
 *  一般搭配 {@link #resolvePath(String, String)} 来使用这些方法:
 *
 *  <pre>{@code
 *  Image image = ResourceLoader.loadImage(ResourceLoader.resolvePath("images", "hikari"));
 *  }</pre>
 *
 *  <p>这些资源对象被创建后, 会<b>被缓存</b>进一个 {@link Map} 对象中, 后续重复读取时会<b>复用该对象</b>.
 *  不同的是, 音频对象 {@link AudioClip} <b>不会</b>被缓存, 因为其是一个有逻辑的对象.
 *
 *  <p>使用 {@link #clearCaches()} 来<b>清空缓存</b>, 通常<b>不会</b>随意调用.
 *
 *  <h2>线程安全</h2>
 *
 *  <p>{@link ResourceLoader} 是<b>线程安全</b>的, 这意味着多个线程<b>可以同时</b>通过此类读取资源,
 *  <b>多线程同时</b>获取某一资源也<b>不会</b>创建多份缓存.
 *
 * @author KashiKoiAstra
 */
public final class ResourceLoader {

    private static final String BASE = "/io/github/phiseecodyhsp/arcstory/";
    private static final String CONFIG_PATH = "resource-config.json";

    private static final ObjectMapper mapper = new ObjectMapper();
    private static volatile JsonNode config;

    private static final Map<String, Image> imageCache = new ConcurrentHashMap<>();
    private static final Map<String, Font> fontCache = new ConcurrentHashMap<>();

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

    /**
     * 从资源映射表中读取资源路径.
     *
     * @param category 资源分类
     * @param key 键
     * @return 资源映射表中对应的路径, 不存在时返回 {@code null}
     */
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
        return fontCache.computeIfAbsent(cacheKey, _ -> {
            InputStream is = loadStream(relativePath);
            return Font.loadFont(is, size);
        });
    }

    public static Image loadImage(String relativePath) {
        return imageCache.computeIfAbsent(relativePath, _ -> {
            String url = loadUrl(relativePath);
            if (url == null) {
                throw new IllegalArgumentException("Image not found: " + relativePath);
            }
            return new Image(url);
        });
    }

    public static AudioClip loadAudio(String relativePath) {
        String url = loadUrl(relativePath);
        if (url == null) {
            throw new IllegalArgumentException("Audio not found: " + relativePath);
        }
        return new AudioClip(url);
    }

    public static void playSound(String relativePath) {
        loadAudio(relativePath).play();
    }

    public static void clearCaches() {
        imageCache.clear();
        fontCache.clear();
    }
}
