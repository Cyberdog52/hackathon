package ch.zuehlke.fullstack.hackathon.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Training {
    private final String name;
    private final long pictureId;
    private final List<Skill> skills;
    private final long id;

    @JsonCreator
    public Training(@JsonProperty("Name") String name,
                    @JsonProperty("PictureId") long pictureId,
                    @JsonProperty("Skills") List<Skill> skills,
                    @JsonProperty("Id") long id) {
        this.name = name;
        this.pictureId = pictureId;
        this.skills = skills;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public long getPictureId() {
        return pictureId;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public long getId() {
        return id;
    }
}