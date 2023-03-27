package ch.zuehlke.tablut;

public sealed class Field {

    public static final class NormalField extends Field {
        public final NormalFieldState state;

        public NormalField(NormalFieldState state) {
            this.state = state;
        }

    }

    public static final class CastleField extends Field {
        public final CastleFieldState state;

        public CastleField(CastleFieldState state) {
            this.state = state;
        }

    }
}


