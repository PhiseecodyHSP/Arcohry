package io.github.phiseecodyhsp.arcstory.model.story;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * 由 JSON 数据驱动的 Arcaea 故事.
 *
 * @author RikkaKawaii0612
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public final class Story {

    static final String DEFAULT_NAME = "Unnamed";

    @JsonProperty
    private String name = DEFAULT_NAME;

    @JsonProperty
    private ResourceLocation partnerAvatarLocation;

    @JsonProperty
    private List<Paragraph> paragraphs = new ArrayList<>();

    public Story() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResourceLocation getPartnerAvatarLocation() {
        return this.partnerAvatarLocation;
    }

    public void setPartnerAvatarLocation(ResourceLocation location) {
        this.partnerAvatarLocation = location;
    }

    public List<Paragraph> getParagraphs() {
        return this.paragraphs;
    }

    public void setParagraphs(List<Paragraph> paragraphs) {
        this.paragraphs = paragraphs;
    }
}
