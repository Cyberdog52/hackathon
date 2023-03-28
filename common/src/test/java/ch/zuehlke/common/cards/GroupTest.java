package ch.zuehlke.common.cards;

import ch.zuehlke.common.GroupType;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static ch.zuehlke.common.cards.Rank.*;
import static ch.zuehlke.common.cards.Suit.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GroupTest {

    @Test
    void constructGroup_withSingleCard_intoASingleGroup() {
        var card = new Card(CLUBS, JACK);
        var group = new Group(Set.of(card), GroupType.SINGLE);

        assertEquals(GroupType.SINGLE, group.type());
    }

    @Test
    void constructGroup_withTwoCards_intoASingleGroup() {
        var cards = Set.of(new Card(CLUBS, JACK), new Card(HEART, JACK));
        var group = new Group(cards, GroupType.SINGLE);

        assertEquals(GroupType.SINGLE, group.type());
    }

    @Test
    void constructGroup_withThreeDifferentCards_intoASingleGroup() {
        var cards = Set.of(new Card(CLUBS, SIX), new Card(HEART, JACK), new Card(SPADE, ACE));
        var group = new Group(cards, GroupType.SINGLE);

        assertEquals(GroupType.SINGLE, group.type());
    }

    @Test
    void constructGroup_withThreeNeighboringCards_intoASequence() {
        var cards = Set.of(new Card(CLUBS, JACK), new Card(CLUBS, QUEEN), new Card(CLUBS, KING));

        var group = new Group(cards);

        assertEquals(GroupType.SEQUENCE, group.type());
    }

    @Test
    void constructGroup_threeMatchingCards_intoATriplet() {
        var cards = Set.of(new Card(CLUBS, JACK), new Card(HEART, JACK), new Card(SPADE, JACK));

        var group = new Group(cards);

        assertEquals(GroupType.TRIPLET, group.type());
    }

    @Test
    void constructGroup_fourMatchingCards_intoAQuartet() {
        var cards = Set.of(new Card(CLUBS, JACK), new Card(HEART, JACK), new Card(SPADE, JACK), new Card(DIAMOND, JACK));

        var group = new Group(cards);

        assertEquals(GroupType.QUARTET, group.type());
    }


}