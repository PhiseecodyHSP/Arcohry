package io.github.phiseecodyhsp.arcstory.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChartTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    @DisplayName("Tutorial [PST] 谱面的正常定义与读取")
    void defaultConstructor_tutorial() throws JsonProcessingException {
        String json =
                """
                    {
                        "music": "Tutorial",
                        "music_location": "audios/story_bgm",
                        "composer": "ak+q",
                        "left_bpm": 128,
                        "right_bpm": 128,
                        "difficulty": "pst",
                        "rating": 1,
                        "illustration_location": "images/tutorial_illustration",
                        "illustrator": "Unknown",
                        "note_designer": "Unknown",
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
        assertEquals(Paradigm.LIGHT, chart.paradigm());
    }
}