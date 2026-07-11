package io.github.phiseecodyhsp.arcstory.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;

/**
 *
 * @param chartLocation
 * @param partnerLocation
 */
public record StoryUnlockCondition(@JsonProperty ResourceLocation chartLocation, @JsonProperty ResourceLocation partnerLocation) {
}
