package io.github.phiseecodyhsp.arcstory.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;

/**
 * 由 JSON 数据驱动的 Arcaea 搭档.
 *
 * @param name 搭档名称
 * @param avatarLocation 搭档头像路径
 * @param illustrationLocation 搭档立绘路径
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record Partner(@JsonProperty String name, @JsonProperty ResourceLocation avatarLocation, @JsonProperty ResourceLocation illustrationLocation) {
}
