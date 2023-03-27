package ch.zuehlke.common;

import java.util.UUID;

public record PlayerToken(String value) {

    public PlayerToken() {
        this(UUID.randomUUID().toString());
    }
}
