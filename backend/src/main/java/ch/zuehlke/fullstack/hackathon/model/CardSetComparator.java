package ch.zuehlke.fullstack.hackathon.model;

import java.util.Comparator;

/**
 * A Set consists of 3 or 4 cards of the same Rank
 * Example: ♣A ♠A ♦A
 */
public class CardSetComparator implements Comparator<Card> {
    @Override
    public int compare(Card card1, Card card2) {
        return Integer.compare(card1.getValue(), card2.getValue());
    }
}
