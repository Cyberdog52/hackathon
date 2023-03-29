package ch.zuehlke.fullstack.hackathon.model;

import ch.zuehlke.common.Player;
import ch.zuehlke.common.cards.Card;
import lombok.Builder;

import java.util.Collection;

@Builder
public record PlayerHand(Player player, Collection<Card> cards) {
}
