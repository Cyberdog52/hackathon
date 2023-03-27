package ch.zuehlke.tablut;

import lombok.Builder;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

@Builder
public record Board(Field[][] fields) {

    public static Board createInitialBoard() {
        var fields = new Field[9][9];

        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                fields[x][y] = new Field(new Coordinates(x, y), FieldState.EMPTY);
            }
        }
        var board = new Board(fields);

        board.updateField(new Field(new Coordinates(3, 0), FieldState.ATTACKER));
        board.updateField(new Field(new Coordinates(4, 0), FieldState.ATTACKER));
        board.updateField(new Field(new Coordinates(5, 0), FieldState.ATTACKER));
        board.updateField(new Field(new Coordinates(4, 1), FieldState.ATTACKER));

        board.updateField(new Field(new Coordinates(0, 3), FieldState.ATTACKER));
        board.updateField(new Field(new Coordinates(0, 4), FieldState.ATTACKER));
        board.updateField(new Field(new Coordinates(0, 5), FieldState.ATTACKER));
        board.updateField(new Field(new Coordinates(1, 4), FieldState.ATTACKER));

        board.updateField(new Field(new Coordinates(3, 8), FieldState.ATTACKER));
        board.updateField(new Field(new Coordinates(4, 8), FieldState.ATTACKER));
        board.updateField(new Field(new Coordinates(5, 8), FieldState.ATTACKER));
        board.updateField(new Field(new Coordinates(4, 7), FieldState.ATTACKER));

        board.updateField(new Field(new Coordinates(8, 3), FieldState.ATTACKER));
        board.updateField(new Field(new Coordinates(8, 4), FieldState.ATTACKER));
        board.updateField(new Field(new Coordinates(8, 5), FieldState.ATTACKER));
        board.updateField(new Field(new Coordinates(7, 4), FieldState.ATTACKER));

        board.updateField(new Field(new Coordinates(4, 2), FieldState.DEFENDER));
        board.updateField(new Field(new Coordinates(4, 3), FieldState.DEFENDER));
        board.updateField(new Field(new Coordinates(2, 4), FieldState.DEFENDER));
        board.updateField(new Field(new Coordinates(3, 4), FieldState.DEFENDER));
        board.updateField(new Field(new Coordinates(4, 5), FieldState.DEFENDER));
        board.updateField(new Field(new Coordinates(4, 6), FieldState.DEFENDER));
        board.updateField(new Field(new Coordinates(5, 4), FieldState.DEFENDER));
        board.updateField(new Field(new Coordinates(6, 4), FieldState.DEFENDER));

        board.updateField(new Field(new Coordinates(4, 4), FieldState.KING));

        return board;
    }

    public void updateField(Field field) {
        fields[field.coordinates().x()][field.coordinates().y()] = field;
    }

    @Transient
    public Field getField(Coordinates coordinates) {
        return fields[coordinates.x()][coordinates.y()];
    }

    @Transient
    public List<Field> getFields() {
        var fieldsList = new ArrayList<Field>();
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                fieldsList.add(fields[x][y]);
            }
        }
        return fieldsList;
    }

    public void movePiece(Coordinates from, Coordinates to) {
        Field fromField = getField(from);

        updateField(new Field(to, fromField.state()));
        updateField(new Field(from, FieldState.EMPTY));
    }
}