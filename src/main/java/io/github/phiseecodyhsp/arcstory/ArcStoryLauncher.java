package io.github.phiseecodyhsp.arcstory;

import io.github.phiseecodyhsp.arcstory.core.state.GameState;
import io.github.phiseecodyhsp.arcstory.core.state.SaveManager;
import io.github.phiseecodyhsp.arcstory.res.AudioManager;
import io.github.phiseecodyhsp.arcstory.ui.base.AppWindow;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class ArcStoryLauncher extends Application {

    private static ArcStoryLauncher instance;

    private AppWindow appWindow;
    private AudioManager audioManager;
    private GameState gameState;

    @Override
    public void start(Stage stage) {
        instance = this;
        loadGameState();

        audioManager = new AudioManager();
        appWindow = new AppWindow(stage);

        audioManager.playBgm("story_bgm");
    }

    @Override
    public void stop() {
        if (audioManager != null) {
            audioManager.stopBgm();
        }
        saveGameState();
    }

    private void loadGameState() {
        try {
            gameState = SaveManager.load();
        } catch (IOException e) {
            gameState = new GameState();
        }
    }

    private void saveGameState() {
        if (gameState != null) {
            try {
                SaveManager.save(gameState);
            } catch (IOException ignored) {
            }
        }
    }

    public static ArcStoryLauncher getInstance() {
        return instance;
    }

    public AppWindow getAppWindow() {
        return appWindow;
    }

    public AudioManager getAudioManager() {
        return audioManager;
    }

    public GameState getGameState() {
        return gameState;
    }
}
