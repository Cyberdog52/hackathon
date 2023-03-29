package ch.zuehlke.common;


import ch.zuehlke.common.cards.Card;

import java.util.Collection;

public record Deal(Collection<Card> cards) {
}
