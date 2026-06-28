package io.github.phiseecodyhsp.arcstory.viewmodel;

import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ButtonNodeViewModelTest {

    private static final ResourceLocation AVATAR = ResourceLocation.image("test_avatar");
    private static final ResourceLocation STORY = ResourceLocation.story("test_story");

    private ButtonNodeViewModel viewModel;

    @BeforeEach
    void setUp() {
        this.viewModel = new ButtonNodeViewModel("Test Title", AVATAR, STORY, null, null);
    }

    @Test
    void lockedProperty_enabledAfterUnlocked() {
        this.viewModel.lockedProperty().set(true);
        assertFalse(this.viewModel.enabledProperty().get());
        this.viewModel.lockedProperty().set(false);
        assertTrue(this.viewModel.enabledProperty().get());
        this.viewModel.lockedProperty().set(true);
        assertTrue(this.viewModel.enabledProperty().get());
    }
}
