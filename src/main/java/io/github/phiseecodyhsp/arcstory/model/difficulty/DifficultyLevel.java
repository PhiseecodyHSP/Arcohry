package io.github.phiseecodyhsp.arcstory.model.difficulty;

/**
 * Arcaea 谱面难度分级, 包含 1~12 和 7+, 8+, 9+, 10+, 11+.
 *
 * <p>可以方便地获取其对应的基础分级 ({@link #base}) 和是否为 "+" 分级 ({@link #plus}).
 * 同时该类重写了 {@link #toString()} 方法, 会自动输出如 "11", "9+" 这种字符串.
 */
public enum DifficultyLevel {
    LEVEL_1(1, false),
    LEVEL_2(2, false),
    LEVEL_3(3, false),
    LEVEL_4(4, false),
    LEVEL_5(5, false),
    LEVEL_6(6, false),
    LEVEL_7(7, false),
    LEVEL_7_PLUS(7, true),
    LEVEL_8(8, false),
    LEVEL_8_PLUS(8, true),
    LEVEL_9(9, false),
    LEVEL_9_PLUS(9, true),
    LEVEL_10(10, false),
    LEVEL_10_PLUS(10, true),
    LEVEL_11(11, false),
    LEVEL_11_PLUS(11, true),
    LEVEL_12(12, false);

    private final int base;
    private final boolean plus;

    DifficultyLevel(int base, boolean plus) {
        this.base = base;
        this.plus = plus;
    }

    public int getBase() {
        return this.base;
    }

    public boolean isPlus() {
        return this.plus;
    }

    @Override
    public String toString() {
        return this.plus ? this.base + "+" : String.valueOf(this.base);
    }
}
