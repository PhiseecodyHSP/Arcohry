package io.github.phiseecodyhsp.arcstory.model.story;

import com.fasterxml.jackson.databind.EnumNamingStrategies;
import com.fasterxml.jackson.databind.annotation.EnumNaming;

/**
 * 故事段落的类型, 分为文本类和 CG 类.
 *
 * @see Paragraph
 *
 * @author RikkaKawaii0612
 */
@EnumNaming(value = EnumNamingStrategies.SnakeCaseStrategy.class)
public enum ParagraphType {
    TEXT, CG
}
