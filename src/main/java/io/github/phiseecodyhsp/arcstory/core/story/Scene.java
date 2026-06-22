package io.github.phiseecodyhsp.arcstory.core.story;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Scene {

    @JsonProperty("id")
    private String id;

    @JsonProperty("type")
    private SceneType type;

    @JsonProperty("image")
    private String image;

    @JsonProperty("reveal")
    private String reveal;

    @JsonProperty("speaker")
    private String speaker;

    @JsonProperty("text")
    private String text;

    @JsonProperty("avatar")
    private String avatar;

    @JsonProperty("audio")
    private String audio;

    @JsonProperty("fontSize")
    private Double fontSize;

    @JsonProperty("music")
    private String music;

    @JsonProperty("duration")
    private Double duration;

    @JsonProperty("effect")
    private String effect;

    @JsonProperty("returnTo")
    private String returnTo;

    @JsonProperty("prompt")
    private String prompt;

    @JsonProperty("options")
    private List<ChoiceOption> options;

    @JsonProperty("nextScene")
    private String nextScene;

    public Scene() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public SceneType getType() { return type; }
    public void setType(SceneType type) { this.type = type; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getReveal() { return reveal; }
    public void setReveal(String reveal) { this.reveal = reveal; }

    public String getSpeaker() { return speaker; }
    public void setSpeaker(String speaker) { this.speaker = speaker; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }

    public String getAudio() { return audio; }
    public void setAudio(String audio) { this.audio = audio; }

    public Double getFontSize() { return fontSize; }
    public void setFontSize(Double fontSize) { this.fontSize = fontSize; }

    public String getMusic() { return music; }
    public void setMusic(String music) { this.music = music; }

    public Double getDuration() { return duration; }
    public void setDuration(Double duration) { this.duration = duration; }

    public String getEffect() { return effect; }
    public void setEffect(String effect) { this.effect = effect; }

    public String getReturnTo() { return returnTo; }
    public void setReturnTo(String returnTo) { this.returnTo = returnTo; }

    public String getPrompt() { return prompt; }
    public void setPrompt(String prompt) { this.prompt = prompt; }

    public List<ChoiceOption> getOptions() { return options; }
    public void setOptions(List<ChoiceOption> options) { this.options = options; }

    public String getNextScene() { return nextScene; }
    public void setNextScene(String nextScene) { this.nextScene = nextScene; }
}
