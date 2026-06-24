package io.github.phiseecodyhsp.arcstory.storyMode.screen.viewModel;

import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;
import io.github.phiseecodyhsp.arcstory.storyMode.model.Paragraph;
import io.github.phiseecodyhsp.arcstory.storyMode.model.ParagraphType;
import io.github.phiseecodyhsp.arcstory.storyMode.model.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StoryViewModelTest {

    private static final ResourceLocation AVATAR = ResourceLocation.image("test_avatar");
    private static final ResourceLocation TEXT_LOC = ResourceLocation.text("test_text");
    private static final ResourceLocation CG1_LOC = ResourceLocation.image("cg_1");
    private static final ResourceLocation CG2_LOC = ResourceLocation.image("cg_2");

    private StoryViewModel viewModel;
    private Story story;

    private Story makeStory(List<Paragraph> paragraphs) {
        Story s = new Story();
        s.setName("Test");
        s.setParagraphs(paragraphs);
        return s;
    }

    @BeforeEach
    void setUp() {
        story = null;
        viewModel = null;
    }

    //  测试正常流程

    @Test
    void playNext_mixedParagraphs_progressesCorrectly() {
        List<Paragraph> paragraphs = new ArrayList<>();
        paragraphs.add(new Paragraph(ParagraphType.TEXT, TEXT_LOC));
        paragraphs.add(new Paragraph(ParagraphType.CG, CG1_LOC));
        paragraphs.add(new Paragraph(ParagraphType.CG, CG2_LOC));

        viewModel = new StoryViewModel(makeStory(paragraphs), AVATAR);

        assertEquals(StoryViewModel.Status.TEXT, viewModel.playNext());
        assertEquals(TEXT_LOC, viewModel.getCurrentText());
        assertNull(viewModel.getCurrentCg());
        assertNull(viewModel.getLastCg());

        assertEquals(StoryViewModel.Status.CG, viewModel.playNext());
        assertEquals(CG1_LOC, viewModel.getCurrentCg());
        assertNull(viewModel.getLastCg());

        assertEquals(StoryViewModel.Status.CG, viewModel.playNext());
        assertEquals(CG2_LOC, viewModel.getCurrentCg());
        assertEquals(CG1_LOC, viewModel.getLastCg());

        assertEquals(StoryViewModel.Status.FINISHED, viewModel.playNext());
    }

    // 测试边界情况

    @Test
    void playNext_singleParagraph_playsThenFinishes() {
        List<Paragraph> paragraphs = new ArrayList<>();
        paragraphs.add(new Paragraph(ParagraphType.TEXT, TEXT_LOC));

        viewModel = new StoryViewModel(makeStory(paragraphs), AVATAR);

        assertEquals(StoryViewModel.Status.TEXT, viewModel.playNext());
        assertEquals(TEXT_LOC, viewModel.getCurrentText());

        assertEquals(StoryViewModel.Status.FINISHED, viewModel.playNext());
    }

    @Test
    void playNext_emptyList_finishesImmediately() {
        List<Paragraph> paragraphs = new ArrayList<>();

        viewModel = new StoryViewModel(makeStory(paragraphs), AVATAR);

        assertEquals(StoryViewModel.Status.FINISHED, viewModel.playNext());
    }

    @Test
    void playNext_allText_updatesCurrentTextEachTime() {
        ResourceLocation text2 = ResourceLocation.text("text_2");
        ResourceLocation text3 = ResourceLocation.text("text_3");
        List<Paragraph> paragraphs = new ArrayList<>();
        paragraphs.add(new Paragraph(ParagraphType.TEXT, TEXT_LOC));
        paragraphs.add(new Paragraph(ParagraphType.TEXT, text2));
        paragraphs.add(new Paragraph(ParagraphType.TEXT, text3));

        viewModel = new StoryViewModel(makeStory(paragraphs), AVATAR);

        assertEquals(StoryViewModel.Status.TEXT, viewModel.playNext());
        assertEquals(TEXT_LOC, viewModel.getCurrentText());

        assertEquals(StoryViewModel.Status.TEXT, viewModel.playNext());
        assertEquals(text2, viewModel.getCurrentText());

        assertEquals(StoryViewModel.Status.TEXT, viewModel.playNext());
        assertEquals(text3, viewModel.getCurrentText());

        assertEquals(StoryViewModel.Status.FINISHED, viewModel.playNext());
    }

    @Test
    void playNext_allCg_lastCgTracksPrevious() {
        List<Paragraph> paragraphs = new ArrayList<>();
        paragraphs.add(new Paragraph(ParagraphType.CG, CG1_LOC));
        paragraphs.add(new Paragraph(ParagraphType.CG, CG2_LOC));
        paragraphs.add(new Paragraph(ParagraphType.CG,
                ResourceLocation.image("cg_3")));

        viewModel = new StoryViewModel(makeStory(paragraphs), AVATAR);

        assertEquals(StoryViewModel.Status.CG, viewModel.playNext());
        assertEquals(CG1_LOC, viewModel.getCurrentCg());
        assertNull(viewModel.getLastCg());

        assertEquals(StoryViewModel.Status.CG, viewModel.playNext());
        assertEquals(CG2_LOC, viewModel.getCurrentCg());
        assertEquals(CG1_LOC, viewModel.getLastCg());

        assertEquals(StoryViewModel.Status.CG, viewModel.playNext());
        assertEquals(ResourceLocation.image("cg_3"), viewModel.getCurrentCg());
        assertEquals(CG2_LOC, viewModel.getLastCg());
    }

    @Test
    void playNext_afterFinished_stillReturnsFinished() {
        List<Paragraph> paragraphs = new ArrayList<>();
        paragraphs.add(new Paragraph(ParagraphType.TEXT, TEXT_LOC));

        viewModel = new StoryViewModel(makeStory(paragraphs), AVATAR);

        viewModel.playNext();
        assertEquals(StoryViewModel.Status.FINISHED, viewModel.playNext());
        assertEquals(StoryViewModel.Status.FINISHED, viewModel.playNext());
        assertEquals(StoryViewModel.Status.FINISHED, viewModel.playNext());
    }

    // 属性初始状态

    @Test
    void properties_beforePlay_areNull() {
        List<Paragraph> paragraphs = new ArrayList<>();
        paragraphs.add(new Paragraph(ParagraphType.TEXT, TEXT_LOC));

        viewModel = new StoryViewModel(makeStory(paragraphs), AVATAR);

        assertNull(viewModel.getCurrentCg());
        assertNull(viewModel.getCurrentText());
        assertNull(viewModel.getLastCg());
    }

    // null边界

    @Test
    void constructorNullStory_playNextThrowsNpe() {
        viewModel = new StoryViewModel(null, AVATAR);

        assertThrows(NullPointerException.class, () -> viewModel.playNext());
    }

    @Test
    void constructorNullParagraphs_playNextThrowsNpe() {
        Story s = new Story();
        s.setName("Null Paragraphs");
        s.setParagraphs(null);

        viewModel = new StoryViewModel(s, AVATAR);

        assertThrows(NullPointerException.class, () -> viewModel.playNext());
    }

    // property getter

    @Test
    void propertyGetters_returnSameObject() {
        List<Paragraph> paragraphs = new ArrayList<>();
        paragraphs.add(new Paragraph(ParagraphType.TEXT, TEXT_LOC));

        viewModel = new StoryViewModel(makeStory(paragraphs), AVATAR);

        assertSame(viewModel.storyProperty(), viewModel.storyProperty());
        assertSame(viewModel.partnerAvatarProperty(), viewModel.partnerAvatarProperty());
        assertSame(viewModel.lastCgProperty(), viewModel.lastCgProperty());
        assertSame(viewModel.currentCgProperty(), viewModel.currentCgProperty());
        assertSame(viewModel.currentTextProperty(), viewModel.currentTextProperty());

        assertEquals("Test", viewModel.getStory().getName());
        assertEquals(AVATAR, viewModel.getPartnerAvatar());
    }
}
