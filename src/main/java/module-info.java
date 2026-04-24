module io.github.phiseecodyhsp.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires java.desktop;
    requires jdk.unsupported.desktop;
    requires org.jetbrains.annotations;

    opens io.github.phiseecodyhsp.demo to javafx.fxml;
    exports io.github.phiseecodyhsp.demo;
    exports io.github.phiseecodyhsp.demo.storyMode;
    opens io.github.phiseecodyhsp.demo.storyMode to javafx.fxml;
}