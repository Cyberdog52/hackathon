package ch.zuehlke.common;

import java.util.UUID;

public record RequestId(String value) {

    public RequestId() {
        this(UUID.randomUUID().toString());
    }
}
