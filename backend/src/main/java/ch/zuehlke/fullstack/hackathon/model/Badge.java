package ch.zuehlke.fullstack.hackathon.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Badge {
    private final int id;
    private final String description;

    @JsonCreator
    public Badge(@JsonProperty("id") int id,
                 @JsonProperty("description") String description) {
        this.id = id;
        this.description = description;
    }

    @JsonProperty
    public int getId() {
        return id;
    }

    @JsonProperty
    public String getDescription() {
        return description;
    }
}