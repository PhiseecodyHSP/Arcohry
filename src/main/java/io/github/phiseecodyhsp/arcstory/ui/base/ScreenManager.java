package io.github.phiseecodyhsp.arcstory.ui.base;

import io.github.phiseecodyhsp.arcstory.ui.util.JavaFxUtil;
import javafx.animation.FadeTransition;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;

public class ScreenManager {

    private final StackPane root;
    private final Map<String, Screen> screens = new LinkedHashMap<>();
    private final Stack<Screen> navigationStack = new Stack<>();
    private Screen currentScreen;

    public ScreenManager(StackPane root) {
        this.root = root;
    }

    public void register(Screen screen) {
        this.screens.put(screen.getId(), screen);
    }

    public void navigateTo(String id, Transition transition) {
        Screen target = this.screens.get(id);
        if (target == null) {
            throw new IllegalArgumentException("Screen not registered: " + id);
        }
        if (this.currentScreen != null) {
            this.currentScreen.onPause();
            this.navigationStack.push(currentScreen);
        }
        switchScreen(target, transition);
    }

    public void goBack(Transition transition) {
        if (this.navigationStack.isEmpty()) return;

        if (this.currentScreen != null) {
            this.currentScreen.onExit();
        }

        Screen previous = this.navigationStack.pop();
        switchScreen(previous, transition);
    }

    private void switchScreen(Screen target, Transition transition) {
        Parent targetRoot = target.getRoot();
        target.onEnter();

        if (transition == Transition.FADE && this.currentScreen != null) {
            Parent oldRoot = this.currentScreen.getRoot();
            targetRoot.setOpacity(0);
            JavaFxUtil.runOnFxThread(() -> {
                this.root.getChildren().add(targetRoot);
                FadeTransition fadeOut = new FadeTransition(Duration.millis(250), oldRoot);
                fadeOut.setFromValue(1.0);
                fadeOut.setToValue(0.0);
                FadeTransition fadeIn = new FadeTransition(Duration.millis(250), targetRoot);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeOut.setOnFinished(_ -> this.root.getChildren().remove(oldRoot));
                fadeOut.play();
                fadeIn.play();
            });
        } else {
            JavaFxUtil.runOnFxThread(() -> {
                this.root.getChildren().clear();
                this.root.getChildren().add(targetRoot);
            });
        }

        this.currentScreen = target;
    }

    public void setInitialScreen(Screen screen) {
        this.currentScreen = screen;
        this.root.getChildren().clear();
        this.root.getChildren().add(screen.getRoot());
        screen.onEnter();
    }

    public Screen getCurrentScreen() {
        return this.currentScreen;
    }

    public Parent getRoot() {
        return this.root;
    }

    public void gotoRoot(Transition transition) {
        if (this.navigationStack.isEmpty()) return;

        if (this.currentScreen != null) {
            this.currentScreen.onExit();
        }
        this.navigationStack.clear();

        Screen first = this.screens.values().iterator().next();
        switchScreen(first, transition);
    }
}
