module io.github.phiseecodyhsp.arcstory {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires java.desktop;
    requires jdk.unsupported.desktop;
    requires org.jetbrains.annotations;
    requires javafx.media;
    requires com.fasterxml.jackson.databind;

    opens io.github.phiseecodyhsp.arcstory to javafx.fxml;
    opens io.github.phiseecodyhsp.arcstory.storage to javafx.fxml;
    opens io.github.phiseecodyhsp.arcstory.storyMode to javafx.fxml, com.fasterxml.jackson.databind;
    exports io.github.phiseecodyhsp.arcstory;
    exports io.github.phiseecodyhsp.arcstory.storyMode;
    exports io.github.phiseecodyhsp.arcstory.storage;
}