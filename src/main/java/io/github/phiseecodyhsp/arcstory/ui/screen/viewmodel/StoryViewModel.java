package io.github.phiseecodyhsp.arcstory.ui.screen.viewmodel;

import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;
import io.github.phiseecodyhsp.arcstory.model.story.Paragraph;
import io.github.phiseecodyhsp.arcstory.model.story.ParagraphType;
import io.github.phiseecodyhsp.arcstory.model.story.Story;
import io.github.phiseecodyhsp.arcstory.ui.screen.view.StoryView;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.List;

/**
 * @see StoryView
 *
 * @author RikkaKawaii0612
 */
public class StoryViewModel {

    private final ObjectProperty<Story> story;

    private final ObjectProperty<ResourceLocation> partnerAvatar;

    private final Runnable onFinishedCallback;

    private final ObjectProperty<ResourceLocation> bottomCg;

    private final ObjectProperty<ResourceLocation> topCg;

    private final ObjectProperty<ResourceLocation> currentText;

    private final ObjectProperty<Status> currentStatus;

    private final BooleanProperty shadowHidden;

    private int currentIndex = -1;

    public StoryViewModel(Story story, ResourceLocation partnerAvatar, Runnable onFinishedCallback) {
        this.story = new SimpleObjectProperty<>(story);
        this.partnerAvatar = new SimpleObjectProperty<>(partnerAvatar);
        this.onFinishedCallback = onFinishedCallback;
        this.bottomCg = new SimpleObjectProperty<>();
        this.topCg = new SimpleObjectProperty<>();
        this.currentText = new SimpleObjectProperty<>();
        this.currentStatus = new SimpleObjectProperty<>(Status.WAITING);
        this.shadowHidden = new SimpleBooleanProperty(true);

//        this.currentStatus.addListener((_, oldVar, newVar) -> {
//            // CG 动画播放完毕后自动步进故事
//            if (oldVar == Status.CG && newVar == Status.WAITING) {
//                this.playNext();
//            }
//        });
    }

    public ResourceLocation getPartnerAvatar() {
        return this.partnerAvatar.get();
    }

    public ObjectProperty<ResourceLocation> partnerAvatarProperty() {
        return this.partnerAvatar;
    }

    public Story getStory() {
        return this.story.get();
    }

    public ObjectProperty<Story> storyProperty() {
        return this.story;
    }

    public ResourceLocation getBottomCg() {
        return this.bottomCg.get();
    }

    public ObjectProperty<ResourceLocation> bottomCgProperty() {
        return this.bottomCg;
    }

    public ResourceLocation getTopCg() {
        return this.topCg.get();
    }

    public ObjectProperty<ResourceLocation> topCgProperty() {
        return this.topCg;
    }

    public ResourceLocation getCurrentText() {
        return this.currentText.get();
    }

    public ObjectProperty<ResourceLocation> currentTextProperty() {
        return this.currentText;
    }

    public void proceed() {
        switch (this.getCurrentStatus()) {
            case TEXT -> this.markWaiting();
            case WAITING -> this.playNext();
        }
    }

    private void playNext() {
        List<Paragraph> list = this.story.get().getParagraphs();
        if (this.currentIndex >= list.size() - 1) {
            this.currentStatus.setValue(Status.FINISHED);
            return;
        }

        this.currentIndex++;
        if (this.topCg.getValue() != null) {
            this.bottomCg.setValue(this.topCg.getValue());
        }
        this.topCg.setValue(null);

        Paragraph paragraph = list.get(this.currentIndex);
        if (paragraph.type() == ParagraphType.TEXT) {
            this.currentText.setValue(paragraph.location());
            this.currentStatus.setValue(Status.TEXT);
            this.shadowHidden.setValue(false);
        } else {
            this.topCg.setValue(paragraph.location());
            this.currentStatus.setValue(Status.CG);
            this.shadowHidden.setValue(true);
        }
    }

    /**
     * 修改当前状态为 {@link Status#WAITING}. 用于动画播放完毕, 等待下一步输入.
     */
    public void markWaiting() {
        this.currentStatus.setValue(Status.WAITING);
    }

    /**
     * 请求移除 View 层的故事播放. 在结束动画后被调用, 见 {@link StoryView#onStatusChanged(Status)}.
     */
    public void requestRemoving() {
        if (this.onFinishedCallback != null) {
            this.onFinishedCallback.run();
        }
    }

    public Status getCurrentStatus() {
        return this.currentStatus.get();
    }

    public ObjectProperty<Status> currentStatusProperty() {
        return this.currentStatus;
    }

    public boolean isShadowHidden() {
        return this.shadowHidden.get();
    }

    public BooleanProperty shadowHiddenProperty() {
        return this.shadowHidden;
    }

    public enum Status {
        TEXT, CG, WAITING, FINISHED
    }
}
