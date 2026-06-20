module io.github.phiseecodyhsp.arcstory {
    requires javafx.controls;
    requires javafx.media;
    requires java.desktop;
    requires org.jetbrains.annotations;
    requires com.fasterxml.jackson.databind;

    exports io.github.phiseecodyhsp.arcstory;
    exports io.github.phiseecodyhsp.arcstory.core.story;
    exports io.github.phiseecodyhsp.arcstory.core.state;
    exports io.github.phiseecodyhsp.arcstory.core.condition;
    exports io.github.phiseecodyhsp.arcstory.model;
    exports io.github.phiseecodyhsp.arcstory.res;
    exports io.github.phiseecodyhsp.arcstory.util;

    opens io.github.phiseecodyhsp.arcstory.core.story to com.fasterxml.jackson.databind;
    opens io.github.phiseecodyhsp.arcstory.core.state to com.fasterxml.jackson.databind;
    opens io.github.phiseecodyhsp.arcstory.core.condition to com.fasterxml.jackson.databind;
}
