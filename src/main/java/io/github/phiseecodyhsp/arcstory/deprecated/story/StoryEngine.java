package io.github.phiseecodyhsp.arcstory.deprecated.story;

import java.util.Map;
import java.util.function.Consumer;

public class StoryEngine {

    private final Story story;
    private int currentSceneIndex;
    private final Map<String, Integer> sceneIndexMap;
    private Consumer<Scene> onSceneChanged;
    private Runnable onStoryEnd;

    public StoryEngine(Story story) {
        this.story = story;
        this.currentSceneIndex = 0;
        this.sceneIndexMap = new java.util.HashMap<>();
        for (int i = 0; i < story.getScenes().size(); i++) {
            String id = story.getScenes().get(i).getId();
            if (id != null) {
                sceneIndexMap.put(id, i);
            }
        }
    }

    public void setOnSceneChanged(Consumer<Scene> onSceneChanged) {
        this.onSceneChanged = onSceneChanged;
    }

    public void setOnStoryEnd(Runnable onStoryEnd) {
        this.onStoryEnd = onStoryEnd;
    }

    public Scene nextScene() {
        if (!hasNextScene()) {
            if (onStoryEnd != null) {
                onStoryEnd.run();
            }
            return null;
        }
        currentSceneIndex++;
        Scene scene = story.getScenes().get(currentSceneIndex);
        if (onSceneChanged != null) {
            onSceneChanged.accept(scene);
        }
        return scene;
    }

    public Scene jumpTo(String sceneId) {
        Integer idx = sceneIndexMap.get(sceneId);
        if (idx == null) {
            throw new IllegalArgumentException("Scene not found: " + sceneId);
        }
        currentSceneIndex = idx;
        Scene scene = story.getScenes().get(currentSceneIndex);
        if (onSceneChanged != null) {
            onSceneChanged.accept(scene);
        }
        return scene;
    }

    public void makeChoice(int choiceIndex) {
        Scene current = story.getScenes().get(currentSceneIndex);
        if (current.getOptions() != null && choiceIndex >= 0 && choiceIndex < current.getOptions().size()) {
            String nextSceneId = current.getOptions().get(choiceIndex).getNextScene();
            jumpTo(nextSceneId);
        }
    }

    public boolean hasNextScene() {
        return currentSceneIndex < story.getScenes().size() - 1;
    }

    public Scene getCurrentScene() {
        return story.getScenes().get(currentSceneIndex);
    }

    public Story getStory() {
        return story;
    }

    public int getCurrentSceneIndex() {
        return currentSceneIndex;
    }
}
