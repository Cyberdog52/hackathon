package ch.zuehlke.fullstack.hackathon.model;

import ch.zuehlke.common.GameAction;
import ch.zuehlke.common.Board;
import ch.zuehlke.common.Coordinates;
import ch.zuehlke.common.Field;
import ch.zuehlke.common.FieldState;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InternalGameState {

    private final Board board;

    private final List<GameAction> actionHistory;

    public InternalGameState() {
        this.board = Board.createInitialBoard();
        this.actionHistory = new ArrayList<>();
    }

    public List<GameAction> getActionHistory() {
        return actionHistory;
    }

    public boolean attackersTurn() {
        return actionHistory.size() % 2 == 0;
    }

    public boolean defendersTurn() {
        return !attackersTurn();
    }


    public Set<GameAction> getPossibleActions() {
        if (attackersTurn()) {
            return getPossibleActionsForAttacker();
        }
        return getPossibleActionsForDefender();
    }

    private Set<GameAction> getPossibleActionsForAttacker() {
        return board.getAllFieldsAsList().stream()
                .filter(field -> field.state().equals(FieldState.ATTACKER))
                .flatMap(a -> findAvailableActions(a).stream()).collect(Collectors.toSet());
    }

    private Set<GameAction> findAvailableActions(Field field) {

        var result = new HashSet<GameAction>();
        result.addAll(findAvailableActions(field, Coordinates::left));
        result.addAll(findAvailableActions(field, Coordinates::right));
        result.addAll(findAvailableActions(field, Coordinates::up));
        result.addAll(findAvailableActions(field, Coordinates::down));

        return result;
    }

    private Set<GameAction> findAvailableActions(Field field, Function<Coordinates, Optional<Coordinates>> neighbourGetter) {
        var result = new HashSet<GameAction>();

        var neighbour = neighbourGetter.apply(field.coordinates());
        while (neighbour.isPresent()) {
            var neighbourCoordinates = neighbour.get();
            var neighbourField = board.getFieldForCoordinate(neighbourCoordinates);
            if ((neighbourField.state() == FieldState.EMPTY && !neighbourField.isCastle()) ||
                    (field.state() == FieldState.KING && neighbourField.isCastle())) {
                result.add(new GameAction(field.coordinates(), neighbourField.coordinates()));
            } else {
                break;
            }
            neighbour = neighbourGetter.apply(neighbourCoordinates);
        }
        return result;
    }

    private Set<GameAction> getPossibleActionsForDefender() {
        return board.getAllFieldsAsList().stream()
                .filter(field -> field.state() == FieldState.DEFENDER || field.state() == FieldState.KING)
                .flatMap(a -> findAvailableActions(a).stream()).collect(Collectors.toSet());
    }

    public void playAction(GameAction gameAction) {
        getActionHistory().add(gameAction);
        board.movePiece(gameAction.from(), gameAction.to());

        var toField = board.getFieldForCoordinate(gameAction.to());

        checkPinchCapture(toField, Coordinates::left);
        checkPinchCapture(toField, Coordinates::right);
        checkPinchCapture(toField, Coordinates::up);
        checkPinchCapture(toField, Coordinates::down);
    }

    private void checkPinchCapture(Field field, Function<Coordinates, Optional<Coordinates>> neighbourGetter) {
        neighbourGetter.apply(field.coordinates())
                .map(board::getFieldForCoordinate)
                .ifPresent(neighbour -> {
                    if (field.state().isEnemy(neighbour.state())) {
                        neighbourGetter.apply(neighbour.coordinates())
                                .map(board::getFieldForCoordinate)
                                .ifPresent(nextNeighbour -> {
                                    boolean isEmptyCastle = nextNeighbour.isCastle() && nextNeighbour.state() == FieldState.EMPTY;
                                    boolean isEnemy = neighbour.state().isEnemy(nextNeighbour.state());
                                    boolean isKingCapturable = neighbour.state() == FieldState.KING && (neighbour.isCastle() || neighbour.isAdjacentCastle());

                                    if (isKingCapturable) {
                                        long necessaryAttackersPresent = neighbour.getNeighbours().stream()
                                                .map(c -> board.getFieldForCoordinate(c.get()))
                                                .filter(f -> f.state() == FieldState.ATTACKER).count();
                                        boolean necessaryAttackers = necessaryAttackersPresent == 3 && neighbour.isAdjacentCastle()
                                                || necessaryAttackersPresent == 4 && neighbour.isCastle();
                                        if (necessaryAttackers)
                                            board.updateField(new Field(neighbour.coordinates(), FieldState.EMPTY));
                                    } else {
                                        if (isEnemy || isEmptyCastle) {
                                            board.updateField(new Field(neighbour.coordinates(), FieldState.EMPTY));
                                        }
                                    }
                                });
                    }
                });
    }

    public Board board() {
        return board;
    }

    public boolean isGameFinished() {
        return hasDefenderWon() || isDraw() || hasAttackerWon();
    }

    public boolean isDraw() {
        return getActionHistory().size() >= 200;
    }

    public boolean hasDefenderWon() {
        if (attackersTurn() && getPossibleActions().isEmpty()) {
            return true;
        }
        return board.getAllFieldsAsList().stream()
                .filter(a -> a.state() == FieldState.KING)
                .anyMatch(Field::isBorder);
    }

    public boolean hasAttackerWon() {
        if (defendersTurn() && getPossibleActions().isEmpty()) {
            return true;
        }
        return board.getAllFieldsAsList().stream()
                .filter(a -> a.state() == FieldState.KING)
                .findAny()
                .isEmpty();

    }
}
