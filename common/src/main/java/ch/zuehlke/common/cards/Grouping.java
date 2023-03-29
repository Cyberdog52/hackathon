package ch.zuehlke.common.cards;

import ch.zuehlke.common.GroupType;
import lombok.NonNull;

import java.util.*;
import java.util.stream.Collectors;

public record Grouping(Set<Group> groups) {

    public static Grouping group(final Set<Card> cards) {
        final var allPossibleNonSingleGroups = getAllPossibleNonSingleGroups(cards);
        final var allGroupings = findAllGroupings(cards, allPossibleNonSingleGroups, new HashSet<>());
        return allGroupings.stream()
                .min(Comparator.comparingInt(Grouping::getNumberOfPoints))
                .orElseThrow();
    }

    // recursive method
    private static @NonNull Set<Grouping> findAllGroupings(final Set<Card> remainingCards, final Set<Group> remainingGroups, final Set<Group> alreadyPickedGroups) {
        if (remainingCards.isEmpty()) {
            return Set.of(new Grouping(alreadyPickedGroups));
        }

        final var remainingGroupsWhichAreStillPossibleWithRemainingCards = remainingGroups.stream()
                .filter(group -> remainingCards.containsAll(group.cards()))
                .toList();

        // all cards must be in exactly one group, any cards that are not in a group are single cards
        if (remainingGroupsWhichAreStillPossibleWithRemainingCards.isEmpty()) {
            final var singleGroup = new Group(remainingCards);
            alreadyPickedGroups.add(singleGroup);
            return Set.of(new Grouping(alreadyPickedGroups));
        }

        // find all grouping, doing recursion for each possible group
        final var groupings = new HashSet<Grouping>();
        for (final var group : remainingGroupsWhichAreStillPossibleWithRemainingCards) {
            final Set<Group> newAlreadyPickedGroups = new HashSet<>(alreadyPickedGroups);
            newAlreadyPickedGroups.add(group);
            final Set<Group> newRemainingGroups = new HashSet<>(remainingGroups);
            newRemainingGroups.remove(group);
            final Set<Card> newRemainingCards = new HashSet<>(remainingCards);
            newRemainingCards.removeAll(group.cards());
            groupings.addAll(findAllGroupings(newRemainingCards, newRemainingGroups, newAlreadyPickedGroups));
        }

        return groupings;
    }

    public static Set<Group> getAllPossibleNonSingleGroups(final Set<Card> cards) {
        final Set<Group> possibleGroups = new HashSet<>();
        possibleGroups.addAll(getAllPossibleMatchingGroups(cards));
        possibleGroups.addAll(getAllPossibleSequenceGroups(cards));

        return possibleGroups;
    }

    public static Set<Group> getAllPossibleSequenceGroups(final Set<Card> cards) {
        final var sortedCards = cards.stream()
                .sorted(Comparator.comparingInt(Card::getOrder))
                .toList();
        final List<List<Card>> allRuns = new ArrayList<>();

        for (final var card : sortedCards) {
            final List<List<Card>> newRuns = new ArrayList<>();
            for (final var run : allRuns) {
                if (run.get(run.size() - 1).isNeighbourOf(card)) {
                    final List<Card> newRun = new ArrayList<>(run);
                    newRun.add(card);
                    newRuns.add(newRun);
                }
            }
            allRuns.addAll(newRuns);
            allRuns.add(List.of(card));
        }
        // create groups from all runs where the run has at least 3 cards
        return allRuns.stream()
                .filter(run -> run.size() >= 3)
                .map(Group::new)
                .collect(Collectors.toSet());
    }

    public static Set<Group> getAllPossibleMatchingGroups(final Set<Card> cards) {
        final Set<Group> possibleGroups = new HashSet<>();
        possibleGroups.addAll(getAllPossibleTripletGroups(cards));
        possibleGroups.addAll(getAllPossibleQuartetGroups(cards));
        return possibleGroups;
    }

    public static Set<Group> getAllPossibleTripletGroups(final Set<Card> cards) {
        final var tripletGroups = cards.stream()
                .map(card -> card.getCardsWithSameRank(cards))
                .filter(set -> set.size() == 3)
                .map(Group::new)
                .collect(Collectors.toSet());
        for (final var quartetGroup : getAllPossibleQuartetGroups(cards)) {
            for (final var card : quartetGroup.cards()) {
                final Set<Card> tripletCards = new HashSet<>(quartetGroup.cards());
                tripletCards.remove(card);
                tripletGroups.add(new Group(tripletCards));
            }
        }
        return tripletGroups;
    }

    public static Set<Group> getAllPossibleQuartetGroups(final Set<Card> cards) {
        return cards.stream()
                .map(card -> card.getCardsWithSameRank(cards))
                .filter(set -> set.size() == 4)
                .map(Group::new)
                .collect(Collectors.toSet());
    }

    public boolean isWinningHand() {
        final var singleGroup = groups.stream().filter(group -> group.type() == GroupType.SINGLE).findFirst();
        if (singleGroup.isPresent() && singleGroup.get().cards().size() == 1 && singleGroup.get().cards().stream().findFirst().get().getValue() < 6) {
            return true;
        } else {
            return getNumberOfPoints() == -10 || getNumberOfPoints() == 0;
        }
    }

    public int getNumberOfPoints() {
        // chinchon scores -10 points if there is only one sequence
        if (groups.size() == 1 && groups.stream().findFirst().get().type() == GroupType.SEQUENCE) {
            return -10;
        }

        return groups.stream()
                .mapToInt(Group::getNumberOfPoints)
                .sum();
    }
}