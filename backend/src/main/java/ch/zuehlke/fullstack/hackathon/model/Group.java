package ch.zuehlke.fullstack.hackathon.model;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public record Group(Set<Card> cards, GroupType type) {

    public Group(Set<Card> cards) {
        this(cards, getGroupType(cards));
    }

    public Group(List<Card> cards) {
        this(Set.copyOf(cards));
    }

    private static GroupType getGroupType(Set<Card> cards) {
        if (cards.size() == 4 && haveAllTheSameValue(cards)) {
            return GroupType.QUARTET;
        }
        if (cards.size() == 3 && haveAllTheSameValue(cards)) {
            return GroupType.TRIPLET;
        }
        if (cards.size() > 2 && allCardsAreNeighbours(cards) && allCardsHaveSameSuit(cards)) {
            return GroupType.SEQUENCE;
        }
        return GroupType.SINGLE;
    }

    private static boolean allCardsHaveSameSuit(Set<Card> cards) {
        return cards.stream().allMatch(card -> card.hasSameSuitAs(cards.iterator().next()));
    }

    private static boolean allCardsAreNeighbours(Set<Card> cards) {
        var sortedCards = cards.stream()
                .sorted(Comparator.comparingInt(Card::getOrder))
                .toList();

        for (var i = 0; i < sortedCards.size() - 1; i++) {
            if (!sortedCards.get(i).isNeighbourOf(sortedCards.get(i + 1))) {
                return false;
            }
        }

        return true;
    }

    private static boolean haveAllTheSameValue(Set<Card> cards) {
        return cards.stream().allMatch(card -> card.hasSameValueAs(cards.iterator().next()));
    }

    public int getNumberOfPoints() {
        if (type() != GroupType.SINGLE) {
            return 0;
        }

        return cards.stream()
                .mapToInt(Card::getPoints)
                .sum();
    }


}
