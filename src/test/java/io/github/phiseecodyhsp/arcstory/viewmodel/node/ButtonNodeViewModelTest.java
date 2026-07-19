package io.github.phiseecodyhsp.arcstory.viewmodel.node;

import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ButtonNodeViewModelTest {

    private static final ResourceLocation AVATAR = ResourceLocation.image("test_avatar");

    private ButtonNodeViewModel viewModel;

    @BeforeEach
    void setUp() {
        this.viewModel = new ButtonNodeViewModel("Test Title", AVATAR);
    }

    @Test
    void lockedProperty_enabledAfterUnlocked() {
        this.viewModel.lockedProperty().set(true);
        this.viewModel.enabledProperty().set(false);
        assertFalse(this.viewModel.enabledProperty().get());
        this.viewModel.lockedProperty().set(false);
        assertTrue(this.viewModel.enabledProperty().get());
        this.viewModel.lockedProperty().set(true);
        assertTrue(this.viewModel.enabledProperty().get());
    }
}
