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

        this.audioManager = new AudioManager();
        this.appWindow = new AppWindow(stage);

        this.audioManager.playBgm("story_bgm");
    }

    @Override
    public void stop() {
        if (this.audioManager != null) {
            this.audioManager.stopBgm();
        }
        saveGameState();
    }

    private void loadGameState() {
        try {
            this.gameState = SaveManager.load();
        } catch (IOException e) {
            this.gameState = new GameState();
        }
    }

    private void saveGameState() {
        if (this.gameState != null) {
            try {
                SaveManager.save(this.gameState);
            } catch (IOException ignored) {
            }
        }
    }

    public static ArcStoryLauncher getInstance() {
        return instance;
    }

    public AppWindow getAppWindow() {
        return this.appWindow;
    }

    public AudioManager getAudioManager() {
        return this.audioManager;
    }

    public GameState getGameState() {
        return this.gameState;
    }
}
