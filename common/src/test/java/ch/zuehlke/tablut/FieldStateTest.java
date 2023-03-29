package ch.zuehlke.tablut;

import ch.zuehlke.common.FieldState;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FieldStateTest {

    @Test
    void isEnemy() {
        assertThat(FieldState.ATTACKER.isEnemy(FieldState.DEFENDER)).isTrue();
        assertThat(FieldState.ATTACKER.isEnemy(FieldState.KING)).isTrue();
        assertThat(FieldState.ATTACKER.isEnemy(FieldState.ATTACKER)).isFalse();
        assertThat(FieldState.ATTACKER.isEnemy(FieldState.EMPTY)).isFalse();

        assertThat(FieldState.DEFENDER.isEnemy(FieldState.DEFENDER)).isFalse();
        assertThat(FieldState.DEFENDER.isEnemy(FieldState.KING)).isFalse();
        assertThat(FieldState.DEFENDER.isEnemy(FieldState.ATTACKER)).isTrue();
        assertThat(FieldState.DEFENDER.isEnemy(FieldState.EMPTY)).isFalse();

        assertThat(FieldState.KING.isEnemy(FieldState.DEFENDER)).isFalse();
        assertThat(FieldState.KING.isEnemy(FieldState.KING)).isFalse();
        assertThat(FieldState.KING.isEnemy(FieldState.ATTACKER)).isTrue();
        assertThat(FieldState.KING.isEnemy(FieldState.EMPTY)).isFalse();
    }

}