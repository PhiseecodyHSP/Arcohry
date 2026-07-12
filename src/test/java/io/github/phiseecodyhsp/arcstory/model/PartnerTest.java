package io.github.phiseecodyhsp.arcstory.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PartnerTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void readJson() throws JsonProcessingException {
        String json =
                """
                    {
                        "name": "Hikari",
                        "avatarLocation": "images/hikari_avatar",
                        "illustrationLocation": "images/hikari_illustration"
                    }
                """;
        Partner partner = MAPPER.readValue(json, Partner.class);

        assertEquals("Hikari", partner.name());
        assertEquals("images/hikari_avatar", partner.avatarLocation().getLocation());
        assertEquals("images/hikari_illustration", partner.illustrationLocation().getLocation());
    }
}
