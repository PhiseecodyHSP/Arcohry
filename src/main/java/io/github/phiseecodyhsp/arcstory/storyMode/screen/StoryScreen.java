package io.github.phiseecodyhsp.arcstory.storyMode.screen;

import io.github.phiseecodyhsp.arcstory.storyMode.screen.view.StoryScreenView;
import io.github.phiseecodyhsp.arcstory.storyMode.screen.viewModel.StoryScreenViewModel;
import io.github.phiseecodyhsp.arcstory.ui.base.Screen;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

/**
 * 仿 Arcaea 故事模式的界面场景.
 *
 * @author RikkaKawaii0612
 */
public class StoryScreen extends StackPane implements Screen {

    private final StoryScreenView view;

    public StoryScreen(StoryScreenViewModel viewModel) {
        this.view = new StoryScreenView(viewModel);
    }

    @Override
    public String getScreenId() {
        return "story";
    }

    @Override
    public Parent getView() {
        return this.view;
    }

    @Override
    public void onEnter() {
    }

    @Override
    public void onExit() {
    }

    @Override
    public void onResume() {
        // 怎么会有暂停和恢复的方法??
    }

    @Override
    public void onPause() {
    }
}