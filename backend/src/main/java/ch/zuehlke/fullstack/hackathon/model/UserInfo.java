package ch.zuehlke.fullstack.hackathon.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfo {
    private final String firstName;
    private final String lastName;
    private final String id;
    private final long pictureId;
    private final String jobTitle;

    @JsonCreator
    public UserInfo(@JsonProperty("FirstName") String firstName,
                    @JsonProperty("LastName") String lastName,
                    @JsonProperty("Code") String id,
                    @JsonProperty("PictureId") long pictureId,
                    @JsonProperty("Title") String jobTitle) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.pictureId = pictureId;
        this.jobTitle = jobTitle;
    }

    @JsonProperty("firstName")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("lastName")
    public String getLastName() {
        return lastName;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("pictureId")
    public long getPictureId() {
        return pictureId;
    }

    @JsonProperty("jobTitle")
    public String getJobTitle() {
        return jobTitle;
    }
}