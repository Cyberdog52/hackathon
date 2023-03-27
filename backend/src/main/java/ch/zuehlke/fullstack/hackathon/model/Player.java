package ch.zuehlke.fullstack.hackathon.model;

import java.util.Collection;
import java.util.UUID;

public record Player(UUID id, String name, String icon, Collection<Card> cards) {
}
