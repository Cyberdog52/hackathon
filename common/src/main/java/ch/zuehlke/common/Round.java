package ch.zuehlke.common;

import java.util.List;

public record Round(List<Move> moves, PlayerId winner) {

}
