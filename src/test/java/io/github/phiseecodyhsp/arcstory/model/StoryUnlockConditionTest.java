package io.github.phiseecodyhsp.arcstory.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StoryUnlockConditionTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    @DisplayName("同时需求谱面和搭档的解锁条件定义与读取")
    void defaultConstructor_tutorialAndHikari() throws JsonProcessingException {
        String json =
                """
                    {
                        "chart_location": "charts/tutorial_pst",
                        "partner_location": "partners/hikari"
                    }
                """;
        StoryUnlockCondition condition = MAPPER.readValue(json, StoryUnlockCondition.class);

        assertEquals("charts/tutorial_pst", condition.chartLocation().getLocation());
        assertEquals("partners/hikari", condition.partnerLocation().getLocation());
        assertTrue(condition.needsPartner());
    }

    @Test
    @DisplayName("不需要搭档的解锁条件定义与读取")
    void defaultConstructor_noPartner() throws JsonProcessingException {
        String json =
                """
                    {
                        "chart_location": "charts/tutorial_pst",
                        "partner_location": null
                    }
                """;
        StoryUnlockCondition condition = MAPPER.readValue(json, StoryUnlockCondition.class);

        assertEquals("charts/tutorial_pst", condition.chartLocation().getLocation());
        assertNull(condition.partnerLocation());
        assertFalse(condition.needsPartner());
    }
}
