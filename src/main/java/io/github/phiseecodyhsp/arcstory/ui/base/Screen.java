package io.github.phiseecodyhsp.arcstory.ui.base;

import javafx.scene.Parent;

public interface Screen {

    String getId();

    Parent getRoot();

    void onEnter();

    void onExit();

    void onResume();

    void onPause();
}
