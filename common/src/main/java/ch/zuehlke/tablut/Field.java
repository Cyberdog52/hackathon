package ch.zuehlke.tablut;

public sealed class Field {

    public final Coordinates coordinates;

    public Field(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public static final class NormalField extends Field {
        public final NormalFieldState state;

        public NormalField(Coordinates coordinates, NormalFieldState state) {
            super(coordinates);
            this.state = state;
        }
    }

    public static final class CastleField extends Field {
        public final CastleFieldState state;

        public CastleField(Coordinates coordinates, CastleFieldState state) {
            super(coordinates);
            this.state = state;
        }

    }
}


