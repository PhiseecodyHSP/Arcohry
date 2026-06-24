package io.github.phiseecodyhsp.arcstory.storyMode.view.node;

import io.github.phiseecodyhsp.arcstory.model.Partner;
import io.github.phiseecodyhsp.arcstory.storyMode.view.Avatar;
import io.github.phiseecodyhsp.arcstory.storyMode.view.Effects;
import io.github.phiseecodyhsp.arcstory.storyMode.viewModel.AvatarNodeViewModel;
import javafx.scene.paint.Color;

/**
 * 搭档节点. 传入一个 Arcaea 头像栏以进行绘制.
 *
 * @author RikkaKawaii0612
 */
public class AvatarNode extends StoryNode<AvatarNodeViewModel> {

    /**
     * 头像边长.
     */
    private static final double SIDE_LENGTH = 100.0D;

    /**
     * 头像边框颜色.
     */
    private static final Color AVATAR_BORDER = Color.rgb(150, 140, 160);

    public AvatarNode(AvatarNodeViewModel model) {
        super(model);

        Avatar avatar = new Avatar(null, SIDE_LENGTH, AVATAR_BORDER, Effects.SHADOW);
        avatar.avatarLocationProperty().bind(model.partnerProperty().map(Partner::avatarLocation));
        getChildren().addAll(avatar);
    }
}
