//package ch.zuehlke.common;
//
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//
//import java.util.stream.Stream;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class GameActionTest {
//
//
//    @ParameterizedTest
//    @MethodSource("provideRockPaperScissorsInputs")
//    void beats(GameAction firstAction, GameAction secondAction, boolean expected) {
//        boolean actual = firstAction.beats(secondAction);
//        assertThat(actual).isEqualTo(expected);
//    }
//
//    private static Stream<Arguments> provideRockPaperScissorsInputs() {
//        return Stream.of(
//                Arguments.of(new GameAction(new Coordinates(0, 3), new Coordinates(0, 0)), new GameAction(new Coordinates(0, 3), new Coordinates(0, 0)), false),
//                Arguments.of(new GameAction(new Coordinates(0, 3), new Coordinates(0, 0)), GameAction.PAPER, false),
//                Arguments.of(new GameAction(new Coordinates(0, 3), new Coordinates(0, 0)), GameAction.SCISSORS, true),
//                Arguments.of(GameAction.PAPER, new GameAction(new Coordinates(0, 3), new Coordinates(0, 0)), true),
//                Arguments.of(GameAction.PAPER, GameAction.PAPER, false),
//                Arguments.of(GameAction.PAPER, GameAction.SCISSORS, false),
//                Arguments.of(GameAction.SCISSORS, new GameAction(new Coordinates(0, 3), new Coordinates(0, 0)), false),
//                Arguments.of(GameAction.SCISSORS, GameAction.PAPER, true),
//                Arguments.of(GameAction.SCISSORS, GameAction.SCISSORS, false)
//        );
//    }
//}