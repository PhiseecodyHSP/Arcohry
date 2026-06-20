package io.github.phiseecodyhsp.arcstory.core.story;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChoiceOption {

    @JsonProperty("text")
    private String text;

    @JsonProperty("nextScene")
    private String nextScene;

    public ChoiceOption() {}

    public ChoiceOption(String text, String nextScene) {
        this.text = text;
        this.nextScene = nextScene;
    }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getNextScene() { return nextScene; }
    public void setNextScene(String nextScene) { this.nextScene = nextScene; }
}
