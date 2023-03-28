package ch.zuehlke.common.cards;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents a card with a suit and a rank
 *
 * @param suit Color/Symbol of the card, diamonds, hearts, spades or clubs
 * @param rank Value of the card, Ace, 2, 3, 4, 5, 6, 7, 8, 9, 10, Jack, Queen or King
 */
public record Card(Suit suit, Rank rank) {

    public static Set<Card> getAllCards() {
        return Arrays.stream(Rank.values())
                .flatMap(rank -> Arrays.stream(Suit.values())
                        .map(suit -> new Card(suit, rank)))
                .collect(Collectors.toSet());
    }

    public int getPoints() {
        return rank.getValue();
    }

    public int getOrder() {
        return rank.getOrder();
    }

    public boolean hasSameRankAs(Card other) {
        return rank == other.rank;
    }

    public boolean isNeighbourOf(Card other) {
        return Math.abs(rank.getOrder() - other.rank.getOrder()) == 1;
    }

    public Set<Card> getCardsWithSameRank(Set<Card> cards) {
        return cards.stream()
                .filter(card -> card.rank() == rank)
                .collect(Collectors.toSet());
    }

    public boolean hasSameSuitAs(Card next) {
        return suit == next.suit;
    }
}
