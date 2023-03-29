package ch.zuehlke.common;

import lombok.Builder;

@Builder(toBuilder = true)
public record Coordinate(
        int x,
        int y
) {

}
