package io.github.phiseecodyhsp.arcstory.ui.screen.viewmodel;

import io.github.phiseecodyhsp.arcstory.model.Chart;
import io.github.phiseecodyhsp.arcstory.model.Partner;
import io.github.phiseecodyhsp.arcstory.model.StoryUnlockCondition;
import io.github.phiseecodyhsp.arcstory.res.ResourceLoader;

public class StoryUnlockConditionViewModel {

    private final Chart chart;

    private final Partner partner;

    private final Runnable onFinishedCallback;

    public StoryUnlockConditionViewModel(StoryUnlockCondition condition, Runnable onFinishedCallback) {
        this.chart = ResourceLoader.loadChart(condition.chartLocation());
        this.partner = ResourceLoader.loadPartner(condition.partnerLocation());
        this.onFinishedCallback = onFinishedCallback;
    }

    public Chart getChart() {
        return this.chart;
    }

    public Partner getPartner() {
        return this.partner;
    }

    public boolean needsPartner() {
        return this.partner != null;
    }

    public void requestRemoving() {
        if (this.onFinishedCallback != null) {
            this.onFinishedCallback.run();
        }
    }
}
