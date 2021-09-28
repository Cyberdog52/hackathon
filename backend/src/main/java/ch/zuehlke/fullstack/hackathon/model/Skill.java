package ch.zuehlke.fullstack.hackathon.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Skill {
    private final long skillId;
    private final String description;
    private final String name;
    // todo: find out how to map this
    private final long pictureId = 1;
    // todo: calculate rank via trainings
    private int rank;

    @JsonCreator
    public Skill(@JsonProperty("Id") long skillId,
                 @JsonProperty("ShortDescription") String description,
                 @JsonProperty("Name") String name) {
        this.skillId = skillId;
        this.description = description;
        this.name = name;
    }

    @JsonProperty("skillId")
    public long getSkillId() {
        return skillId;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("pictureId")
    public long getPictureId() {
        return pictureId;
    }

    @JsonProperty("rank")
    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}