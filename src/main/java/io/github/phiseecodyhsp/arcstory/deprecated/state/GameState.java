package io.github.phiseecodyhsp.arcstory.deprecated.state;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GameState {

    @JsonProperty("unlockedStories")
    private Set<String> unlockedStories = new HashSet<>();

    @JsonProperty("readStories")
    private Set<String> readStories = new HashSet<>();

    @JsonProperty("clearedCharts")
    private Set<String> clearedCharts = new HashSet<>();

    public GameState() {}

    public Set<String> getUnlockedStories() { return unlockedStories; }
    public void setUnlockedStories(Set<String> unlockedStories) { this.unlockedStories = unlockedStories; }

    public Set<String> getReadStories() { return readStories; }
    public void setReadStories(Set<String> readStories) { this.readStories = readStories; }

    public Set<String> getClearedCharts() { return clearedCharts; }
    public void setClearedCharts(Set<String> clearedCharts) { this.clearedCharts = clearedCharts; }

    public boolean isStoryUnlocked(String storyId) {
        return unlockedStories.contains(storyId);
    }

    public boolean isStoryRead(String storyId) {
        return readStories.contains(storyId);
    }

    public boolean isChartCleared(String chartId) {
        return clearedCharts.contains(chartId);
    }

    public void unlockStory(String storyId) {
        unlockedStories.add(storyId);
    }

    public void markStoryRead(String storyId) {
        readStories.add(storyId);
    }

    public void clearChart(String chartId) {
        clearedCharts.add(chartId);
    }
}
