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
        screens.put(screen.getId(), screen);
    }

    public void navigateTo(String id, Transition transition) {
        Screen target = screens.get(id);
        if (target == null) {
            throw new IllegalArgumentException("Screen not registered: " + id);
        }
        if (currentScreen != null) {
            currentScreen.onPause();
            navigationStack.push(currentScreen);
        }
        switchScreen(target, transition);
    }

    public void goBack(Transition transition) {
        if (navigationStack.isEmpty()) return;

        if (currentScreen != null) {
            currentScreen.onExit();
        }

        Screen previous = navigationStack.pop();
        switchScreen(previous, transition);
    }

    private void switchScreen(Screen target, Transition transition) {
        Parent targetRoot = target.getRoot();
        target.onEnter();

        if (transition == Transition.FADE && currentScreen != null) {
            Parent oldRoot = currentScreen.getRoot();
            targetRoot.setOpacity(0);
            JavaFxUtil.runOnFxThread(() -> {
                root.getChildren().add(targetRoot);
                FadeTransition fadeOut = new FadeTransition(Duration.millis(250), oldRoot);
                fadeOut.setFromValue(1.0);
                fadeOut.setToValue(0.0);
                FadeTransition fadeIn = new FadeTransition(Duration.millis(250), targetRoot);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeOut.setOnFinished(e -> root.getChildren().remove(oldRoot));
                fadeOut.play();
                fadeIn.play();
            });
        } else {
            JavaFxUtil.runOnFxThread(() -> {
                root.getChildren().clear();
                root.getChildren().add(targetRoot);
            });
        }

        currentScreen = target;
    }

    public void setInitialScreen(Screen screen) {
        currentScreen = screen;
        root.getChildren().clear();
        root.getChildren().add(screen.getRoot());
        screen.onEnter();
    }

    public Screen getCurrentScreen() {
        return currentScreen;
    }

    public Parent getRoot() {
        return root;
    }

    public void gotoRoot(Transition transition) {
        if (navigationStack.isEmpty()) return;

        if (currentScreen != null) {
            currentScreen.onExit();
        }
        navigationStack.clear();

        Screen first = screens.values().iterator().next();
        switchScreen(first, transition);
    }
}
