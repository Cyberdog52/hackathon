package ch.zuehlke.tablut;

import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public record Board(Field[][] fields) {

    public static Board createInitialBoard() {
        var fields = new Field[9][9];

        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                if (x == 4 && y == 4) {
                    fields[x][y] = new Field.CastleField(new Coordinates(x, y), CastleFieldState.OCCUPIED);
                } else {
                    fields[x][y] = new Field.NormalField(new Coordinates(x, y), NormalFieldState.EMPTY);
                }
            }
        }
        var board = new Board(fields);

        board.updateField(new Field.NormalField(new Coordinates(3, 0), NormalFieldState.ATTACKER));
        board.updateField(new Field.NormalField(new Coordinates(4, 0), NormalFieldState.ATTACKER));
        board.updateField(new Field.NormalField(new Coordinates(5, 0), NormalFieldState.ATTACKER));
        board.updateField(new Field.NormalField(new Coordinates(4, 1), NormalFieldState.ATTACKER));

        board.updateField(new Field.NormalField(new Coordinates(0, 3), NormalFieldState.ATTACKER));
        board.updateField(new Field.NormalField(new Coordinates(0, 4), NormalFieldState.ATTACKER));
        board.updateField(new Field.NormalField(new Coordinates(0, 5), NormalFieldState.ATTACKER));
        board.updateField(new Field.NormalField(new Coordinates(1, 4), NormalFieldState.ATTACKER));

        board.updateField(new Field.NormalField(new Coordinates(3, 8), NormalFieldState.ATTACKER));
        board.updateField(new Field.NormalField(new Coordinates(4, 8), NormalFieldState.ATTACKER));
        board.updateField(new Field.NormalField(new Coordinates(5, 8), NormalFieldState.ATTACKER));
        board.updateField(new Field.NormalField(new Coordinates(4, 7), NormalFieldState.ATTACKER));

        board.updateField(new Field.NormalField(new Coordinates(8, 3), NormalFieldState.ATTACKER));
        board.updateField(new Field.NormalField(new Coordinates(8, 4), NormalFieldState.ATTACKER));
        board.updateField(new Field.NormalField(new Coordinates(8, 5), NormalFieldState.ATTACKER));
        board.updateField(new Field.NormalField(new Coordinates(7, 4), NormalFieldState.ATTACKER));

        board.updateField(new Field.NormalField(new Coordinates(4, 2), NormalFieldState.DEFENDER));
        board.updateField(new Field.NormalField(new Coordinates(4, 3), NormalFieldState.DEFENDER));
        board.updateField(new Field.NormalField(new Coordinates(2, 4), NormalFieldState.DEFENDER));
        board.updateField(new Field.NormalField(new Coordinates(3, 4), NormalFieldState.DEFENDER));
        board.updateField(new Field.NormalField(new Coordinates(4, 5), NormalFieldState.DEFENDER));
        board.updateField(new Field.NormalField(new Coordinates(4, 6), NormalFieldState.DEFENDER));
        board.updateField(new Field.NormalField(new Coordinates(5, 4), NormalFieldState.DEFENDER));
        board.updateField(new Field.NormalField(new Coordinates(6, 4), NormalFieldState.DEFENDER));

        return board;
    }

    public void updateField(Field field) {
        fields[field.coordinates.x()][field.coordinates.y()] = field;
    }

    public Field getField(Coordinates coordinates) {
        return fields[coordinates.x()][coordinates.y()];
    }

    public List<Field> getFields() {
        var fieldsList = new ArrayList<Field>();
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                fieldsList.add(fields[x][y]);
            }
        }
        return fieldsList;
    }
}