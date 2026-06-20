package io.github.phiseecodyhsp.arcstory.core.story;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.phiseecodyhsp.arcstory.res.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;

public final class StoryLoader {

    private static final ObjectMapper mapper = new ObjectMapper();

    private StoryLoader() {}

    public static Story load(String storyPath) throws IOException {
        try (InputStream is = ResourceLoader.loadStream(storyPath)) {
            Story story = mapper.readValue(is, Story.class);
            validate(story);
            return story;
        }
    }

    public static Story loadFromString(String json) throws IOException {
        Story story = mapper.readValue(json, Story.class);
        validate(story);
        return story;
    }

    private static void validate(Story story) {
        if (story.getId() == null || story.getId().isBlank()) {
            throw new IllegalArgumentException("Story id must not be null or blank");
        }
        if (story.getScenes() == null || story.getScenes().isEmpty()) {
            throw new IllegalArgumentException("Story must have at least one scene");
        }
        for (Scene scene : story.getScenes()) {
            if (scene.getType() == null) {
                throw new IllegalArgumentException("Scene in story '" + story.getId() + "' has null type");
            }
        }
    }
}
