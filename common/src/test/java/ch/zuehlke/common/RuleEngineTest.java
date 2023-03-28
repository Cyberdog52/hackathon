package ch.zuehlke.common;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static ch.zuehlke.common.CardValue.*;
import static ch.zuehlke.common.Suit.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RuleEngineTest {

    public static Stream<Arguments> winningHands() {
        return Stream.of(
                Arguments.of(Set.of(new Card(CLUBS, ACE), new Card(SPADE, ACE), new Card(DIAMOND, ACE), new Card(HEART, EIGHT), new Card(HEART, NINE), new Card(HEART, TEN), new Card(HEART, FIVE)), true),       // ♣A ♠A ♦A     |  ♥8 ♥9 ♥10    | ♥5 -> Score 5
                Arguments.of(Set.of(new Card(CLUBS, ACE), new Card(SPADE, ACE), new Card(DIAMOND, ACE), new Card(HEART, EIGHT), new Card(HEART, NINE), new Card(HEART, TEN), new Card(HEART, JACK)), true),       // ♣A ♠A ♦A     |  ♥8 ♥9 ♥10 ♥J |    -> Score 0
                Arguments.of(Set.of(new Card(CLUBS, ACE), new Card(SPADE, ACE), new Card(DIAMOND, ACE), new Card(HEART, ACE), new Card(HEART, NINE), new Card(HEART, TEN), new Card(HEART, JACK)), true),         // ♣A ♠A ♦A ♥A  |  ♥9 ♥10 ♥J    |    -> Score 0
                Arguments.of(Set.of(new Card(CLUBS, ACE), new Card(SPADE, ACE), new Card(DIAMOND, ACE), new Card(DIAMOND, TWO), new Card(DIAMOND, THREE), new Card(DIAMOND, FOUR), new Card(HEART, FIVE)), true), // ♣A ♠A ♦A     | ♦2 ♦3 ♦4      | ♥5 -> Score 5 (Ambiguous ♦A)
                Arguments.of(Set.of(new Card(CLUBS, ACE), new Card(SPADE, ACE), new Card(DIAMOND, ACE), new Card(HEART, ACE), new Card(HEART, TWO), new Card(HEART, THREE), new Card(HEART, FIVE)), true),        // ♣A ♠A ♦A     | ♥A ♥2 ♥3      | ♥5 -> Score 5 (Ambiguous ♦A)
                Arguments.of(Set.of(new Card(CLUBS, ACE), new Card(CLUBS, TWO), new Card(CLUBS, THREE), new Card(CLUBS, FOUR), new Card(CLUBS, FIVE), new Card(CLUBS, SIX), new Card(CLUBS, SEVEN)), true)      // ♣A ♣2 ♣3 ♣4 ♣5 ♣6 ♣7         | Chinchon -> Score -10
        );
    }

    public static Stream<Arguments> losingHands() {
        return Stream.of(
                Arguments.of(Set.of(new Card(CLUBS, ACE), new Card(SPADE, ACE), new Card(DIAMOND, ACE), new Card(HEART, EIGHT), new Card(HEART, NINE), new Card(HEART, TEN), new Card(HEART, SIX)), false),        // ♣A ♠A ♦A   |  ♥8 ♥9 ♥10 | ♥6 -> Too high
                Arguments.of(Set.of(new Card(HEART, FIVE), new Card(DIAMOND, FIVE), new Card(CLUBS, FIVE), new Card(SPADE, FIVE), new Card(CLUBS, ACE), new Card(SPADE, ACE), new Card(SPADE, TWO)), false),       //♣5 ♠5 ♦5 ♥5 | ♣A ♠A ♠2   | Score of 4, but not winning
                Arguments.of(Set.of(new Card(CLUBS, SEVEN), new Card(CLUBS, EIGHT), new Card(CLUBS, NINE), new Card(CLUBS, FIVE), new Card(HEART, FOUR), new Card(HEART, FIVE), new Card(SPADE, TWO)), false),     //♣7 ♣8 ♣9 | ♣5 ♥4 ♥5 ♠2 -> Score of 16
                Arguments.of(Set.of(new Card(CLUBS, SEVEN), new Card(SPADE, EIGHT), new Card(CLUBS, NINE), new Card(CLUBS, FIVE), new Card(HEART, FOUR), new Card(HEART, FIVE), new Card(SPADE, TWO)), false)     //♣7 ♠8 ♣9 | ♣5 ♥4 ♥5 ♠2 -> Score of 16 (Not a row)
        );
    }

    public static Stream<Arguments> scoreCalculation() {
        return Stream.of(
                Arguments.of(Set.of(new Card(CLUBS, ACE), new Card(SPADE, ACE), new Card(DIAMOND, ACE), new Card(HEART, EIGHT), new Card(HEART, NINE), new Card(HEART, TEN), new Card(HEART, FIVE)), 5),       // ♣A ♠A ♦A     |  ♥8 ♥9 ♥10    | ♥5 -> Score 5
                Arguments.of(Set.of(new Card(CLUBS, ACE), new Card(SPADE, ACE), new Card(DIAMOND, ACE), new Card(HEART, EIGHT), new Card(HEART, NINE), new Card(HEART, TEN), new Card(HEART, JACK)), 0),       // ♣A ♠A ♦A     |  ♥8 ♥9 ♥10 ♥J |    -> Score 0
                Arguments.of(Set.of(new Card(CLUBS, ACE), new Card(SPADE, ACE), new Card(DIAMOND, ACE), new Card(HEART, ACE), new Card(HEART, NINE), new Card(HEART, TEN), new Card(HEART, JACK)), 0),         // ♣A ♠A ♦A ♥A  |  ♥9 ♥10 ♥J    |    -> Score 0
                Arguments.of(Set.of(new Card(CLUBS, ACE), new Card(SPADE, ACE), new Card(DIAMOND, ACE), new Card(DIAMOND, TWO), new Card(DIAMOND, THREE), new Card(DIAMOND, FOUR), new Card(HEART, FIVE)), 5), // ♣A ♠A ♦A     | ♦2 ♦3 ♦4      | ♥5 -> Score 5 (Ambiguous ♦A)
                Arguments.of(Set.of(new Card(CLUBS, ACE), new Card(SPADE, ACE), new Card(DIAMOND, ACE), new Card(HEART, ACE), new Card(HEART, TWO), new Card(HEART, THREE), new Card(HEART, FIVE)), 5),        // ♣A ♠A ♦A     | ♥A ♥2 ♥3      | ♥5 -> Score 5 (Ambiguous ♦A)
                Arguments.of(Set.of(new Card(CLUBS, ACE), new Card(CLUBS, TWO), new Card(CLUBS, THREE), new Card(CLUBS, FOUR), new Card(CLUBS, FIVE), new Card(CLUBS, SIX), new Card(CLUBS, SEVEN)), -10),
                Arguments.of(Set.of(new Card(CLUBS, ACE), new Card(SPADE, ACE), new Card(DIAMOND, ACE), new Card(HEART, EIGHT), new Card(HEART, NINE), new Card(HEART, TEN), new Card(HEART, SIX)), 6),        // ♣A ♠A ♦A   |  ♥8 ♥9 ♥10 | ♥6 -> Too high
                Arguments.of(Set.of(new Card(HEART, FIVE), new Card(DIAMOND, FIVE), new Card(CLUBS, FIVE), new Card(SPADE, FIVE), new Card(CLUBS, ACE), new Card(SPADE, ACE), new Card(SPADE, TWO)), 4),       //♣5 ♠5 ♦5 ♥5 | ♣A ♠A ♠2   | Score of 4, but not winning
                Arguments.of(Set.of(new Card(CLUBS, SEVEN), new Card(CLUBS, EIGHT), new Card(CLUBS, NINE), new Card(CLUBS, FIVE), new Card(HEART, FOUR), new Card(HEART, FIVE), new Card(SPADE, TWO)), 16),     //♣7 ♣8 ♣9 | ♣5 ♥4 ♥5 ♠2 -> Score of 16
                Arguments.of(Set.of(new Card(CLUBS, SEVEN), new Card(SPADE, EIGHT), new Card(CLUBS, NINE), new Card(CLUBS, FIVE), new Card(HEART, FOUR), new Card(HEART, FIVE), new Card(SPADE, TWO)), 40)     //♣7 ♠8 ♣9 | ♣5 ♥4 ♥5 ♠2 -> Score of 40 (Not a row)
        );
    }

    @ParameterizedTest
    @MethodSource("scoreCalculation")
    void isCorrectScore(Set<Card> cards, int score) {
        var result = RuleEngine.calculateScore(cards);

        assertThat(result).isEqualTo(score);
    }


    @ParameterizedTest
    @MethodSource("winningHands")
    void isWinningHand(Set<Card> cards, boolean hasWon) {
        var result = RuleEngine.isWinning(cards);

        assertThat(result).isEqualTo(hasWon);
    }

    @ParameterizedTest
    @MethodSource("losingHands")
    void isLosingHand(Set<Card> cards, boolean hasWon) {
        var result = RuleEngine.isWinning(cards);

        assertThat(result).isEqualTo(hasWon);
    }
}