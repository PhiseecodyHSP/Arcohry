package io.github.phiseecodyhsp.arcstory.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DifficultyTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    @DisplayName("五个难度的缩写读取")
    void fromString_abbr() throws JsonProcessingException {
        assertEquals(Difficulty.PST, MAPPER.readValue("\"pst\"", Difficulty.class));
        assertEquals(Difficulty.PRS, MAPPER.readValue("\"prs\"", Difficulty.class));
        assertEquals(Difficulty.FTR, MAPPER.readValue("\"ftr\"", Difficulty.class));
        assertEquals(Difficulty.BYD, MAPPER.readValue("\"byd\"", Difficulty.class));
        assertEquals(Difficulty.ETR, MAPPER.readValue("\"etr\"", Difficulty.class));
    }

    @Test
    @DisplayName("五个难度的全写读取")
    void fromString_fullName() throws JsonProcessingException {
        assertEquals(Difficulty.PST, MAPPER.readValue("\"past\"", Difficulty.class));
        assertEquals(Difficulty.PRS, MAPPER.readValue("\"present\"", Difficulty.class));
        assertEquals(Difficulty.FTR, MAPPER.readValue("\"future\"", Difficulty.class));
        assertEquals(Difficulty.BYD, MAPPER.readValue("\"beyond\"", Difficulty.class));
        assertEquals(Difficulty.ETR, MAPPER.readValue("\"eternal\"", Difficulty.class));
    }

    @Test
    @DisplayName("忽略大小写")
    void fromString_ignoresCase() throws JsonProcessingException {
        assertEquals(Difficulty.FTR, MAPPER.readValue("\"FTR\"", Difficulty.class));
        assertEquals(Difficulty.FTR, MAPPER.readValue("\"Ftr\"", Difficulty.class));
        assertEquals(Difficulty.FTR, MAPPER.readValue("\"FUTURE\"", Difficulty.class));
        assertEquals(Difficulty.FTR, MAPPER.readValue("\"Future\"", Difficulty.class));
    }

    @Test
    @DisplayName("传入无法识别的参数时抛出 ValueInstantiationException")
    void fromString_throwsExceptionWhenUnexpectedValuesGiven() {
        assertThrows(ValueInstantiationException.class, () -> MAPPER.readValue("\"insane\"", Difficulty.class));
        assertThrows(ValueInstantiationException.class, () -> MAPPER.readValue("\"Another\"", Difficulty.class));
        assertThrows(ValueInstantiationException.class, () -> MAPPER.readValue("\"AT\"", Difficulty.class));
    }
}
