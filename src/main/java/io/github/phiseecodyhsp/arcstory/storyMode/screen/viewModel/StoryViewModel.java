package io.github.phiseecodyhsp.arcstory.storyMode.screen.viewModel;

import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;
import io.github.phiseecodyhsp.arcstory.storyMode.model.Paragraph;
import io.github.phiseecodyhsp.arcstory.storyMode.model.ParagraphType;
import io.github.phiseecodyhsp.arcstory.storyMode.model.Story;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.List;

/**
 * @see io.github.phiseecodyhsp.arcstory.storyMode.screen.view.StoryView
 *
 * @author RikkaKawaii0612
 */
public class StoryViewModel {

    private final ObjectProperty<Story> story;

    private final ObjectProperty<ResourceLocation> partnerAvatar;

    private final ObjectProperty<ResourceLocation> lastCg;

    private final ObjectProperty<ResourceLocation> currentCg;

    private final ObjectProperty<ResourceLocation> currentText;

    private int currentIndex = -1;

    public StoryViewModel(Story story, ResourceLocation partnerAvatar) {
        this.story = new SimpleObjectProperty<>(story);
        this.partnerAvatar = new SimpleObjectProperty<>(partnerAvatar);
        this.lastCg = new SimpleObjectProperty<>();
        this.currentCg = new SimpleObjectProperty<>();
        this.currentText = new SimpleObjectProperty<>();
    }

    public Status playNext() {
        List<Paragraph> list = this.story.get().getParagraphs();
        if (this.currentIndex >= list.size() - 1) {
            return Status.FINISHED;
        }

        this.currentIndex++;
        Paragraph paragraph = list.get(this.currentIndex);
        if (paragraph.type() == ParagraphType.TEXT) {
            this.currentText.setValue(paragraph.location());
            return Status.TEXT;
        } else {
            this.lastCg.setValue(this.currentCg.getValue());
            this.currentCg.setValue(paragraph.location());
            return Status.CG;
        }
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

    public ResourceLocation getLastCg() {
        return this.lastCg.get();
    }

    public ObjectProperty<ResourceLocation> lastCgProperty() {
        return this.lastCg;
    }

    public ResourceLocation getCurrentCg() {
        return this.currentCg.get();
    }

    public ObjectProperty<ResourceLocation> currentCgProperty() {
        return this.currentCg;
    }

    public ResourceLocation getCurrentText() {
        return this.currentText.get();
    }

    public ObjectProperty<ResourceLocation> currentTextProperty() {
        return this.currentText;
    }

    public enum Status {
        TEXT, CG, FINISHED
    }
}
