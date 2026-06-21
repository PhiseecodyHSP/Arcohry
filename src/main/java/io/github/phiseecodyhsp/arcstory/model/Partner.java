package io.github.phiseecodyhsp.arcstory.model;

import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * Arcaea 搭档.
 *
 * @param name 搭档名称
 * @param avatarLocation 搭档头像路径
 * @param illustrationLocation 搭档立绘路径
 */
public record Partner(String name, @NotNull ResourceLocation avatarLocation, @NotNull ResourceLocation illustrationLocation) {
}
