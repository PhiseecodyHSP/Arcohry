module io.github.phiseecodyhsp.arcstory {
    requires javafx.controls;
    requires javafx.media;
    requires java.desktop;
    requires org.jetbrains.annotations;
    requires com.fasterxml.jackson.databind;

    exports io.github.phiseecodyhsp.arcstory;
    exports io.github.phiseecodyhsp.arcstory.deprecated.story;
    exports io.github.phiseecodyhsp.arcstory.deprecated.state;
    exports io.github.phiseecodyhsp.arcstory.deprecated.condition;
    exports io.github.phiseecodyhsp.arcstory.model;
    exports io.github.phiseecodyhsp.arcstory.model.story;
    exports io.github.phiseecodyhsp.arcstory.res;
    exports io.github.phiseecodyhsp.arcstory.util;
    exports io.github.phiseecodyhsp.arcstory.ui.base;
    exports io.github.phiseecodyhsp.arcstory.ui.util;

    opens io.github.phiseecodyhsp.arcstory.deprecated.story to com.fasterxml.jackson.databind;
    opens io.github.phiseecodyhsp.arcstory.deprecated.state to com.fasterxml.jackson.databind;
    opens io.github.phiseecodyhsp.arcstory.deprecated.condition to com.fasterxml.jackson.databind;
    opens io.github.phiseecodyhsp.arcstory.model.story to com.fasterxml.jackson.databind;
    exports io.github.phiseecodyhsp.arcstory.model.difficulty;
}
