package ch.zuehlke.fullstack.hackathon.service;

import ch.zuehlke.fullstack.hackathon.model.Card;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static ch.zuehlke.fullstack.hackathon.model.CardValue.*;
import static ch.zuehlke.fullstack.hackathon.model.Color.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RuleEngineTest {

    public static Stream<Arguments> winningHands() {
        return Stream.of(
                Arguments.of(List.of(new Card(CLUBS, ACE), new Card(SPADE, ACE), new Card(DIAMOND, ACE), new Card(HEART, EIGHT), new Card(HEART, NINE), new Card(HEART, TEN), new Card(HEART, FIVE)), 5),       // ♣A ♠A ♦A     |  ♥8 ♥9 ♥10    | ♥5 -> Score 5
                Arguments.of(List.of(new Card(CLUBS, ACE), new Card(SPADE, ACE), new Card(DIAMOND, ACE), new Card(HEART, EIGHT), new Card(HEART, NINE), new Card(HEART, TEN), new Card(HEART, JACK)), 0),       // ♣A ♠A ♦A     |  ♥8 ♥9 ♥10 ♥J |    -> Score 0
                Arguments.of(List.of(new Card(CLUBS, ACE), new Card(SPADE, ACE), new Card(DIAMOND, ACE), new Card(HEART, ACE), new Card(HEART, NINE), new Card(HEART, TEN), new Card(HEART, JACK)), 0),         // ♣A ♠A ♦A ♥A  |  ♥9 ♥10 ♥J    |    -> Score 0
                Arguments.of(List.of(new Card(CLUBS, ACE), new Card(SPADE, ACE), new Card(DIAMOND, ACE), new Card(DIAMOND, TWO), new Card(DIAMOND, THREE), new Card(DIAMOND, FOUR), new Card(HEART, FIVE)), 5), // ♣A ♠A ♦A     | ♦2 ♦3 ♦4      | ♥5 -> Score 5 (Ambiguous ♦A)
                Arguments.of(List.of(new Card(CLUBS, ACE), new Card(SPADE, ACE), new Card(DIAMOND, ACE), new Card(HEART, ACE), new Card(HEART, TWO), new Card(HEART, THREE), new Card(HEART, FIVE)), 5),        // ♣A ♠A ♦A     | ♥A ♥2 ♥3      | ♥5 -> Score 5 (Ambiguous ♦A)
                Arguments.of(List.of(new Card(CLUBS, ACE), new Card(CLUBS, TWO), new Card(CLUBS, THREE), new Card(CLUBS, FOUR), new Card(CLUBS, FIVE), new Card(CLUBS, SIX), new Card(CLUBS, SEVEN)), -10)      // ♣A ♣2 ♣3 ♣4 ♣5 ♣6 ♣7         | Chinchon -> Score -10
        );
    }

    public static Stream<Arguments> losingHands() {
        return Stream.of(
                Arguments.of(List.of(new Card(CLUBS, ACE), new Card(SPADE, ACE), new Card(DIAMOND, ACE), new Card(HEART, EIGHT), new Card(HEART, NINE), new Card(HEART, TEN), new Card(HEART, SIX))),       // ♣A ♠A ♦A   |  ♥8 ♥9 ♥10 | ♥6 -> Too high
                Arguments.of(List.of(new Card(HEART, FIVE), new Card(DIAMOND, FIVE), new Card(CLUBS, FIVE), new Card(SPADE, FIVE), new Card(CLUBS, ACE), new Card(SPADE, ACE), new Card(SPADE, TWO)))       //♣5 ♠5 ♦5 ♥5 | ♣A ♠A ♠2   | Score of 4, but not winning
                );
    }

    @ParameterizedTest
    @MethodSource("winningHands")
    void calculateScore(List<Card> cards, int score) {
        var result = RuleEngine.calculateScore(cards);

        assertThat(result).isEqualTo(score);
    }
}