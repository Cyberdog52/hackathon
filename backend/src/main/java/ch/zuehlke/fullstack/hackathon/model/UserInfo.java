package ch.zuehlke.fullstack.hackathon.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfo {
    private final String firstName;
    private final String lastName;
    private final String id;
    private final String pictureId;
    private final String jobTitle;

    @JsonCreator
    public UserInfo(@JsonProperty("FirstName") String firstName,
                    @JsonProperty("LastName") String lastName,
                    @JsonProperty("Code") String id,
                    @JsonProperty("PictureId") String pictureId,
                    @JsonProperty("Title") String jobTitle) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.pictureId = pictureId;
        this.jobTitle = jobTitle;
    }

    @JsonProperty
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty
    public String getLastName() {
        return lastName;
    }

    @JsonProperty
    public String getId() {
        return id;
    }

    @JsonProperty
    public String getPictureId() {
        return pictureId;
    }

    @JsonProperty
    public String getJobTitle() {
        return jobTitle;
    }
}