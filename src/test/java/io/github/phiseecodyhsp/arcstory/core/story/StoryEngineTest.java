package io.github.phiseecodyhsp.arcstory.core.story;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class StoryEngineTest {

    private Story story;
    private StoryEngine engine;

    @BeforeEach
    void setUp() {
        List<Scene> scenes = new ArrayList<>();
        scenes.add(createScene("s1", SceneType.CG));
        scenes.add(createScene("s2", SceneType.DIALOGUE));
        scenes.add(createScene("s3", SceneType.END));

        story = new Story();
        story.setId("test");
        story.setTitle("Test Story");
        story.setScenes(scenes);

        engine = new StoryEngine(story);
    }

    private Scene createScene(String id, SceneType type) {
        Scene s = new Scene();
        s.setId(id);
        s.setType(type);
        return s;
    }

    @Test
    void getCurrentScene_returnsFirstSceneInitially() {
        assertEquals("s1", engine.getCurrentScene().getId());
    }

    @Test
    void hasNextScene_returnsTrueWhenMoreScenes() {
        assertTrue(engine.hasNextScene());
    }

    @Test
    void nextScene_progressesToNext() {
        Scene next = engine.nextScene();
        assertEquals("s2", next.getId());
        assertEquals("s2", engine.getCurrentScene().getId());
    }

    @Test
    void nextScene_returnsNullAtEnd_andFiresCallback() {
        engine.nextScene(); // to s2
        engine.nextScene(); // to s3

        AtomicInteger endCount = new AtomicInteger(0);
        engine.setOnStoryEnd(endCount::incrementAndGet);

        Scene result = engine.nextScene(); // no more
        assertNull(result);
        assertEquals(1, endCount.get());
    }

    @Test
    void hasNextScene_returnsFalseAtEnd() {
        engine.nextScene();
        engine.nextScene();
        assertFalse(engine.hasNextScene());
    }

    @Test
    void jumpTo_navigatesToSceneById() {
        Scene jumped = engine.jumpTo("s3");
        assertEquals("s3", jumped.getId());
    }

    @Test
    void jumpTo_throwsForInvalidId() {
        assertThrows(IllegalArgumentException.class, () -> engine.jumpTo("nonexistent"));
    }

    @Test
    void onSceneChanged_firesOnNextScene() {
        List<String> history = new ArrayList<>();
        engine.setOnSceneChanged(s -> history.add(s.getId()));

        engine.nextScene();
        engine.nextScene();

        assertEquals(List.of("s2", "s3"), history);
    }

    @Test
    void onSceneChanged_firesOnJumpTo() {
        List<String> history = new ArrayList<>();
        engine.setOnSceneChanged(s -> history.add(s.getId()));

        engine.jumpTo("s3");

        assertEquals(List.of("s3"), history);
    }

    @Test
    void makeChoice_navigatesToTargetScene() {
        Scene choiceScene = new Scene();
        choiceScene.setId("choice");
        choiceScene.setType(SceneType.CHOICE);
        List<ChoiceOption> options = List.of(
                new ChoiceOption("Opt A", "s2"),
                new ChoiceOption("Opt B", "s3")
        );
        choiceScene.setOptions(options);

        List<Scene> scenes = new ArrayList<>();
        scenes.add(choiceScene);
        scenes.add(createScene("s2", SceneType.DIALOGUE));
        scenes.add(createScene("s3", SceneType.END));

        Story choiceStory = new Story();
        choiceStory.setId("choiceStory");
        choiceStory.setScenes(scenes);

        StoryEngine choiceEngine = new StoryEngine(choiceStory);
        choiceEngine.makeChoice(1);

        assertEquals("s3", choiceEngine.getCurrentScene().getId());
    }

    @Test
    void makeChoice_ignoresInvalidIndex() {
        Scene choiceScene = new Scene();
        choiceScene.setId("choice");
        choiceScene.setType(SceneType.CHOICE);
        choiceScene.setOptions(List.of(new ChoiceOption("A", "s2")));

        List<Scene> scenes = List.of(choiceScene, createScene("s2", SceneType.END));
        Story s = new Story();
        s.setId("cs");
        s.setScenes(scenes);

        StoryEngine ce = new StoryEngine(s);
        ce.makeChoice(99); // invalid index

        assertEquals("choice", ce.getCurrentScene().getId());
    }
}
