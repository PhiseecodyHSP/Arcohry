package io.github.phiseecodyhsp.arcstory.service;

import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;

import java.util.Set;

/**
 * 管理当前存档状态的服务.
 *
 * @author RikkaKawaii0612
 */
public interface GameStateService {

    Set<ResourceLocation> getUnlockedStories();

    Set<ResourceLocation> getReadStories();

    void unlockStory(ResourceLocation story);

    void readStory(ResourceLocation story);

    void save();
}
