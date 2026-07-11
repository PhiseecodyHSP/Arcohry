package io.github.phiseecodyhsp.arcstory.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;

/**
 * 由 JSON 数据驱动的存储故事解锁条件数据的类.
 *
 * @author HSP
 *
 * @param chartLocation 谱面路径
 * @param partnerLocation 搭档路径
 */
public record StoryUnlockCondition(@JsonProperty ResourceLocation chartLocation, @JsonProperty ResourceLocation partnerLocation) {
}
