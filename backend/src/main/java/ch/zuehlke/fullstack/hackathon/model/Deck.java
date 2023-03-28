package ch.zuehlke.fullstack.hackathon.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static ch.zuehlke.fullstack.hackathon.model.CardValue.*;
import static ch.zuehlke.fullstack.hackathon.model.Color.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Deck {

    private static final List<Color> COLORS = List.of(SPADE, HEART, CLUBS, DIAMOND);
    private static final List<CardValue> VALUES = List.of(ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING);

    public static List<Card> generateNewDeck() {
        final var deck = new ArrayList<Card>();
        for (Color color : COLORS) {
            for(CardValue value : VALUES){
                deck.add(new Card(color, value));
            }
        }
        return deck;
    }
}
