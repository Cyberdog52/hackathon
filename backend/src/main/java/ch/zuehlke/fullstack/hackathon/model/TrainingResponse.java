package ch.zuehlke.fullstack.hackathon.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TrainingResponse {
    private final long year;
    private final Training training;

    @JsonCreator
    public TrainingResponse(@JsonProperty("Year") long year,
                            @JsonProperty("Training") Training training) {
        this.year = year;
        this.training = training;
    }

    public long getYear() {
        return year;
    }

    public Training getTraining() {
        return training;
    }
}