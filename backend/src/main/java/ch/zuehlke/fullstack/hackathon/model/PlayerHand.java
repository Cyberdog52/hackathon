package ch.zuehlke.fullstack.hackathon.model;

import lombok.Builder;

import java.util.Collection;

@Builder
public record PlayerHand(Player player, Collection<Card> cards) {
}
