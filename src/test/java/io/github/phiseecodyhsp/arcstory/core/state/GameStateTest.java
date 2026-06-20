package io.github.phiseecodyhsp.arcstory.core.state;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {

    private GameState state;

    @BeforeEach
    void setUp() {
        state = new GameState();
    }

    @Test
    void newState_hasEmptyCollections() {
        assertTrue(state.getUnlockedStories().isEmpty());
        assertTrue(state.getReadStories().isEmpty());
        assertTrue(state.getClearedCharts().isEmpty());
    }

    @Test
    void isStoryUnlocked_returnsFalseForNew() {
        assertFalse(state.isStoryUnlocked("story_001"));
    }

    @Test
    void unlockStory_makesStoryUnlocked() {
        state.unlockStory("story_001");
        assertTrue(state.isStoryUnlocked("story_001"));
    }

    @Test
    void markStoryRead_marksAsRead() {
        state.markStoryRead("story_001");
        assertTrue(state.isStoryRead("story_001"));
    }

    @Test
    void clearChart_marksAsCleared() {
        state.clearChart("Tutorial_PST");
        assertTrue(state.isChartCleared("Tutorial_PST"));
        assertFalse(state.isChartCleared("Unknown"));
    }

    @Test
    void setterAndGetter_work() {
        state.getUnlockedStories().add("s1");
        state.getReadStories().add("s1");
        state.getClearedCharts().add("c1");

        assertEquals(1, state.getUnlockedStories().size());
        assertEquals(1, state.getReadStories().size());
        assertEquals(1, state.getClearedCharts().size());
    }
}
