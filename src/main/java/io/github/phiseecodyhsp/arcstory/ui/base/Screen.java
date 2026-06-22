package io.github.phiseecodyhsp.arcstory.ui.base;

import javafx.scene.Parent;

/**
 * 界面场景.
 *
 * @author KashiKoiAstra
 */
public interface Screen {

    /**
     * 获取场景的唯一 ID.
     *
     * @return 场景 ID
     */
    String getId();

    /**
     * 获取场景的渲染节点. 通常返回一个 {@link javafx.scene.layout.Pane} 的子类.
     *
     * @return 场景渲染节点
     */
    Parent getView();

    /**
     * 进入该场景时触发的动作.
     */
    void onEnter();

    /**
     * 退出该场景时触发的动作.
     */
    void onExit();

    void onResume();

    void onPause();
}
