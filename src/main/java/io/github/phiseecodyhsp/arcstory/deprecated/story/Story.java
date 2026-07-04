package io.github.phiseecodyhsp.arcstory.deprecated.story;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.phiseecodyhsp.arcstory.deprecated.condition.UnlockCondition;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Story {

    @JsonProperty("id")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("version")
    private String version;

    @JsonProperty("background")
    private String background;

    @JsonProperty("bgm")
    private String bgm;

    @JsonProperty("unlock")
    private UnlockCondition unlock;

    @JsonProperty("hasCG")
    private boolean hasCG;

    @JsonProperty("scenes")
    private List<Scene> scenes;

    public Story() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    public String getBackground() { return background; }
    public void setBackground(String background) { this.background = background; }

    public String getBgm() { return bgm; }
    public void setBgm(String bgm) { this.bgm = bgm; }

    public UnlockCondition getUnlock() { return unlock; }
    public void setUnlock(UnlockCondition unlock) { this.unlock = unlock; }

    public boolean isHasCG() { return hasCG; }
    public void setHasCG(boolean hasCG) { this.hasCG = hasCG; }

    public List<Scene> getScenes() { return scenes; }
    public void setScenes(List<Scene> scenes) { this.scenes = scenes; }
}
