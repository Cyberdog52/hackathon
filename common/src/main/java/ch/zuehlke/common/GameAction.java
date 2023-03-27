package ch.zuehlke.common;


public record GameAction(int x, int y) {

    public void execute(Board board) {
        if (hitsShip(board)) {
            board.hits[x][y] = true;
        } else {
            board.misses[x][y] = true;
        }
    }

    private boolean hitsShip(Board board) {
        return board.ships.stream().anyMatch(ship -> ship.hits(x, y));
    }
}
