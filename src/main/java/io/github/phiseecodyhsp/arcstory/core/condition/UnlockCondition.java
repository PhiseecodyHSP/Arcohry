package io.github.phiseecodyhsp.arcstory.core.condition;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UnlockCondition {

    @JsonProperty("type")
    private String type;

    @JsonProperty("chart")
    private String chart;

    @JsonProperty("partner")
    private String partner;

    public UnlockCondition() {}

    public UnlockCondition(String type, String chart, String partner) {
        this.type = type;
        this.chart = chart;
        this.partner = partner;
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getChart() { return chart; }
    public void setChart(String chart) { this.chart = chart; }

    public String getPartner() { return partner; }
    public void setPartner(String partner) { this.partner = partner; }

    public boolean isNone() {
        return type == null || "none".equals(type);
    }
}
