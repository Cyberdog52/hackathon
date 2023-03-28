package ch.zuehlke.fullstack.hackathon.model;

import java.util.UUID;

public record Player(UUID id, String name, String icon) {
}
