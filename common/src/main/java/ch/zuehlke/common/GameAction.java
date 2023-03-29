package ch.zuehlke.common;

import java.util.Optional;

public record GameAction(Coordinates from, Coordinates to) {
    public GameAction(int fromX, int fromY, int toX, int toY) {
        this(new Coordinates(fromX, fromY), new Coordinates(toX, toY));
    }

    public boolean movesNextToTheKing(Board board) {
        Optional<Coordinates> kingPosition = board.getKingPosition();
        if (kingPosition.isEmpty()) {
            return false;
        }
        return kingPosition.get().down().map(to::equals).orElse(false) ||
                kingPosition.get().up().map(to::equals).orElse(false) ||
                kingPosition.get().left().map(to::equals).orElse(false) ||
                kingPosition.get().right().map(to::equals).orElse(false);
    }

    public boolean isAWinningDefendingMove(Board board) {
        return isKingMove(board) && movesToSideOfBoard();
    }

    public boolean isKingMove(Board board) {
        Optional<Coordinates> kingPosition = board.getKingPosition();
        return kingPosition.filter(from::equals).isPresent();
    }

    public boolean movesToSideOfBoard() {
        return to.x() == 0 || to.x() == Board.SIZE-1 ||
                to.y() == 0 || to.y() == Board.SIZE-1;
    }
}
