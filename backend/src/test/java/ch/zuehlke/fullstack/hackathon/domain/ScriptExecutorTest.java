package ch.zuehlke.fullstack.hackathon.domain;

import org.junit.jupiter.api.Test;

import static ch.zuehlke.fullstack.hackathon.domain.FieldContent.EMPTY;
import static ch.zuehlke.fullstack.hackathon.domain.FieldContent.FLAG;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ScriptExecutorTest {

    @Test
    void executeScript() {
        final Move result = new ScriptExecutor().executeScript(
                new Surroundings(FLAG, EMPTY, FLAG, FLAG),
                """
                        (function() {
                        if (surroundings.up() === fieldObjects.FLAG) {
                            return moves.UP;
                        }
                                            
                        return moves.DOWN;
                        })
                        """);

        assertEquals(Move.UP, result);

    }

}