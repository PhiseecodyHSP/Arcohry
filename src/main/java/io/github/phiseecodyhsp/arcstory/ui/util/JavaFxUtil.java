package io.github.phiseecodyhsp.arcstory.ui.util;

import javafx.application.Platform;

public final class JavaFxUtil {

    private JavaFxUtil() {}

    public static void runOnFxThread(Runnable action) {
        if (Platform.isFxApplicationThread()) {
            action.run();
        } else {
            Platform.runLater(action);
        }
    }

}
