package ch.zuehlke.fullstack.hackathon.model;

import java.util.UUID;

public record PlayerId(String value) {
    public PlayerId() {
        this(UUID.randomUUID().toString());
    }
}
