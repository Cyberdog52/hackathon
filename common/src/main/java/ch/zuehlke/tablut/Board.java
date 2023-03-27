package ch.zuehlke.tablut;

import lombok.Builder;

@Builder
public record Board(Field[][] fields) {

    public static Board createInitialBoard() {
        var fields = new Field[9][9];

        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                if (x == 4 && y == 4) {
                    fields[x][y] = new Field.CastleField(CastleFieldState.OCCUPIED);
                } else {
                    fields[x][y] = new Field.NormalField(NormalFieldState.EMPTY);
                }
            }
        }
        // top attackers
        fields[3][0] = new Field.NormalField(NormalFieldState.ATTACKER);
        fields[4][0] = new Field.NormalField(NormalFieldState.ATTACKER);
        fields[5][0] = new Field.NormalField(NormalFieldState.ATTACKER);
        fields[4][1] = new Field.NormalField(NormalFieldState.ATTACKER);
        // left attackers
        fields[0][3] = new Field.NormalField(NormalFieldState.ATTACKER);
        fields[0][4] = new Field.NormalField(NormalFieldState.ATTACKER);
        fields[0][5] = new Field.NormalField(NormalFieldState.ATTACKER);
        fields[1][4] = new Field.NormalField(NormalFieldState.ATTACKER);
        // bottom attackers
        fields[3][8] = new Field.NormalField(NormalFieldState.ATTACKER);
        fields[4][8] = new Field.NormalField(NormalFieldState.ATTACKER);
        fields[5][8] = new Field.NormalField(NormalFieldState.ATTACKER);
        fields[4][7] = new Field.NormalField(NormalFieldState.ATTACKER);
        // right attackers
        fields[8][3] = new Field.NormalField(NormalFieldState.ATTACKER);
        fields[8][4] = new Field.NormalField(NormalFieldState.ATTACKER);
        fields[8][5] = new Field.NormalField(NormalFieldState.ATTACKER);
        fields[7][4] = new Field.NormalField(NormalFieldState.ATTACKER);

        // defenders
        fields[4][2] = new Field.NormalField(NormalFieldState.DEFENDER);
        fields[4][3] = new Field.NormalField(NormalFieldState.DEFENDER);
        fields[2][4] = new Field.NormalField(NormalFieldState.DEFENDER);
        fields[3][4] = new Field.NormalField(NormalFieldState.DEFENDER);
        fields[4][5] = new Field.NormalField(NormalFieldState.DEFENDER);
        fields[4][6] = new Field.NormalField(NormalFieldState.DEFENDER);
        fields[5][4] = new Field.NormalField(NormalFieldState.DEFENDER);
        fields[6][4] = new Field.NormalField(NormalFieldState.DEFENDER);

        return new Board(fields);
    }

    public Field getField(Coordinates coordinates) {
        return fields[coordinates.x()][coordinates.y()];
    }
}