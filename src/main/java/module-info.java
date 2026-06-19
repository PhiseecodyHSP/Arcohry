module io.github.phiseecodyhsp.arcstory {
    requires javafx.controls;
    requires java.desktop;
    requires org.jetbrains.annotations;
    requires javafx.media;
    requires com.fasterxml.jackson.databind;

    opens io.github.phiseecodyhsp.arcstory.storyMode to com.fasterxml.jackson.databind;
    exports io.github.phiseecodyhsp.arcstory;
    exports io.github.phiseecodyhsp.arcstory.storyMode;
    exports io.github.phiseecodyhsp.arcstory.storage;
}