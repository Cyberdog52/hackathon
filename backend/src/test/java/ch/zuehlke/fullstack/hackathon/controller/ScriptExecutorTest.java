package ch.zuehlke.fullstack.hackathon.controller;

import ch.zuehlke.fullstack.hackathon.model.Move;
import ch.zuehlke.fullstack.hackathon.model.Surroundings;
import org.junit.jupiter.api.Test;

import static ch.zuehlke.fullstack.hackathon.model.FieldContent.EMPTY;
import static ch.zuehlke.fullstack.hackathon.model.FieldContent.FLAG;
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