module io.github.phiseecodyhsp.arcstory {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires java.desktop;
    requires jdk.unsupported.desktop;
    requires org.jetbrains.annotations;
    requires javafx.media;

    opens io.github.phiseecodyhsp.arcstory to javafx.fxml;
    opens io.github.phiseecodyhsp.arcstory.storyMode to javafx.fxml;
    opens io.github.phiseecodyhsp.arcstory.storage to javafx.fxml;
    exports io.github.phiseecodyhsp.arcstory;
    exports io.github.phiseecodyhsp.arcstory.storyMode;
    exports io.github.phiseecodyhsp.arcstory.storage;
}