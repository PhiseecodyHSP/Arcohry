package io.github.phiseecodyhsp.arcstory.model;

import org.jetbrains.annotations.NotNull;

/**
 * Arcaea 搭档.
 *
 * @param name 搭档名称
 * @param avatarPath 搭档头像路径
 * @param illustrationPath 搭档立绘路径
 */
public record Partner(String name, @NotNull String avatarPath, @NotNull String illustrationPath) {
}
