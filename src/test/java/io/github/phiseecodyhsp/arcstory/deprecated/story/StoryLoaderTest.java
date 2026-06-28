package io.github.phiseecodyhsp.arcstory.deprecated.story;

import io.github.phiseecodyhsp.arcstory.deprecated.condition.UnlockCondition;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StoryLoaderTest {

    @Test
    void loadFromString_shouldParseValidJson() throws Exception {
        String json = """
                {
                  "id": "test_001",
                  "title": "Test Story",
                  "version": "1.0",
                  "unlock": { "type": "chart", "chart": "Tutorial_PST" },
                  "hasCG": true,
                  "scenes": [
                    { "id": "s1", "type": "cg", "image": "images/0-3.jpg", "reveal": "sweep" },
                    { "id": "s2", "type": "dialogue", "text": "Hello|你好|こんにちは" },
                    { "id": "s3", "type": "end", "returnTo": "menu" }
                  ]
                }""";

        Story story = StoryLoader.loadFromString(json);

        assertEquals("test_001", story.getId());
        assertEquals("Test Story", story.getTitle());
        assertEquals("1.0", story.getVersion());
        assertTrue(story.isHasCG());
        assertEquals(3, story.getScenes().size());

        Scene first = story.getScenes().get(0);
        assertEquals("s1", first.getId());
        assertEquals(SceneType.CG, first.getType());
        assertEquals("images/0-3.jpg", first.getImage());
        assertEquals("sweep", first.getReveal());

        UnlockCondition unlock = story.getUnlock();
        assertEquals("chart", unlock.getType());
        assertEquals("Tutorial_PST", unlock.getChart());
    }

    @Test
    void loadFromString_shouldParseDialogueScene() throws Exception {
        String json = """
                {
                  "id": "test_002",
                  "title": "Dialogue Test",
                  "version": "1.0",
                  "unlock": { "type": "none" },
                  "hasCG": false,
                  "scenes": [
                    { "id": "d1", "type": "dialogue", "speaker": "Hikari", "text": "Hello world", "avatar": "images/avatar.png" }
                  ]
                }""";

        Story story = StoryLoader.loadFromString(json);

        Scene scene = story.getScenes().get(0);
        assertEquals(SceneType.DIALOGUE, scene.getType());
        assertEquals("Hikari", scene.getSpeaker());
        assertEquals("Hello world", scene.getText());
        assertEquals("images/avatar.png", scene.getAvatar());
    }

    @Test
    void loadFromString_shouldParseChoiceScene() throws Exception {
        String json = """
                {
                  "id": "test_003",
                  "title": "Choice Test",
                  "version": "1.0",
                  "unlock": { "type": "none" },
                  "hasCG": false,
                  "scenes": [
                    { "id": "c1", "type": "choice", "prompt": "Choose one", "options": [
                      { "text": "Option A", "nextScene": "s_a" },
                      { "text": "Option B", "nextScene": "s_b" }
                    ]}
                  ]
                }""";

        Story story = StoryLoader.loadFromString(json);

        Scene scene = story.getScenes().get(0);
        assertEquals(SceneType.CHOICE, scene.getType());
        assertEquals("Choose one", scene.getPrompt());
        assertEquals(2, scene.getOptions().size());
        assertEquals("Option A", scene.getOptions().get(0).getText());
        assertEquals("s_a", scene.getOptions().get(0).getNextScene());
    }

    @Test
    void loadFromString_shouldFailOnMissingId() {
        String json = """
                {
                  "title": "No ID",
                  "scenes": [ { "id": "s1", "type": "end" } ]
                }""";
        assertThrows(Exception.class, () -> StoryLoader.loadFromString(json));
    }

    @Test
    void loadFromString_shouldFailOnNoScenes() {
        String json = """
                {
                  "id": "no_scenes",
                  "title": "No Scenes",
                  "scenes": []
                }""";
        assertThrows(Exception.class, () -> StoryLoader.loadFromString(json));
    }

    @Test
    void loadFromString_shouldFailOnNullSceneType() {
        String json = """
                {
                  "id": "null_type",
                  "title": "Null Type",
                  "scenes": [ { "id": "s1" } ]
                }""";
        assertThrows(Exception.class, () -> StoryLoader.loadFromString(json));
    }

    @Test
    void unlockCondition_isNone_returnsTrueForNull() {
        UnlockCondition c = new UnlockCondition();
        assertTrue(c.isNone());
    }

    @Test
    void unlockCondition_isNone_returnsTrueForNone() {
        UnlockCondition c = new UnlockCondition("none", null, null);
        assertTrue(c.isNone());
    }

    @Test
    void unlockCondition_isNone_returnsFalseForChart() {
        UnlockCondition c = new UnlockCondition("chart", "Tutorial_PST", null);
        assertFalse(c.isNone());
    }
}
