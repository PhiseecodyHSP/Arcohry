package io.github.phiseecodyhsp.arcstory.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MathUtilTest {

    @Test
    @DisplayName("intToOrdinal 传入个位数不为 1, 2, 3 时应添加 -th 后缀")
    void intToOrdinal_whenUnitPlaceNot123_returnsSpecialSuffixes() {
        assertEquals("4th", MathUtil.intToOrdinal(4));
        assertEquals("16th", MathUtil.intToOrdinal(16));
        assertEquals("85th", MathUtil.intToOrdinal(85));
        assertEquals("2026th", MathUtil.intToOrdinal(2026));
    }

    @Test
    @DisplayName("intToOrdinal 传入十位数不为 1 时应可以添加 -st, -rd, -nd 后缀")
    void intToOrdinal_whenTenPlaceNot1_returnsSpecialSuffixes() {
        assertEquals("1st", MathUtil.intToOrdinal(1));
        assertEquals("2nd", MathUtil.intToOrdinal(2));
        assertEquals("3rd", MathUtil.intToOrdinal(3));
        assertEquals("21st", MathUtil.intToOrdinal(21));
        assertEquals("622nd", MathUtil.intToOrdinal(622));
    }

    @Test
    @DisplayName("intToOrdinal 传入十位数为 1 时应统一添加 -th 后缀")
    void intToOrdinal_whenTenPlace1_returnsSpecialSuffixes() {
        assertEquals("11th", MathUtil.intToOrdinal(11));
        assertEquals("12th", MathUtil.intToOrdinal(12));
        assertEquals("13th", MathUtil.intToOrdinal(13));
        assertEquals("211th", MathUtil.intToOrdinal(211));
    }

    @Test
    @DisplayName("intToOrdinal 传入负数时, 应返回加负号前缀的传入正数的结果")
    void intToOrdinal_whenNegative_returnsNegativePrefixes() {
        assertEquals("-" + MathUtil.intToOrdinal(1), MathUtil.intToOrdinal(-1));
        assertEquals("-" + MathUtil.intToOrdinal(12), MathUtil.intToOrdinal(-12));
        assertEquals("-" + MathUtil.intToOrdinal(235), MathUtil.intToOrdinal(-235));
    }
}
