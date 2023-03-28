package ch.zuehlke.common;

import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static ch.zuehlke.common.CardValue.*;
import static ch.zuehlke.common.Suit.HEART;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class CardTest {

    private final Card card = new Card(HEART, TWO);

    @Test
    void getPoints() {
        assertThat(card.getPoints()).isEqualTo(2);
    }

    @Test
    void getOrder() {
        assertThat(card.getOrder()).isEqualTo(2);
    }

    @Test
    void hasSameValueAs() {
        assertThat(card.hasSameValueAs(new Card(HEART, TWO))).isTrue();
        assertThat(card.hasSameValueAs(new Card(HEART, ACE))).isFalse();
    }

    @Test
    void isNeighbourOf() {
        assertThat(card.isNeighbourOf(new Card(HEART, ACE))).isTrue();
        assertThat(card.isNeighbourOf(new Card(HEART, THREE))).isTrue();
        assertThat(card.isNeighbourOf(new Card(HEART, FOUR))).isFalse();
    }

    @Test
    void getCardsWithSameValue() {
        AssertionsForInterfaceTypes.assertThat(card.getCardsWithSameValue(Set.of(new Card(HEART, TWO)))).hasSize(1);
        AssertionsForInterfaceTypes.assertThat(card.getCardsWithSameValue(Set.of(new Card(HEART, ACE)))).hasSize(0);
    }

    @Test
    void getAllCards() {
        AssertionsForInterfaceTypes.assertThat(Card.getAllCards()).hasSize(52);
    }
}