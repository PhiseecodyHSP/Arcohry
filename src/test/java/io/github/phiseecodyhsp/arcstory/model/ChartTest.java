package io.github.phiseecodyhsp.arcstory.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChartTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void readJson() throws JsonProcessingException {
        String json =
                """
                    {
                        "music": "Tutorial",
                        "musicLocation": "audios/story_bgm",
                        "composer": "ak+q",
                        "leftBpm": 128,
                        "rightBpm": 128,
                        "difficulty": "pst",
                        "rating": 1,
                        "illustrationLocation": "images/tutorial_illustration",
                        "illustrator": "Unknown",
                        "noteDesigner": "Unknown",
                        "paradigm": "light"
                    }
                """;
        Chart chart = MAPPER.readValue(json, Chart.class);

        assertEquals("Tutorial", chart.music());
        assertEquals("audios/story_bgm", chart.musicLocation().getLocation());
        assertEquals("ak+q", chart.composer());
        assertEquals("128", chart.getBpm());
        assertEquals("PST", chart.difficulty().name());
        assertEquals("1", chart.getLevel());
        assertEquals("images/tutorial_illustration", chart.illustrationLocation().getLocation());
        assertEquals("Unknown", chart.illustrator());
        assertEquals("Unknown", chart.noteDesigner());
        assertEquals("LIGHT", chart.paradigm().name());
    }
}