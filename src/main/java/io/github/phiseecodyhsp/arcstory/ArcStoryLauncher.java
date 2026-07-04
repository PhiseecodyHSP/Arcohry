package io.github.phiseecodyhsp.arcstory;

import io.github.phiseecodyhsp.arcstory.deprecated.state.GameState;
import io.github.phiseecodyhsp.arcstory.deprecated.state.SaveManager;
import io.github.phiseecodyhsp.arcstory.model.Partners;
import io.github.phiseecodyhsp.arcstory.res.AudioManager;
import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;
import io.github.phiseecodyhsp.arcstory.ui.screen.StoryScreen;
import io.github.phiseecodyhsp.arcstory.ui.screen.viewmodel.StoryScreenViewModel;
import io.github.phiseecodyhsp.arcstory.viewmodel.AvatarNodeViewModel;
import io.github.phiseecodyhsp.arcstory.viewmodel.ButtonNodeViewModel;
import io.github.phiseecodyhsp.arcstory.viewmodel.StoryBranchViewModel;
import io.github.phiseecodyhsp.arcstory.ui.base.AppWindow;
import io.github.phiseecodyhsp.arcstory.ui.base.ScreenManager;
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
        this.registerScreens(this.appWindow.getScreenManager());

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

    private void registerScreens(ScreenManager screenManager) {
        StoryScreenViewModel storyScreenViewModel = new StoryScreenViewModel(ResourceLocation.image("chapter5_scenery"));
        StoryBranchViewModel branch1 = new StoryBranchViewModel();
        branch1.getStoryNodes().addAll(new AvatarNodeViewModel(Partners.Hikari),
                new ButtonNodeViewModel("Test", ResourceLocation.image("tutorial_illustration"), ResourceLocation.story("test"), null, null));
        StoryBranchViewModel branch2 = new StoryBranchViewModel();
        branch2.getStoryNodes().addAll(new ButtonNodeViewModel("A1", ResourceLocation.image("tutorial_illustration"), ResourceLocation.story("test"), null, null),
                new ButtonNodeViewModel("A2", ResourceLocation.image("tutorial_illustration"), ResourceLocation.story("test"), null, null),
                new ButtonNodeViewModel("A3", ResourceLocation.image("tutorial_illustration"), ResourceLocation.story("test"), null, null));
        storyScreenViewModel.getStoryBranches().addAll(branch1, branch2);
        StoryScreen storyScreen = new StoryScreen(storyScreenViewModel);
        screenManager.register(storyScreen);
        screenManager.setInitialScreen(storyScreen);
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
