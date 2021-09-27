package ch.zuehlke.fullstack.hackathon.controller;


import ch.zuehlke.fullstack.hackathon.model.FieldObjects;
import ch.zuehlke.fullstack.hackathon.model.Move;
import ch.zuehlke.fullstack.hackathon.model.Moves;
import ch.zuehlke.fullstack.hackathon.model.Surroundings;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;

public class ScriptExecutor {


    public ScriptExecutor() {

    }

    public Move executeScript(Surroundings surroundings, String script) {
        Context polyglot = Context.newBuilder("js").allowHostAccess(HostAccess.ALL).build();
        polyglot.getBindings("js").putMember("surroundings", surroundings);
        polyglot.getBindings("js").putMember("moves", new Moves());
        polyglot.getBindings("js").putMember("fieldObjects", new FieldObjects());
        final Value playerFunction = polyglot.eval("js", script);

        final Value move = playerFunction.execute();
        return move.as(Move.class);
    }
}
