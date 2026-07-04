package io.github.phiseecodyhsp.arcstory.deprecated.state;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class SaveManagerTest {

    @BeforeEach
    void setUp() throws Exception {
        Files.deleteIfExists(SaveManager.getSavePath());
    }

    @AfterEach
    void tearDown() throws Exception {
        Files.deleteIfExists(SaveManager.getSavePath());
    }

    @Test
    void load_returnsNewStateWhenNoSave() throws Exception {
        GameState state = SaveManager.load();
        assertNotNull(state);
        assertTrue(state.getUnlockedStories().isEmpty());
    }

    @Test
    void saveAndLoad_roundTrip() throws Exception {
        GameState original = new GameState();
        original.unlockStory("story_001");
        original.markStoryRead("story_001");
        original.clearChart("Tutorial_PST");

        SaveManager.save(original);

        GameState loaded = SaveManager.load();
        assertTrue(loaded.isStoryUnlocked("story_001"));
        assertTrue(loaded.isStoryRead("story_001"));
        assertTrue(loaded.isChartCleared("Tutorial_PST"));
    }

    @Test
    void getSavePath_returnsValidPath() {
        assertNotNull(SaveManager.getSavePath());
        assertTrue(SaveManager.getSavePath().toString().contains(".arcstory"));
    }
}
