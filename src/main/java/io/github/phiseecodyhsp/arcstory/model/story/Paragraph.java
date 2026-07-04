package io.github.phiseecodyhsp.arcstory.model.story;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;

/**
 * 故事的一个小段落, 可以是文本类或 CG 类, 指向一个文本或图片的资源路径.
 *
 * @param type 段落类型, 文本或 CG
 * @param location 该段落使用的资源路径
 *
 * @author RikkaKawaii0612
 */
public record Paragraph(@JsonProperty ParagraphType type, @JsonProperty ResourceLocation location) {
}
