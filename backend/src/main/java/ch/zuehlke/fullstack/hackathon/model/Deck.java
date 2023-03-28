package ch.zuehlke.fullstack.hackathon.model;

import ch.zuehlke.common.cards.Card;
import ch.zuehlke.common.cards.Rank;
import ch.zuehlke.common.cards.Suit;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static ch.zuehlke.common.cards.Rank.*;
import static ch.zuehlke.common.cards.Suit.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Deck {

    private static final List<Suit> COLORS = List.of(SPADE, HEART, CLUBS, DIAMOND);
    private static final List<Rank> VALUES = List.of(ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING);

    public static List<Card> generateNewDeck() {
        final var deck = new ArrayList<Card>();
        for (var color : COLORS) {
            for (var value : VALUES) {
                deck.add(new Card(color, value));
            }
        }
        return deck;
    }
}
