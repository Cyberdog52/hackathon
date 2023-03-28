package ch.zuehlke.fullstack.hackathon.model;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public record Card(Suit suit, CardValue value) {


    public int getPoints() {
        return value.getValue();
    }

    public int getOrder() {
        return value.getOrder();
    }

    public boolean hasSameValueAs(Card other) {
        return value == other.value;
    }

    public boolean isNeighbourOf(Card other) {
        return Math.abs(value.getOrder() - other.value.getOrder()) == 1;
    }

    public static Set<Card> getAllCards() {
        return Arrays.stream(CardValue.values())
                .flatMap(value -> Arrays.stream(Suit.values())
                        .map(suit -> new Card(suit, value)))
                .collect(Collectors.toSet());
    }

    public Set<Card> getCardsWithSameValue(Set<Card> cards) {
        return cards.stream()
                .filter(card -> card.value() == value)
                .collect(Collectors.toSet());
    }

}
