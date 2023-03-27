package ch.zuehlke.common;

import ch.zuehlke.tablut.Board;
import ch.zuehlke.tablut.Coordinates;
import ch.zuehlke.tablut.Field;
import ch.zuehlke.tablut.NormalFieldState;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GameState2 {

    private Board board;

    private List<GameAction> actionHistory;

    private Set<PlayRequest> currentRequests;
    private List<Round> rounds;

    public GameState2() {
        this.board = Board.createInitialBoard();
        this.actionHistory = new ArrayList<>();
        this.currentRequests = new HashSet<>();
        this.rounds = new ArrayList<>();
    }

    public GameState2(Set<PlayRequest> currentRequests, List<Round> rounds) {
        this.board = Board.createInitialBoard();
        this.actionHistory = new ArrayList<>();
        this.currentRequests = currentRequests;
        this.rounds = rounds;
    }

    public Set<PlayRequest> currentRequests() {
        return currentRequests;
    }

    public List<Round> rounds() {
        return rounds;
    }

    //    public GameState(Board board, List<GameAction> actionHistory) {
//        this.board = board;
//        this.actionHistory = actionHistory;
//    }

    public Set<GameAction> getPossibleActions() {
        if (actionHistory.size() % 2 == 0) {
            return getPossibleActionsForAttacker();
        }
        return getPossibleActionsForDefender();
    }

    private Set<GameAction> getPossibleActionsForAttacker() {
        Set<Field.NormalField> fieldsWithAttackers = board.getFields().stream()
                .filter(field -> field instanceof Field.NormalField)
                .map(normalField -> (Field.NormalField) normalField)
                .filter(field -> field.state.equals(NormalFieldState.ATTACKER))
                .collect(Collectors.toSet());

        return fieldsWithAttackers.stream().flatMap(a -> findAvailableActions(a).stream()).collect(Collectors.toSet());
    }

    private Set<GameAction> findAvailableActions(Field field) {

        var result = new HashSet<GameAction>();
        result.addAll(findAvailableActions(field, Coordinates::left));
        result.addAll(findAvailableActions(field, c -> c.right(9)));
        result.addAll(findAvailableActions(field, Coordinates::up));
        result.addAll(findAvailableActions(field, c -> c.down(9)));

        return result;
    }

    private Set<GameAction> findAvailableActions(Field field, Function<Coordinates, Optional<Coordinates>> neighbourGetter) {
        var result = new HashSet<GameAction>();

        var neighbour = neighbourGetter.apply(field.coordinates);
        while (neighbour.isPresent()) {
            var neighbourCoordinates = neighbour.get();
            var neighbourField = board.getField(neighbourCoordinates);
            if (neighbourField instanceof Field.NormalField n && n.state == NormalFieldState.EMPTY) {
                result.add(new GameAction(field.coordinates, neighbourField.coordinates));
            } else {
                break;
            }
            neighbour = neighbourGetter.apply(neighbourCoordinates);
        }
        return result;
    }

    private Set<GameAction> getPossibleActionsForDefender() {
        Set<Field.NormalField> fieldsWithDefenders = board.getFields().stream()
                .filter(field -> field instanceof Field.NormalField)
                .map(normalField -> (Field.NormalField) normalField)
                .filter(field -> field.state.equals(NormalFieldState.DEFENDER))
                .collect(Collectors.toSet());

        return fieldsWithDefenders.stream().flatMap(a -> findAvailableActions(a).stream()).collect(Collectors.toSet());
    }
}
