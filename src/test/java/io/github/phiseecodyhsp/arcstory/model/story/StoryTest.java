package io.github.phiseecodyhsp.arcstory.model.story;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StoryTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    void defaultConstructor_defaultValues() {
        Story story = new Story();
        assertEquals(Story.DEFAULT_NAME, story.getName());
        assertNotNull(story.getParagraphs());
        assertEquals(ArrayList.class, story.getParagraphs().getClass());
        assertTrue(story.getParagraphs().isEmpty());
    }

    @Test
    void getName_setName_roundTrip() {
        Story story = new Story();
        story.setName("Test Story");
        assertEquals("Test Story", story.getName());
    }

    @Test
    void setParagraphs_getParagraphs_roundTrip() {
        Story story = new Story();
        List<Paragraph> paragraphs = new ArrayList<>();
        paragraphs.add(new Paragraph(ParagraphType.TEXT, ResourceLocation.text("test_text")));
        story.setParagraphs(paragraphs);

        assertEquals(1, story.getParagraphs().size());
        assertEquals(ParagraphType.TEXT, story.getParagraphs().getFirst().type());
    }

    @Test
    void deserialize_parsesSnakeCaseJson() throws Exception {
        String json = """
                {
                  "name": "Test Story",
                  "paragraphs": [
                    {"type": "text", "location": {"category": "texts", "key": "test_text"}},
                    {"type": "cg", "location": {"category": "images", "key": "test_img"}}
                  ]
                }""";

        Story story = mapper.readValue(json, Story.class);

        assertEquals("Test Story", story.getName());
        assertEquals(2, story.getParagraphs().size());
        assertEquals(ParagraphType.TEXT, story.getParagraphs().getFirst().type());
        assertEquals("texts", story.getParagraphs().getFirst().location().category());
        assertEquals("test_text", story.getParagraphs().getFirst().location().key());
        assertEquals(ParagraphType.CG, story.getParagraphs().get(1).type());
    }

    @Test
    void deserialize_handlesEmptyParagraphs() throws Exception {
        String json = """
                {"name": "Empty", "paragraphs": []}""";

        Story story = mapper.readValue(json, Story.class);

        assertEquals("Empty", story.getName());
        assertNotNull(story.getParagraphs());
        assertTrue(story.getParagraphs().isEmpty());
    }

    @Test
    void deserialize_ignoresUnknownProperties() throws Exception {
        String json = """
                {
                  "name": "With Extra",
                  "paragraphs": [],
                  "extra_field": "should be ignored",
                  "another_one": 42
                }""";

        Story story = mapper.readValue(json, Story.class);

        assertEquals("With Extra", story.getName());
    }

    @Test
    void serializeDeserialize_roundTrip() throws Exception {
        Story original = new Story();
        original.setName("Round Trip");
        List<Paragraph> paragraphs = new ArrayList<>();
        paragraphs.add(new Paragraph(ParagraphType.CG,
                new ResourceLocation("images", "test_img")));
        original.setParagraphs(paragraphs);

        String json = mapper.writeValueAsString(original);
        Story restored = mapper.readValue(json, Story.class);

        assertEquals("Round Trip", restored.getName());
        assertEquals(1, restored.getParagraphs().size());
        assertEquals(ParagraphType.CG, restored.getParagraphs().getFirst().type());
        assertEquals("images", restored.getParagraphs().getFirst().location().category());
        assertEquals("test_img", restored.getParagraphs().getFirst().location().key());
    }
}
