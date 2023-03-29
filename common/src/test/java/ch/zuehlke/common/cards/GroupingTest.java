package ch.zuehlke.common.cards;

import ch.zuehlke.common.GroupType;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static ch.zuehlke.common.cards.Rank.*;
import static ch.zuehlke.common.cards.Suit.*;
import static org.assertj.core.api.Assertions.assertThat;

class GroupingTest {

    @Test
    void group_threeMatchingCards_intoATriplet() {
        var cards = Set.of(
                new Card(CLUBS, JACK),
                new Card(HEART, JACK),
                new Card(SPADE, JACK));

        var grouping = Grouping.group(cards);

        assertThat(grouping.groups()).hasSize(1);
        assertThat(grouping.groups().iterator().next().type()).isEqualTo(GroupType.TRIPLET);
    }

    @Test
    void group_fourMatchingCards_intoAQuartet() {
        var cards = Set.of(
                new Card(CLUBS, JACK),
                new Card(HEART, JACK),
                new Card(SPADE, JACK),
                new Card(DIAMOND, JACK));

        var grouping = Grouping.group(cards);

        assertThat(grouping.groups()).hasSize(1);
        assertThat(grouping.groups().iterator().next().type()).isEqualTo(GroupType.QUARTET);
    }

    @Test
    void getAllPossibleNonSingleGroups_withThreeCardsNeededForSequence_createsFourGroups() {
        var cards = Set.of(
                new Card(CLUBS, JACK),
                new Card(HEART, JACK),
                new Card(DIAMOND, JACK),
                new Card(SPADE, QUEEN),
                new Card(DIAMOND, KING)
        );

        var groups = Grouping.getAllPossibleNonSingleGroups(cards);

        assertThat(groups).hasSize(4);
        assertThat(groups).containsOnly(
                new Group(Set.of(new Card(CLUBS, JACK), new Card(HEART, JACK), new Card(DIAMOND, JACK))),
                new Group(Set.of(new Card(CLUBS, JACK), new Card(DIAMOND, KING), new Card(SPADE, QUEEN))),
                new Group(Set.of(new Card(DIAMOND, JACK), new Card(DIAMOND, KING), new Card(SPADE, QUEEN))),
                new Group(Set.of(new Card(HEART, JACK), new Card(DIAMOND, KING), new Card(SPADE, QUEEN)))
        );
    }

    @Test
    void getAllPossibleMatchingGroups_withFourIdenticalCards_createsFiveGroups() {
        var cards = Set.of(
                new Card(CLUBS, JACK),
                new Card(HEART, JACK),
                new Card(DIAMOND, JACK),
                new Card(SPADE, JACK)
        );

        var groups = Grouping.getAllPossibleMatchingGroups(cards);

        assertThat(groups).containsExactlyInAnyOrder(
                new Group(Set.of(new Card(CLUBS, JACK), new Card(HEART, JACK), new Card(DIAMOND, JACK))),
                new Group(Set.of(new Card(CLUBS, JACK), new Card(HEART, JACK), new Card(SPADE, JACK))),
                new Group(Set.of(new Card(CLUBS, JACK), new Card(DIAMOND, JACK), new Card(SPADE, JACK))),
                new Group(Set.of(new Card(HEART, JACK), new Card(DIAMOND, JACK), new Card(SPADE, JACK))),
                new Group(Set.of(new Card(CLUBS, JACK), new Card(HEART, JACK), new Card(DIAMOND, JACK), new Card(SPADE, JACK)))
        );
    }

    @Test
    void group_withARemainingEight_intoGroupingWithEightPoints() {
        var cards = Set.of(
                new Card(CLUBS, JACK),
                new Card(HEART, JACK),
                new Card(DIAMOND, JACK),
                new Card(SPADE, JACK),
                new Card(SPADE, QUEEN),
                new Card(SPADE, KING),
                new Card(HEART, EIGHT)
        );

        var grouping = Grouping.group(cards);

        assertThat(grouping.getNumberOfPoints()).isEqualTo(8);
        assertThat(grouping.groups()).hasSize(3);
        assertThat(grouping.groups()).anyMatch(group -> group.type() == GroupType.TRIPLET);
        assertThat(grouping.groups()).anyMatch(group -> group.type() == GroupType.SEQUENCE);
        assertThat(grouping.groups()).anyMatch(group -> group.type() == GroupType.SINGLE);
    }

    @Test
    void group_chinchon_intoGroupingWithMinusTenPoints() {
        var cards = Set.of(
                new Card(CLUBS, EIGHT),
                new Card(CLUBS, NINE),
                new Card(CLUBS, TEN),
                new Card(CLUBS, JACK),
                new Card(CLUBS, QUEEN),
                new Card(CLUBS, KING),
                new Card(CLUBS, SEVEN)
        );

        var grouping = Grouping.group(cards);

        assertThat(grouping.getNumberOfPoints()).isEqualTo(-10);
        assertThat(grouping.groups()).hasSize(1);
        assertThat(grouping.groups()).allMatch(group -> group.type() == GroupType.SEQUENCE);
    }
}