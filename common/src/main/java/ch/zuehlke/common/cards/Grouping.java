package ch.zuehlke.common.cards;

import ch.zuehlke.common.GroupType;
import lombok.NonNull;

import java.util.*;
import java.util.stream.Collectors;

public record Grouping(Set<Group> groups) {

    public static Grouping group(Set<Card> cards) {
        var allPossibleNonSingleGroups = getAllPossibleNonSingleGroups(cards);
        var allGroupings = findAllGroupings(cards, allPossibleNonSingleGroups, new HashSet<>());
        return allGroupings.stream()
                .min(Comparator.comparingInt(Grouping::getNumberOfPoints))
                .orElseThrow();
    }

    // recursive method
    private static @NonNull Set<Grouping> findAllGroupings(Set<Card> remainingCards, Set<Group> remainingGroups, Set<Group> alreadyPickedGroups) {
        if (remainingCards.isEmpty()) {
            return Set.of(new Grouping(alreadyPickedGroups));
        }

        var remainingGroupsWhichAreStillPossibleWithRemainingCards = remainingGroups.stream()
                .filter(group -> remainingCards.containsAll(group.cards()))
                .toList();

        // all cards must be in exactly one group, any cards that are not in a group are single cards
        if (remainingGroupsWhichAreStillPossibleWithRemainingCards.isEmpty()) {
            var singleGroup = new Group(remainingCards);
            alreadyPickedGroups.add(singleGroup);
            return Set.of(new Grouping(alreadyPickedGroups));
        }

        // find all grouping, doing recursion for each possible group
        var groupings = new HashSet<Grouping>();
        for (var group : remainingGroupsWhichAreStillPossibleWithRemainingCards) {
            Set<Group> newAlreadyPickedGroups = new HashSet<>(alreadyPickedGroups);
            newAlreadyPickedGroups.add(group);
            Set<Group> newRemainingGroups = new HashSet<>(remainingGroups);
            newRemainingGroups.remove(group);
            Set<Card> newRemainingCards = new HashSet<>(remainingCards);
            newRemainingCards.removeAll(group.cards());
            groupings.addAll(findAllGroupings(newRemainingCards, newRemainingGroups, newAlreadyPickedGroups));
        }

        return groupings;
    }

    public static Set<Group> getAllPossibleNonSingleGroups(Set<Card> cards) {
        Set<Group> possibleGroups = new HashSet<>();
        possibleGroups.addAll(getAllPossibleMatchingGroups(cards));
        possibleGroups.addAll(getAllPossibleSequenceGroups(cards));

        return possibleGroups;
    }

    public static Set<Group> getAllPossibleSequenceGroups(Set<Card> cards) {
        var sortedCards = cards.stream()
                .sorted(Comparator.comparingInt(Card::getOrder))
                .toList();
        List<List<Card>> allRuns = new ArrayList<>();

        for (var card : sortedCards) {
            List<List<Card>> newRuns = new ArrayList<>();
            for (var run : allRuns) {
                if (run.get(run.size() - 1).isNeighbourOf(card)) {
                    List<Card> newRun = new ArrayList<>(run);
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

    public static Set<Group> getAllPossibleMatchingGroups(Set<Card> cards) {
        Set<Group> possibleGroups = new HashSet<>();
        possibleGroups.addAll(getAllPossibleTripletGroups(cards));
        possibleGroups.addAll(getAllPossibleQuartetGroups(cards));
        return possibleGroups;
    }

    public static Set<Group> getAllPossibleTripletGroups(Set<Card> cards) {
        var tripletGroups = cards.stream()
                .map(card -> card.getCardsWithSameRank(cards))
                .filter(set -> set.size() == 3)
                .map(Group::new)
                .collect(Collectors.toSet());
        for (var quartetGroup : getAllPossibleQuartetGroups(cards)) {
            for (var card : quartetGroup.cards()) {
                Set<Card> tripletCards = new HashSet<>(quartetGroup.cards());
                tripletCards.remove(card);
                tripletGroups.add(new Group(tripletCards));
            }
        }
        return tripletGroups;
    }

    public static Set<Group> getAllPossibleQuartetGroups(Set<Card> cards) {
        return cards.stream()
                .map(card -> card.getCardsWithSameRank(cards))
                .filter(set -> set.size() == 4)
                .map(Group::new)
                .collect(Collectors.toSet());
    }

    public boolean isWinningHand() {
        var singleGroup = groups.stream().filter(group -> group.type() == GroupType.SINGLE).findFirst();
        if (singleGroup.isPresent() && singleGroup.get().cards().size() == 1 && singleGroup.get().cards().stream().findFirst().get().getPoints() < 6) {
            return true;
        } else return getNumberOfPoints() == -10 || getNumberOfPoints() == 0;
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