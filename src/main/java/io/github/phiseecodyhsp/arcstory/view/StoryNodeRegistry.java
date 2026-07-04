package io.github.phiseecodyhsp.arcstory.view;

import io.github.phiseecodyhsp.arcstory.view.node.AvatarNode;
import io.github.phiseecodyhsp.arcstory.view.node.ButtonNode;
import io.github.phiseecodyhsp.arcstory.view.node.StoryNode;
import io.github.phiseecodyhsp.arcstory.viewmodel.node.AvatarNodeViewModel;
import io.github.phiseecodyhsp.arcstory.viewmodel.node.ButtonNodeViewModel;
import io.github.phiseecodyhsp.arcstory.viewmodel.node.StoryNodeViewModel;

import java.util.HashMap;
import java.util.Map;

/**
 * 管理故事节点类型的注册表.
 *
 * <p>在这里使用 {@link #registerStoryNode(Class, StoryNodeFactory)} 来注册故事节点的类型.
 * 这些注册项会被 {@link StoryBranch} 用于创建故事节点 View.
 *
 * @author RikkaKawaii0612
 */
public class StoryNodeRegistry {
    private static final Map<Class<? extends StoryNodeViewModel>, StoryNodeFactory<?>> STORY_NODE_FACTORIES = new HashMap<>();

    /**
     * 注册新的故事节点类型, 并为其指派一个 ViewModel -> View 的构造函数.
     * 如果已经注册过该类, 则会覆盖原先注册的构造方法.
     *
     * @param type 注册的故事节点类
     * @param factory ViewModel -> View 的构造函数
     * @param <T> 注册的故事节点类
     */
    public static <T extends StoryNodeViewModel> void registerStoryNode(Class<T> type, StoryNodeFactory<T> factory) {
        STORY_NODE_FACTORIES.put(type, factory);
    }

    /**
     * 从给定的故事节点 ViewModel 中创建一个新的故事节点 View.
     *
     * @param viewModel 构造 View 所需要的故事节点 ViewModel
     * @param <T> 故事节点类
     * @return 构造出的故事节点 View. 如果传入的故事节点类型未注册, 则返回 {@code null}
     */
    @SuppressWarnings("unchecked")
    public static <T extends StoryNodeViewModel> StoryNode<T> create(T viewModel) {
        Class<?> clazz = viewModel.getClass();
        if (!STORY_NODE_FACTORIES.containsKey(clazz)) {
            return null;
        }
        return ((StoryNodeFactory<T>) STORY_NODE_FACTORIES.get(clazz)).create(viewModel);
    }

    public interface StoryNodeFactory<T extends StoryNodeViewModel> {
        StoryNode<T> create(T viewModel);
    }

    static {
        registerStoryNode(AvatarNodeViewModel.class, AvatarNode::new);
        registerStoryNode(ButtonNodeViewModel.class, ButtonNode::new);
    }
}
