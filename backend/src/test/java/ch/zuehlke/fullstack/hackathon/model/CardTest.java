package ch.zuehlke.fullstack.hackathon.model;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class CardTest {

    private final Card card = new Card(Suit.HEART, CardValue.TWO);

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
        assertThat(card.hasSameValueAs(new Card(Suit.HEART, CardValue.TWO))).isTrue();
        assertThat(card.hasSameValueAs(new Card(Suit.HEART, CardValue.ACE))).isFalse();
    }

    @Test
    void isNeighbourOf() {
        assertThat(card.isNeighbourOf(new Card(Suit.HEART, CardValue.ACE))).isTrue();
        assertThat(card.isNeighbourOf(new Card(Suit.HEART, CardValue.THREE))).isTrue();
        assertThat(card.isNeighbourOf(new Card(Suit.HEART, CardValue.FOUR))).isFalse();
    }

    @Test
    void getCardsWithSameValue() {
        assertThat(card.getCardsWithSameValue(Set.of(new Card(Suit.HEART, CardValue.TWO)))).hasSize(1);
        assertThat(card.getCardsWithSameValue(Set.of(new Card(Suit.HEART, CardValue.ACE)))).hasSize(0);
    }

    @Test
    void getAllCards() {
        assertThat(Card.getAllCards()).hasSize(52);
    }
}