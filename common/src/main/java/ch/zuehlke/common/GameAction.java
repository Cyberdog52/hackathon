package ch.zuehlke.common;


import java.util.Optional;

public record GameAction(int x, int y) {

    public void execute(Board board) {
        Optional<Ship> hitShip = hitsShip(board);
        if (hitShip.isPresent()) {
            hitShip.get().hits++;
            if (hitShip.get().type.length == hitShip.get().hits) {
                markShipAsSunk(hitShip.get(), board);
            } else {
                board.shots[x][y] = ShotResult.HIT;
            }
        } else {
            board.shots[x][y] = ShotResult.MISS;
        }
    }

    private Optional<Ship> hitsShip(Board board) {
        return board.ships.stream().filter(ship -> ship.hits(x, y)).findFirst();
    }

    private void markShipAsSunk(Ship ship, Board board) {
        for (int i = 0; i < ship.type.length; i++) {
            if (ship.orientation == Orientation.HORIZONTAL) {
                board.shots[ship.x + i][ship.y] = ShotResult.SUNK;
            } else {
                board.shots[ship.x][ship.y + i] = ShotResult.SUNK;
            }
        }
    }
}
