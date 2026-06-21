package io.github.phiseecodyhsp.arcstory.ui.util;

import javafx.application.Platform;

public final class JavaFxUtil {

    private JavaFxUtil() {}

    /**
     * 在 JavaFX 线程上运行一段代码. 若当前已在 JavaFX 线程上, 则直接运行.
     *
     * @param action 要运行的代码
     */
    public static void runOnFxThread(Runnable action) {
        if (Platform.isFxApplicationThread()) {
            action.run();
        } else {
            Platform.runLater(action);
        }
    }

}
